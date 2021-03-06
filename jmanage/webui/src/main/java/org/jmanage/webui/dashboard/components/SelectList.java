/**
 * Copyright 2004-2006 jManage.org
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
package org.jmanage.webui.dashboard.components;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;

import org.jmanage.core.management.ObjectName;
import org.jmanage.core.management.ServerConnection;
import org.jmanage.util.display.html.Select;
import org.jmanage.webui.dashboard.framework.BaseDashboardComponent;
import org.jmanage.webui.dashboard.framework.DashboardContext;
import org.jmanage.webui.util.WebContext;

/**
 *
 * Example config:
 * 
 *    <property name="mbean" value="java.lang:type=Threading"/>
 *    <property name="attribute" value="AllThreadIds"/>
 *    <property name="idResolverMBean" value="java.lang:type=Threading"/>
 *    <property name="idResolverOperation" value="getThreadInfo"/>
 *    <property name="idResolverName" value="threadName"/>
 *    
 * @author Rakesh Kalra
 */
public class SelectList extends BaseDashboardComponent {

    private static final String MBEAN = "mbean";
    private static final String ATTRIBUTE = "attribute";
    // optional
    private static final String ID_TYPE = "idType";
    private static final String ID_RESOLVER_MBEAN = "idResolverMBean";
    private static final String ID_RESOLVER_OPERATION = "idResolverOperation";
    private static final String ID_RESOLVER_NAME = "idResolverName";
    
    private static final String SIZE = "size";
    
    private String mbean;
    private String attribute;
    
    private String idType;
    private String idResolverMBean;
    private String idResolverOperation;
    private String idResolverName;
    
    private int size = 10; //default value
    
    public void init(Map<String, String> properties) {
        mbean = properties.get(MBEAN);
        assert mbean != null;
        attribute = properties.get(ATTRIBUTE);
        assert attribute != null;
        idType = properties.get(ID_TYPE);
        idResolverMBean = properties.get(ID_RESOLVER_MBEAN);
        idResolverOperation = properties.get(ID_RESOLVER_OPERATION);
        idResolverName = properties.get(ID_RESOLVER_NAME);
        if(properties.get(SIZE) != null)
            size = Integer.parseInt(properties.get(SIZE));
    }
    
    public void drawInternal(DashboardContext context, StringBuffer output) {
        Map<String, String> data = getData(context.getWebContext());
        Select select = new Select("dummy", size, true);
        //select.onChange("alert(''test'');");
        final String dashboardId = context.getDashboardConfig().getDashboardId();
        select.onChange("handleEvent(''" + dashboardId + "'', ''" + getId() + "'', ''onChange'', this.options[this.selectedIndex].value);");
        for(String id:data.keySet()){
            select.addOption(id, data.get(id));
        }
        output.append(select.draw());
    }
    
    private Map<String, String> getData(WebContext webContext){
        Map<String, String> data = new HashMap<String, String>();
        ServerConnection serverConnection = webContext.getServerConnection(); 
        Object value = serverConnection.getAttribute(new ObjectName(mbean), attribute);
        if(value.getClass().isArray()){
            for(int i=0; i<Array.getLength(value); i++){
                Object id = Array.get(value, i);
                data.put(id.toString(), resolveId(serverConnection, id));  
            }
        }else{
            assert false: "List, etc., not yet implemented";
        }
        return data;
    }
    
    private String resolveId(ServerConnection connection, Object id){
        Object resolvedId = id;
        if(idResolverMBean != null){
            if(idResolverOperation != null){
                if(idType == null){
                    idType = id.getClass().getName();
                }
                Object output = connection.invoke(new ObjectName(idResolverMBean), idResolverOperation, 
                        new Object[]{id}, new String[]{idType});
                if(idResolverName != null){
                    // if idResolverName is specified, output is a composite type
                    CompositeData compositeData = (CompositeData)output;
                    resolvedId = compositeData.get(idResolverName);
                }
            }
        }
        return resolvedId.toString();
    }
}
