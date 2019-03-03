package com.wangchao.shares.offer.rpc;

import com.wangchao.shares.offer.rpc.customer.Client;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 *
 * RPC 构成
 * 网络协议
 * 输入输出流（对象流)
 * 动态代理
 */
public class RpcTest {


    public static void main(String[] args) throws IOException {
        IHelloService service = Client.getRemoteProxyObj(IHelloService.class, new InetSocketAddress("127.0.0.1", 8080));
        System.out.println(service.sayHi("张三", "新年快乐，大吉大利!"));
    }
}
