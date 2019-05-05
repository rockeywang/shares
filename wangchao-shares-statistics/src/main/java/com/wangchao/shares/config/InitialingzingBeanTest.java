package com.wangchao.shares.config;

import com.wangchao.shares.service.BillCodeGenerateProcess;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialingzingBeanTest implements ApplicationRunner {


    @Autowired
    private BillCodeGenerateProcess billCodeGenerateProcess;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //billCodeGenerateProcess.init();
    }
}
