/*
 * Copyright 2000-2004 by Upromise Inc.
 * 117 Kendrick Street, Suite 200, Needham, MA, 02494, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Upromise, Inc. ("Confidential Information").  You shall not disclose
 * such Confidential Information and shall use it only in accordance with
 * the terms of an agreement between you and Upromise.
 */
package org.jmanage.core.config;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * date:  Sep 15, 2004
 * @author	Rakesh Kalra
 */
public class ApplicationClusterConfig extends ApplicationConfig {

    private final List applications = new LinkedList();

    public ApplicationClusterConfig(String id, String name){
        setApplicationId(id);
        setName(name);
    }

    /**
     * @return true
     */
    public boolean isCluster(){
        return true;
    }

    /**
     * This method returns a list of applications in this cluster.
     *
     * @return  list of applications in this cluster
     */
    public List getApplications(){
        return applications;
    }

    /**
     * Adds a application to this cluster.
     *
     * @param config    ApplicationConfig to be added to this cluster
     */
    public void addApplication(ApplicationConfig config){
        applications.add(config);
    }

    public void addAllApplications(List appConfigList){
        applications.addAll(appConfigList);
    }

    /**
     * Removes the application from the cluster.
     *
     * @param config
     * @return true: if the application was removed from the cluster
     */
    public boolean removeApplication(ApplicationConfig config){
        return applications.remove(config);
    }

}