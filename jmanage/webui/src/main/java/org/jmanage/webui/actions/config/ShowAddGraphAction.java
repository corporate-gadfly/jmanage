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
import org.jmanage.webui.util.WebContext;
import org.jmanage.webui.util.Forwards;
import org.jmanage.webui.util.RequestAttributes;
import org.jmanage.webui.util.Utils;
import org.jmanage.webui.forms.GraphForm;
import org.jmanage.core.util.Expression;
import org.jmanage.core.services.AccessController;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date: Jun 23, 2005 4:49:16 PM
 * @author Bhavana
 */
public class ShowAddGraphAction extends BaseAction{
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
        AccessController.checkAccess(Utils.getServiceContext(context), ACL_ADD_GRAPH);
        GraphForm form = (GraphForm)actionForm;
        String[] attributes = form.getAttributes();
        String[] attributeNames = new String[attributes.length];
        String[] objectNames = new String[attributes.length];
        String[] displayNames = new String[attributes.length];
        for(int i=0; i<attributes.length;i++){
            Expression expression = new Expression(attributes[i]);
            attributeNames[i] = expression.getTargetName();
            displayNames[i] = expression.getTargetName();
            objectNames[i] = expression.getMBeanName();
        }
        request.setAttribute("attributeNames", attributeNames);
        request.setAttribute("objectNames",objectNames);
        request.setAttribute("displayNames",displayNames);
        /*set current page for navigation*/
        request.setAttribute(RequestAttributes.NAV_CURRENT_PAGE, "Add Graph");
        return mapping.findForward(Forwards.SUCCESS);
    }

}
