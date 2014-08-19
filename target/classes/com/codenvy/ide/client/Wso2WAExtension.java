package com.codenvy.ide.client;


import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.client.wizard.project.WSO2PagePresenter;
import com.codenvy.ide.api.ui.wizard.ProjectTypeWizardRegistry;
import com.codenvy.ide.api.ui.wizard.ProjectWizard;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;


/**
 * @author Sohani
 */
@Singleton
@Extension(title = "Developer Studio - WA", version = "1.0.0")

public class Wso2WAExtension {
    @Inject
    public Wso2WAExtension(Provider<WSO2PagePresenter> wso2PagePresenter,
                         ProjectTypeWizardRegistry projectTypeWizardRegistry,
                         ProjectWizard projectWizard) {

        projectWizard.addPage(wso2PagePresenter);
        projectTypeWizardRegistry.addWizard("WAConfigurationProject", projectWizard);
    }

    
}


  
