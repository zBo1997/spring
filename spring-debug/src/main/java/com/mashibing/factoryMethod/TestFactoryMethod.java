package com.mashibing.factoryMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用FactoryMethod 来创建对象
 *
 */
public class TestFactoryMethod {

    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("factoryMethod.xml");
        Person person = ac.getBean("person", Person.class);
        System.out.println(person);
        Person person2 = ac.getBean("person2", Person.class);
        System.out.println(person2);
    }
}
