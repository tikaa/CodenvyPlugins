/*
 * Copyright [2014] Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.wizard.project;



import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.api.project.shared.dto.ProjectDescriptor;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.resources.model.Project;
import com.codenvy.ide.api.ui.wizard.AbstractWizardPage;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import static com.codenvy.ide.api.ui.wizard.ProjectWizard.PROJECT;
import static com.codenvy.ide.api.ui.wizard.ProjectWizard.PROJECT_NAME;
import static com.codenvy.ide.api.ui.wizard.ProjectWizard.PROJECT_TYPE;
import static com.codenvy.ide.api.ui.wizard.ProjectWizard.PROJECT_VISIBILITY;

/**
 * The wizard page provides creating an empty ESB configuration project.
 *
 * @author Sohani
 */
public class WSO2PagePresenter extends AbstractWizardPage implements WSO2PageView.ActionDelegate {
    private final WSO2PageView         view;
    private final ProjectServiceClient projectServiceClient;
    private final ResourceProvider     resourceProvider;
    private final DtoFactory           factory;
     private NotificationManager notificationManager;
    private Notification projectCreationStatusNotification;     
  

    @Inject
    public WSO2PagePresenter(WSO2PageView view,
                             ProjectServiceClient projectServiceClient,
                             ResourceProvider resourceProvider,
                             DtoFactory factory,
                             NotificationManager notificationManager) {
        super("WSO2 Web Application project settings", null);

        this.view = view;
         this.notificationManager = notificationManager;
        this.projectServiceClient = projectServiceClient;
        this.resourceProvider = resourceProvider;
        this.factory = factory;
        this.view.setDelegate(this);
        
    }

    @Nullable
    @Override
    public String getNotice() {
        return null;
    }

    @Nonnull
    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public void focusComponent() {
    }

    @Override
    public void removeOptions() {
    }

    @Override
    public void go(AcceptsOneWidget container) {
        container.setWidget(view);
    }
    
    /*@Override
    public void onVariableValueChanged() {
       final String  artifacteId = view.getartifactId();
        
    }

    @Override
    public void onotherVariableValueChanged() {
       final String  groupeId = view.getgroupId();
        
    }*/
    
    
    //end

    @Override
    public void commit(@NotNull final CommitCallback callback) {
        final ProjectDescriptor projectDescriptor = factory.createDto(ProjectDescriptor.class);
        projectDescriptor.withProjectTypeId(wizardContext.getData(PROJECT_TYPE).getProjectTypeId());

        boolean visibility = wizardContext.getData(PROJECT_VISIBILITY);
        projectDescriptor.setVisibility(visibility ? "public" : "private");
        final String name = wizardContext.getData(PROJECT_NAME);
        final Project project = wizardContext.getData(PROJECT);
        
        //assigning values
        
        

        if (project != null) {
            if (project.getName().equals(name)) {
                updateProject(project, projectDescriptor, callback);
            } else {
                projectServiceClient.rename(project.getPath(), name, null, new AsyncRequestCallback<Void>() {
                    @Override
                    protected void onSuccess(Void result) {
                        project.setName(name);

                        updateProject(project, projectDescriptor, callback);
                    }

                    @Override
                    protected void onFailure(Throwable exception) {
                        callback.onFailure(exception);
                    }
                });
            }

        } else {
            createProject(callback, projectDescriptor, name);           
           
        }
    }

