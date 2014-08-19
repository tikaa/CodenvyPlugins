package com.codenvy.ide.server;

import com.codenvy.inject.DynaModule;
import com.google.inject.AbstractModule;


/**
 * @author Sohani
 */
@DynaModule
public class Wso2WAModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(Wso2WAProjectTypeDescriptionExension.class);
        //bind(Axis2ServiceProjectTypeExtension.class);
    }

}