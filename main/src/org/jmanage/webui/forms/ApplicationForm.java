package org.jmanage.webui.forms;

import org.jmanage.core.config.ApplicationConfig;

/**
 *
 * date:  Jun 25, 2004
 * @author	Rakesh Kalra
 */
public class ApplicationForm extends BaseForm {

    private String appId;
    private String name;
    private String host;
    private String port;
    private String username;
    private String password;

    public String getApplicationId() {
        return appId;
    }

    public void setApplicationId(String appId) {
        this.appId = appId;
    }

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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
}