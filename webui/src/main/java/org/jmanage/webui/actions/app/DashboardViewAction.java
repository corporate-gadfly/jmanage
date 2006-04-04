/**
 * Copyright 2004-2005 jManage.org. All rights reserved.
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
package org.jmanage.webui.actions.app;

import org.jmanage.webui.actions.BaseAction;
import org.jmanage.webui.util.WebContext;
import org.jmanage.webui.util.RequestAttributes;
import org.jmanage.webui.util.Forwards;
import org.jmanage.webui.forms.DashboardForm;
import org.jmanage.core.config.DashboardConfig;
import org.jmanage.core.services.ConfigurationService;
import org.jmanage.core.services.ServiceFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * <p>
 * Date:  Feb 18, 2006
 * @author	Rakesh Kalra
 */
public class DashboardViewAction  extends BaseAction {

    public ActionForward execute(WebContext context,
                                 ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception{

        DashboardForm dashboardForm = (DashboardForm)actionForm;
        ConfigurationService service = ServiceFactory.getConfigurationService();
        DashboardConfig config =
                service.getDashboard(context.getServiceContext(), dashboardForm.getId());
        assert config != null: "dashboard not found for id: " + dashboardForm.getId();

        /*set current page for navigation*/
        request.setAttribute(RequestAttributes.NAV_CURRENT_PAGE, config.getName());

        return mapping.findForward(Forwards.SUCCESS);
    }
}