package com.mashibing.factoryMethod;

/**
 * Spring的Bean实例化工厂
 */
public class PersonInstanceFactory {
    public Person getPerson(String name){
        Person person = new Person();
        person.setId(1);
        person.setName(name);
        return person;
    }
}