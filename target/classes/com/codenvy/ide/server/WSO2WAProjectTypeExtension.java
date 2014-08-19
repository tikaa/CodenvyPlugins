package com.codenvy.ide.server;

import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.server.ProjectTypeExtension;
import com.codenvy.api.project.shared.Attribute;
import com.codenvy.api.project.shared.ProjectTemplateDescription;
import com.codenvy.api.project.shared.ProjectType;

import com.google.inject.Singleton;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;



/**
 * @author Valeriy Svydenko
 */
@Singleton
public class WSO2WAProjectTypeExtension implements ProjectTypeExtension {

    @Inject
    public WSO2WAProjectTypeExtension(ProjectTypeDescriptionRegistry registry) {
        registry.registerProjectType(this);
    }

    @Override
    public ProjectType getProjectType() {
        return new ProjectType("WAConfigurationProject", "WSO2Project", "WSO2Project");
    }

    @Override
    public List<Attribute> getPredefinedAttributes() {
        return Arrays.asList(new Attribute("language", "WSO2Project"),
                             new Attribute("framework", "WSO2Project"));
    }

    @Override
    public List<ProjectTemplateDescription> getTemplates() {
        return Arrays.asList(new ProjectTemplateDescription("zip",
                                                            "Web Application Project",
                                                            "This is a simple WA configuration project.",
                                                            "templates/WebApplicationProject.zip"));
    }

}