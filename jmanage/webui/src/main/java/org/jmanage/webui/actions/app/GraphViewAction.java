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
package org.jmanage.webui.actions.app;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.jmanage.webui.util.WebContext;
import org.jmanage.webui.util.RequestAttributes;
import org.jmanage.webui.actions.BaseAction;
import org.jmanage.core.config.ApplicationConfig;
import org.jmanage.core.config.GraphConfig;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Action class for the graph view page. This page contains the applet
 * which pulls attriute values via MBeanAttributeValuesAction.
 *
 * @see MBeanAttributeValuesAction
 *
 * Date:  Jun 12, 2005
 * @author	Rakesh Kalra
 */
public class GraphViewAction extends BaseAction {

    public ActionForward execute(WebContext context,
                                 ActionMapping mapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception{

        ApplicationConfig appConfig = context.getApplicationConfig();
        // graphs at cluster level are not yet supported
        assert !appConfig.isCluster();

        // todo: do we need access control for graphs? probably not.
        //AccessController.checkAccess(context.getServiceContext(),
        //        ACLConstants.ACL_VIEW_APPLICATIONS);

        /*set current page for navigation*/
        GraphConfig graphConfig =
                appConfig.findGraph(request.getParameter("graphId"));
        assert graphConfig != null;
        request.setAttribute(RequestAttributes.NAV_CURRENT_PAGE,
                graphConfig.getName());

        return mapping.findForward("success");
    }
}
