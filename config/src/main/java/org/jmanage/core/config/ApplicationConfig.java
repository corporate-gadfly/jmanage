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
package org.jmanage.core.config;


import java.util.*;

/**
 *
 * date:  Jun 11, 2004
 * @author	Rakesh Kalra
 */
public abstract class ApplicationConfig {

    // jsr160 constants
    public static final String JNDI_FACTORY = "java.naming.factory.initial";
    public static final String JNDI_URL = "java.naming.provider.url";

    private static final List EMPTY_LIST = new ArrayList();

    public static String getNextApplicationId(){
        //todo: Need something better
        return String.valueOf(System.currentTimeMillis());
    }

    private String appId;
    private String type;
    private String name;
    private String host;
    private Integer port;
    private String url;
    private String username;
    private String password;
    protected Map paramValues;
    private List mbeanList = new LinkedList();
    private List graphList = new LinkedList();
    // clusterConfig: if this is part of a cluster
    private ApplicationConfig clusterConfig;
    private List alertsList = new LinkedList();

    public String getApplicationId(){
        return appId;
    }

    public void setApplicationId(String appId){
        assert this.appId == null;
        this.appId = appId;
    }

    /**
     * @return  type of application
     */
    public String getType(){
        return type;
    }

    public void setType(String type){
        assert this.type == null;
        this.type = type;
    }

    /**
     * @return  the name of the application
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * If there are no additional parameters, the returned list is empty
     *
     * @return  list of additional configuration parameters(ConfigParam objects)
     */
    public List getAdditionalParameters(){
        return EMPTY_LIST;
    }

    public Map getParamValues(){
        if(paramValues == null)
            paramValues = new HashMap();
        return paramValues;
    }

    public void setParamValues(Map paramValues){
        this.paramValues = paramValues;
    }

    /**
     * @return true: if its a application cluster; false: otherwise
     */
    public boolean isCluster(){
        return false;
    }

    /**
     * If this is a application cluster, this method returns a list of
     * applications in this cluster.
     *
     * @return  list of applications in this cluster
     */
    public List getApplications(){
        return null;
    }

    /**
     * @return list of MBeanConfig objects
     */
    public List getMBeans(){
        return mbeanList;
    }

    /**
     *
     * @param mbeanList list of MBeanConfig objects
     */
    public void setMBeans(List mbeanList){
        if(mbeanList != null){
            this.mbeanList = mbeanList;
        }else{
            this.mbeanList = new LinkedList();
        }
    }

    public int hashCode(){
        return getApplicationId().hashCode();
    }

    public boolean equals(Object obj){
        if(obj instanceof ApplicationConfig){
            ApplicationConfig config = (ApplicationConfig)obj;
            return config.getApplicationId().equals(this.getApplicationId());
        }
        return false;
    }

    public String toString(){
        return getApplicationId() + ";" + getName() + ";" + getURL();
    }

    public MBeanConfig removeMBean(String objectName){

        for(Iterator it=mbeanList.iterator(); it.hasNext();){
            MBeanConfig mbeanConfig = (MBeanConfig)it.next();
            if(mbeanConfig.getObjectName().equals(objectName)){
                mbeanList.remove(mbeanConfig);
                return mbeanConfig;
            }
        }
        return null;
    }

    public void addMBean(MBeanConfig mbeanConfig){

        assert !containsMBean(mbeanConfig.getObjectName());
        mbeanList.add(mbeanConfig);
    }

    public ApplicationConfig getClusterConfig() {
        return clusterConfig;
    }

    public void setClusterConfig(ApplicationConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
    }

    public MBeanConfig findMBean(String mbeanName) {
        for(Iterator it=mbeanList.iterator(); it.hasNext();){
            MBeanConfig mbeanConfig = (MBeanConfig)it.next();
            if(mbeanConfig.getName().equals(mbeanName)){
                return mbeanConfig;
            }
        }
        return null;
    }

    public MBeanConfig findMBeanByObjectName(String objectName){
        for(Iterator it=mbeanList.iterator(); it.hasNext();){
            MBeanConfig mbeanConfig = (MBeanConfig)it.next();
            if(mbeanConfig.getObjectName().equals(objectName)){
                return mbeanConfig;
            }
        }
        return null;
    }

    public boolean containsMBean(String objectName) {
        return findMBeanByObjectName(objectName) != null;
    }

    public void addGraph(GraphConfig graphConfig){
        assert graphConfig!=null:"graphConfig is null";
        graphList.add(graphConfig);
    }
    public void setGraphs(List graphList) {
        if(graphList != null){
            this.graphList = graphList;
        }else{
            this.graphList = new LinkedList();
        }
    }

    public List getGraphs(){
        return graphList;
    }

    public GraphConfig findGraph(String graphId) {
        for(Iterator it=graphList.iterator(); it.hasNext(); ){
            GraphConfig graphConfig = (GraphConfig)it.next();
            if(graphConfig.getId().equals(graphId)){
                return graphConfig;
            }
        }
        return null;
    }
    public GraphConfig removeGraph(String graphId){
        GraphConfig graphConfig = findGraph(graphId);
        if(graphConfig!=null){
            graphList.remove(graphConfig);
            return graphConfig;
        }
        return null;
    }



   public void addAlert(AlertConfig alertConfig){
       assert alertConfig!=null:"alert config is null";
       alertsList.add(alertConfig);
   }

   public List getAlerts(){
       return alertsList;
   }
   /**
    *
    * @param alertsList list of MBeanConfig objects
    */
   public void setAlerts(List alertsList){
       if(alertsList != null){
           this.alertsList = alertsList;
       }else{
           this.alertsList = new LinkedList();
       }
   }

    public AlertConfig findAlertById(String alertId){
        AlertConfig alert = null;
        for(Iterator itr=alertsList.iterator();itr.hasNext();){
            AlertConfig alertConfig = (AlertConfig)itr.next();
            if(alertConfig.getAlertId().equals(alertId)){
                alert = alertConfig;
                break;
            }
        }
        return alert;
    }

    public AlertConfig removeAlert(String alertId){
        AlertConfig alertConfig = findAlertById(alertId);
        if(alertConfig!=null){
            alertsList.remove(alertConfig);
            return alertConfig;
        }
        return null;
    }

    public ClassLoader getApplicationClassLoader(){
        ApplicationType appType =
                ApplicationTypes.getApplicationType(getType());
        assert appType != null;
        return appType.getClassLoader();
    }

    public ApplicationType getApplicationType(){
        return ApplicationTypes.getApplicationType(getType());
    }
}
