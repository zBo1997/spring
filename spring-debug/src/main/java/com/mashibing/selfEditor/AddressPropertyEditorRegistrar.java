package com.mashibing.selfEditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.beans.PropertyEditor;

public class AddressPropertyEditorRegistrar implements PropertyEditorRegistrar {

    /**
     * 自定义的
     * @param registry the {@code PropertyEditorRegistry} to register the
     */
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Address.class,new AddressPropertyEditor());
    }
}
