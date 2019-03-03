package com.wangchao.shares.offer.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerCenter implements Server{

        /**
         * 线程池 接收客户端调用
         */
        private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 200, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(10));
        /**
         * 服务注册缓存
         */
        private static final HashMap<String, Class<?>> serviceRegistry = new HashMap<String, Class<?>>();



       @Override
       public void start() throws IOException {
           ServerSocket server = new ServerSocket();
           server.bind(new InetSocketAddress(PORT));
           try {
               for (;;) {
                   executor.execute(new ServiceTask(server.accept()));
               }
           } finally {
               server.close();
           }
       }

      @Override
      public void stop() {
          executor.shutdown();
      }

      @Override
      public void regist(Class<? extends IRpcService> serviceInterface, Class<? extends IRpcService> impl) {
          serviceRegistry.put(serviceInterface.getName(), impl);
      }

    /**
     *服务具体调度--对象流反序列化，反射调用本地服务，并输出结果到客户端
     */
    private static class ServiceTask implements Runnable {
        Socket clent = null;

        public ServiceTask(Socket client) {
            this.clent = client;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                input = new ObjectInputStream(clent.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class<?> serviceClass = serviceRegistry.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);
                // 3.将执行结果反序列化，通过socket发送给客户端
                output = new ObjectOutputStream(clent.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (clent != null) {
                    try {
                        clent.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
