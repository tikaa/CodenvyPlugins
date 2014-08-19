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



import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;

/**
 * The implementation of {@link WSO2PageView}.
 *
 * @author Sohani
 */
public class WSO2PageViewImpl extends Composite implements WSO2PageView {
    
   //  private ActionDelegate delegate;
    
    @UiField
    TextArea artifactId;
    @UiField
    TextArea groupId;
   // private ActionDelegate delegate;

    @Inject
    public WSO2PageViewImpl(WSO2PageViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setDelegate(ActionDelegate delegate) {
    }

    interface WSO2PageViewImplUiBinder extends UiBinder<DockLayoutPanel, WSO2PageViewImpl> {
    }
   

    public String getartifactId() {
        return artifactId.getText();
        
    }

   
    public String getgroupId() {
         return  groupId.getText();  
        
    }
    
   /*  @UiHandler("artifactId")
    public void onValueChanged(KeyUpEvent event) {
        delegate.onVariableValueChanged();
    }
      @UiHandler("groupId")
    public void onnextValueChanged(KeyUpEvent event) {
        delegate.onotherVariableValueChanged();
    }*/
   
}
