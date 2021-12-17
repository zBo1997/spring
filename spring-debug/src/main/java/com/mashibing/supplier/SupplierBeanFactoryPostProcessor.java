package com.mashibing.supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class SupplierBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    /**
     * 通过BeanPostProcessBeanFactory 来设置 beanDefination 然后通过BeanDefination 来设置 Supplier
     * 进而在 doCreateBean中可以通过 Supplier 来创建对象
     * tip: 或者对bean做一些 其他操作
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //拿到需要创建对象的BeanDefinition
        BeanDefinition user = beanFactory.getBeanDefinition("user");
        //这里 GenericBeanDefinition 里面有所谓的 Supplier 的函数定义
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) user;
        //通过GenericBeanDefinition 设置对应的Supplier 方法
        genericBeanDefinition.setInstanceSupplier(CreateSupplier::createUser);
        //设置BeanDefination的类型
        genericBeanDefinition.setBeanClass(User.class);
    }
}
