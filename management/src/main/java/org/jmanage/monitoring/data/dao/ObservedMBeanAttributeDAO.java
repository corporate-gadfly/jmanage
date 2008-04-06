/**
 * jManage Application Management Platform 
 * Copyright 2004-2008 jManage.org
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details. 
 */
package org.jmanage.monitoring.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.jmanage.core.config.ApplicationConfig;
import org.jmanage.core.config.ApplicationConfigManager;
import org.jmanage.monitoring.data.model.ObservedMBeanAttribute;
import org.jmanage.util.db.DBUtils;

/**
 * @author rkalra
 *
 */
public class ObservedMBeanAttributeDAO {

	public List<ObservedMBeanAttribute> find(ApplicationConfig appConfig){
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try{
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement("SELECT id, application_id, " +
            		"mbean_name, attribute_name " +
                    "FROM MBEAN_ATTRIBUTE" +
                    " WHERE application_id=?");
            stmt.setString(1, appConfig.getApplicationId());
            rset = stmt.executeQuery();
            return resultSetToModel(rset);
        }catch(SQLException e){    
            throw new RuntimeException(e);
        }finally{
            DBUtils.close(rset);
            DBUtils.close(stmt);
            DBUtils.close(conn);
        }
	}

	/**
	 * @return
	 */
	public List<ObservedMBeanAttribute> findAll() {
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try{
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement("SELECT id, application_id, " +
            		"mbean_name, attribute_name " +
                    "FROM MBEAN_ATTRIBUTE");
            rset = stmt.executeQuery();
            return resultSetToModel(rset);
        }catch(SQLException e){    
            throw new RuntimeException(e);
        }finally{
            DBUtils.close(rset);
            DBUtils.close(stmt);
            DBUtils.close(conn);
        }
	}

	private List<ObservedMBeanAttribute> resultSetToModel(ResultSet rset) throws SQLException{
        List<ObservedMBeanAttribute> observedMBeanAttributes = 
        	new LinkedList<ObservedMBeanAttribute>();
		while(rset.next()){
			final String appId = rset.getString(2);
			ApplicationConfig appConfig = 
				ApplicationConfigManager.getApplicationConfig(appId);
			if(appConfig == null){
				throw new RuntimeException("No application config found for appId: " + appId);
			}
        	observedMBeanAttributes.add(
        			new ObservedMBeanAttribute(
        					rset.getLong(1), 
        					appConfig, 
        					rset.getString(3), 
        					rset.getString(4)));
        }
        return observedMBeanAttributes;
	}
}