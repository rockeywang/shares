package com.wangchao.shares.offer.rpc;


/**
 * 服务端暴露服务接口，客户端可以直接调用
 */
public interface IHelloService extends  IRpcService{

    String sayHi(String name, String msg);
}
