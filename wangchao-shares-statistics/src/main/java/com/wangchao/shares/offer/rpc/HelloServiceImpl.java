package com.wangchao.shares.offer.rpc;

public class HelloServiceImpl  implements IHelloService {
    @Override
    public String sayHi(String name, String msg) {
        return new StringBuffer().append("hi~! ").append(name).append(",").append(msg).toString();
    }
}
