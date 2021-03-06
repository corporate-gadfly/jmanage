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
package org.jmanage.webui.actions.auth;

import org.jmanage.webui.actions.BaseAction;
import org.jmanage.webui.util.WebContext;
import org.jmanage.webui.util.RequestParams;
import org.jmanage.webui.util.Forwards;
import org.jmanage.core.auth.UserManager;
import org.jmanage.core.services.AccessController;
import org.jmanage.core.util.UserActivityLogger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date : Jul 27, 2004 11:39:21 PM
 * @author Shashank
 */
public class DeleteUserAction extends BaseAction{

    /**
     * Delete the selected user.
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
        AccessController.checkAccess(context.getServiceContext(), ACL_EDIT_USERS);
        String username = request.getParameter(RequestParams.USER_NAME);
        UserManager.getInstance().deleteUser(username);
        UserActivityLogger.getInstance().logActivity(
                context.getUser().getUsername(), "Deleted user "+username);
        return mapping.findForward(Forwards.SUCCESS);
    }
}