    private void updateProject(@Nonnull final Project project,
                               @Nonnull ProjectDescriptor projectDescriptor,
                               @Nonnull final CommitCallback callback) {
        projectServiceClient.updateProject(project.getPath(), projectDescriptor, new AsyncRequestCallback<ProjectDescriptor>() {
            @Override
            protected void onSuccess(ProjectDescriptor result) {
                resourceProvider.getProject(project.getName(), new AsyncCallback<Project>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        callback.onFailure(caught);
                    }

                    @Override
                    public void onSuccess(Project result) {
                        callback.onSuccess();
                    }
                });
            }

            @Override
            protected void onFailure(Throwable exception) {
                callback.onFailure(exception);
            }
        });
    }

  
     private void createProject(@Nonnull final CommitCallback callback,
                               @Nonnull ProjectDescriptor projectDescriptor,
                               @Nonnull final String name ) {
        projectServiceClient
                .createProject(name, projectDescriptor,
                               new AsyncRequestCallback<ProjectDescriptor>() {
                                   @Override
                                   protected void onSuccess(ProjectDescriptor result) {
                                       resourceProvider.getProject(name, new AsyncCallback<Project>() {
                                           @Override
                                           public void onSuccess(Project project) {
                                               callback.onSuccess();
                                               creatingMethod(project);
                                           }

                                           @Override
                                           public void onFailure(Throwable caught) {
                                               callback.onFailure(caught);
                                           }
                                       });
                                   }

                                   @Override
                                   protected void onFailure(Throwable exception) {
                                       callback.onFailure(exception);
                                   }
                   });
     
     
       
        
    }
     
    
      
      public void creatingMethod(Project project){
          
       projectCreationStatusNotification = new Notification("creating the folder structure ", Notification.Status.PROGRESS);
         notificationManager.showNotification(projectCreationStatusNotification);
        
        //CommitCallback callback = new Commit;
        final String  artifactId = view.getartifactId();
        final String  groupId = view.getgroupId();
          
          
       String Filefile = "pom.xml"; 
        String pomxmlContent =  "<?xml version=\""+"1.0"+"\""+" encoding="+"\""+"UTF-8"+"\"?> \n"
                            +"<project xsi:schemaLocation = \""+"http://maven.apache.org/POM/4.0.0"+"\""+"xmlns:xsi ="+"\""+"http://www.w3.org/2001/XMLSchema-instance"+"\""
                            +"xsi:schemaLocation= "+"\""+"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd"+"\"> \n"+" <modelVersion>"+"4.0.0"+"</modelVersion> \n" +"<parent> \n"
                            +" <artifactId>"+artifactId+"</artifactId> \n"+ "<groupId>"+ groupId ;
                            
        
       String WebxmlLoc = project.getName()+"/WebContent/WEB-INF/lib";
       String webxmlfile = "web.xml";
      
        
       String manifestmfloc = project.getName()+"/WebContent/META-INF";
       String manifestContent = "Manifest-Version: 1.0 \n" + 
      		                     "Class-Path: \n" ;
       String manifestmffile = "MANIFEST.MF";
       
       projectServiceClient.createFolder(project.getName()+"/WebContent/WEB-INF/lib", new MyEmptyCallBack());
     
       projectServiceClient.createFolder(project.getName()+"/WebContent/META-INF", new MyEmptyCallBack());
       
       projectServiceClient.createFolder(project.getName()+"/build/classes", new MyEmptyCallBack());
       
       projectServiceClient.createFolder(project.getName()+"/src/main/java", new MyEmptyCallBack());
       
      
       
       projectServiceClient.createFile(project.getName(), Filefile, pomxmlContent, "fff", new MyEmptyCallBack());
       
       projectServiceClient.createFile(WebxmlLoc, webxmlfile, pomxmlContent, "fff", new MyEmptyCallBack());
       
       projectServiceClient.createFile(manifestmfloc, manifestmffile, manifestContent, "fff", new MyLastCallBack());
          
          
          
      }
      
       //filecreation callback class
      class MyEmptyCallBack  extends AsyncRequestCallback<Void> {
              @Override
              protected void onSuccess(Void result) {
      
              }
      
              @Override
              protected void onFailure(Throwable exception) {
      
              }
      }
      class MyLastCallBack  extends AsyncRequestCallback<Void> {
              @Override
              protected void onSuccess(Void result) {
               

                resourceProvider.getActiveProject().refreshChildren(new AsyncCallback<Project>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(Project result) {

                    }
                });
              }
      
              @Override
              protected void onFailure(Throwable exception) {
      
              }
      }

   
     
    
     
  }