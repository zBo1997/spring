package com.mashibing.supplier;

public class CreateSupplier {
    /**
     * 定义一个函数时接口的实现 准备给 GenericBeanDefination 来使用
     * {@link Supplier}  一个Java 8 实现的 函数式接口
     * @return
     */
    public static User createUser(){
        return new User("zhangsan");
    }
}
