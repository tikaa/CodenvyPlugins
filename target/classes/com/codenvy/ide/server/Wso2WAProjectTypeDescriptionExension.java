package com.codenvy.ide.server;

import com.codenvy.api.project.server.ProjectTypeDescriptionExtension;
import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.shared.AttributeDescription;
import com.codenvy.api.project.shared.ProjectType;
import com.google.inject.Singleton;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;


/**
 * Register WSO2 extension {@link com.codenvy.api.project.server.ProjectTypeDescriptionExtension} to register project types.
 *
 * @author Sohani
 */
@Singleton
public class Wso2WAProjectTypeDescriptionExension implements ProjectTypeDescriptionExtension {
    @Inject
    public Wso2WAProjectTypeDescriptionExension(ProjectTypeDescriptionRegistry registry) {
        registry.registerDescription(this);
    }

    @Override
    public List<ProjectType> getProjectTypes() {
        return Arrays.asList(new ProjectType("WAConfigurationProject", "Web Application Project", "WSO2Project"));
    }

    @Override
    public List<AttributeDescription> getAttributeDescriptions() {
        return Arrays.asList(new AttributeDescription("language"),
                             new AttributeDescription("framework"));
    }
    
     
}
