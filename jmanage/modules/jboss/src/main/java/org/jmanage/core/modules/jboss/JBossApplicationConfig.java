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
package org.jmanage.core.modules.jboss;

import org.jmanage.core.config.ApplicationConfig;
import org.jmanage.core.util.Loggers;

import java.util.logging.Logger;

/**
 *
 * date:  Oct 30, 2004
 * @author	Prem
 */
public class JBossApplicationConfig extends ApplicationConfig {
    private static final Logger logger =
            Loggers.getLogger(JBossApplicationConfig.class);

    public JBossApplicationConfig() {
        super();
        setPort(new Integer(1099));
    }

    public void setURL(String url){
        /* ignore the url */
        logger.fine("Ignoring url=" + url);
    }

    public String getURL() {
        return "jnp://" + getHost() + ":" + getPort();
    }
}
