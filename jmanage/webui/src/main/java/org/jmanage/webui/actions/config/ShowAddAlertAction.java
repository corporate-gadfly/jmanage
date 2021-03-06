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
import org.jmanage.core.config.ApplicationConfigManager;
import org.jmanage.core.config.AlertSourceConfig;
import org.jmanage.core.util.Expression;
import org.jmanage.core.services.AccessController;
import org.jmanage.core.services.MBeanService;
import org.jmanage.core.services.ServiceFactory;
import org.jmanage.core.management.ObjectAttribute;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date: May 26, 2005 4:16:42 PM
 * @author Bhavana
 */
public class ShowAddAlertAction extends BaseAction{
    /**
     * Get all configured alert delivery mechanism
     *
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

        AccessController.checkAccess(Utils.getServiceContext(context), ACL_ADD_ALERT);
        AlertForm alertForm = (AlertForm)actionForm;
        String sourceType = request.getParameter(RequestParams.ALERT_SOURCE_TYPE);
        request.setAttribute("alertSourceType",
                            sourceType);
        if(sourceType.equals(AlertSourceConfig.SOURCE_TYPE_NOTIFICATION)){
            Expression expr = new Expression(alertForm.getExpression());
            request.setAttribute("sourceMBean", expr.getMBeanName());
            request.setAttribute("notificationType", expr.getTargetName());
        }else if(sourceType.equals(AlertSourceConfig.SOURCE_TYPE_GAUGE_MONITOR)
                || sourceType.equals(AlertSourceConfig.SOURCE_TYPE_STRING_MONITOR)){
            Expression expr = new Expression(alertForm.getExpression());
            request.setAttribute("sourceMBean", expr.getMBeanName());
            request.setAttribute("attribute", expr.getTargetName());
            MBeanService mbeanService = ServiceFactory.getMBeanService();
            ObjectAttribute objAttr = mbeanService.getObjectAttribute(
                    Utils.getServiceContext(context, expr),
                    expr.getTargetName());
            request.setAttribute("currentAttrValue",
                    objAttr.getDisplayValue());

        }

        request.setAttribute("applications",
                ApplicationConfigManager.getAllApplications());
        /*set current page for navigation*/
        request.setAttribute(RequestAttributes.NAV_CURRENT_PAGE, "Add Alert");
        return mapping.findForward(Forwards.SUCCESS);
    }
}
