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

package org.jmanage.core.modules.websphere;

import org.jmanage.core.modules.JMXServerConnection;

import com.ibm.websphere.management.AdminClient;

/**
 * Date: Jan 23, 2005 5:54:49 PM
 * @author Shashank Bellary 
 */
public class WebSphereServerConnection extends JMXServerConnection{

    public WebSphereServerConnection(AdminClient adminClient){
        super(adminClient, AdminClient.class);
    }
}
