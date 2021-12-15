package com.mashibing.resolveBeforeInstantiation;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    /**
     * 实例化之前
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("beanName:"+beanName+"----执行postProcessBeforeInstantiation方法");
        if (beanClass == BeforeInstantiation.class){
            //使用CgLib进行动态代理创建对象
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallback(new MyMethodInterceptor());
            BeforeInstantiation beforeInstantiation = (BeforeInstantiation) enhancer.create();
            System.out.println("创建代理对象："+beforeInstantiation);
            return new BeforeInstantiation();
        }
        return null;
    }

    /**
     * 实例化之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("beanName:"+beanName+"----执行postProcessAfterInstantiation方法");

        return false;
    }

    /**
     * 初始化之后前
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("beanName:"+beanName+"----执行postProcessBeforeInitialization方法");

        return bean;
    }

    /**
     * 初始化之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("beanName:"+beanName+"----执行postProcessAfterInitialization方法");

        return bean;
    }

    /**
     * 对属性的相关操作
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("beanName:"+beanName+"----执行postProcessProperties方法");

        return pvs;
    }
}
