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
package org.jmanage.monitoring.downtime.event;

import java.util.Date;

import org.jmanage.core.config.ApplicationConfig;

/**
 *
 * @author Rakesh Kalra
 */
public abstract class Event {
    
    private final ApplicationConfig appConfig;
    private final long time;
    
    public Event(ApplicationConfig appConfig){
        this.appConfig = appConfig;
        this.time = new Date().getTime();
    }
    
    public ApplicationConfig getApplicationConfig(){
        return appConfig;
    }

    public Long getTime() {
        return time;
    }
}
