package com.wangchao.shares.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangchang
 * @Date: 2018/10/30 14:41
 * @Description:
 */
@RestController
public class HelloController {


    @GetMapping(value = "/admin")
    public String sayOK() {
        return "200";
    }


    @GetMapping(value = "/login")
    public String login() {
        return "200";
    }
}
