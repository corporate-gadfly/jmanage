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
package org.jmanage.webui.dashboard.framework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Collection;

import org.jmanage.core.config.ApplicationConfig;

/**
 * Date: Apr 23, 2006 5:33:10 PM
 *
 * @author Shashank Bellary
 */
public class DashboardRepository {
    private static Map<String, DashboardConfig> dashboards =
            Collections.synchronizedMap(new HashMap<String, DashboardConfig>());

    private static DashboardRepository instance = new DashboardRepository();

    private DashboardRepository() {
        DashboardLoader loader = new DashboardLoader();
        loader.initialize(dashboards);
    }

    public static DashboardRepository getInstance(){
        return instance;
    }

    public DashboardConfig get(String dashboardID){

        return dashboards.get(dashboardID);
    }

    public Collection<DashboardConfig> getAll(){
        return dashboards.values();
    }
    
    /**
     * Iterate through available dashboards and get any qualifying dashboards for the given
     * application config.
     *
     * @param config
     */
    public Collection<DashboardConfig> getQualifyingDashboards(ApplicationConfig config){
        List<DashboardConfig> qualifyingDashboards = new ArrayList<DashboardConfig>();
        for(DashboardConfig dashboardConfig : getAll()){
            for(DashboardQualifier qualifier : dashboardConfig.getQualifiers()){
                if(qualifier.isQualified(config)){
                    qualifyingDashboards.add(dashboardConfig);
                    break;
                }
            }
        }
        return qualifyingDashboards;
    }

    public List<String> getQualifyingDashboardIDs(ApplicationConfig config){
        List<String> dashboardIds =  new LinkedList<String>();
        for(DashboardConfig dashboardConfig : getQualifyingDashboards(config)){
            dashboardIds.add(dashboardConfig.getDashboardId());
        }
        return dashboardIds;
    }
}
