/**
 * Copyright 2004-2005 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmanage.webui.actions.config;

import org.jmanage.webui.actions.BaseAction;
import org.jmanage.webui.util.*;
import org.jmanage.webui.forms.AlertForm;
import org.jmanage.core.services.AccessController;
import org.jmanage.core.services.MBeanService;
import org.jmanage.core.services.ServiceFactory;
import org.jmanage.core.auth.RoleManager;
import org.jmanage.core.config.AlertConfig;
import org.jmanage.core.config.ApplicationConfig;
import org.jmanage.core.config.ApplicationConfigManager;
import org.jmanage.core.config.AlertSourceConfig;
import org.jmanage.core.alert.AlertEngine;
import org.jmanage.core.util.Expression;
import org.jmanage.core.util.CoreUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Date: May 25, 2005 3:43:21 PM
 * @author Bhavana
 */
public class AddAlertAction extends BaseAction{
    /**
     * @param context
     * @param mapping
     * @param actionForm
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward execute(WebContext context,
                                 ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        AlertForm form = (AlertForm)actionForm;
        ApplicationConfig appConfig = context.getApplicationConfig();
        AlertConfig alertConfig = null;
        String alertId = request.getParameter(RequestParams.ALERT_ID);
        if(alertId==null || alertId.equals("")){
            alertConfig = new AlertConfig(AlertConfig.getNextAlertId(),
                    form.getAlertName(),
                    form.getAlertDelivery(),
                    form.getEmailAddress(),
                    form.getSubject());
            alertConfig.setAlertSourceConfig(getAlertSourceConfig(context,form));
            appConfig.addAlert(alertConfig);
        }else{
            alertConfig = appConfig.findAlertById(form.getAlertId());
            alertConfig.setAlertName(form.getAlertName());
            alertConfig.setSubject(form.getSubject());
            alertConfig.setAlertDelivery(form.getAlertDelivery());
            if(form.getEmailAddress()!=null){
                alertConfig.setEmailAddress(form.getEmailAddress());
            }
            alertConfig.setAlertSourceConfig(getAlertSourceConfig(context,form));
        }
        ApplicationConfigManager.updateApplication(appConfig);

        /* tell the AlertEngine about the new or modified AlertConfig*/
        AlertEngine.getInstance().updateAlertConfig(alertConfig);

        return mapping.findForward(Forwards.SUCCESS);
    }

    private AlertSourceConfig getAlertSourceConfig(WebContext context,
                                                   AlertForm form){
        AlertSourceConfig sourceConfig = null;
        Expression expression = new Expression(form.getExpression());
        if(AlertSourceConfig.SOURCE_TYPE_NOTIFICATION.equals(
                form.getAlertSourceType())){
            sourceConfig = new AlertSourceConfig(expression.getMBeanName(),
                    expression.getTargetName());
        }else if(AlertSourceConfig.SOURCE_TYPE_GAUGE_MONITOR.equals(
                form.getAlertSourceType())){
            MBeanService mbeanService = ServiceFactory.getMBeanService();
            String attributeDataType = mbeanService.getAttributeDataType(
                    Utils.getServiceContext(context),expression.getTargetName(),
                    expression.getMBeanName());
            sourceConfig = new AlertSourceConfig(expression.getMBeanName(),
                    expression.getTargetName(),
                    CoreUtils.valueOf(form.getMinAttributeValue(),attributeDataType),
                    CoreUtils.valueOf(form.getMaxAttributeValue(),attributeDataType),
                    attributeDataType);
        }else if(AlertSourceConfig.SOURCE_TYPE_STRING_MONITOR.equals(
                form.getAlertSourceType())){
            sourceConfig = new AlertSourceConfig(expression.getMBeanName(),
                    expression.getTargetName(), form.getStringAttributeValue());

        }else {
            assert false: "not supported type";
        }
        sourceConfig.setApplicationConfig(context.getApplicationConfig());
        return sourceConfig;
    }


}
