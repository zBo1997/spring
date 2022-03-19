package com.mashibing.selfAutowired;

import org.springframework.stereotype.Component;

@Component
public class TestServiceImpl implements TestService{


    public void show(){
        System.out.println("我是Test");
    };
}
