/**
* Copyright (c) 2004-2005 jManage.org
*
* This is a free software; you can redistribute it and/or
* modify it under the terms of the license at
* http://www.jmanage.org.
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.jmanage.core.alert.delivery;

import org.jmanage.core.util.EmailUtils;
import org.jmanage.core.util.Loggers;
import org.jmanage.core.alert.AlertDelivery;
import org.jmanage.core.alert.AlertInfo;

import javax.mail.MessagingException;
import java.util.logging.Logger;

/**
 *
 * Date:  Jul 1, 2005
 * @author	Rakesh Kalra
 */
public class EmailDelivery implements AlertDelivery {

    private static final Logger logger = Loggers.getLogger(EmailDelivery.class);

    public void deliver(AlertInfo alertInfo) {
        try {
            EmailUtils.sendEmail(
                    alertInfo.getEmailAddress(),
                    "Alert: " + alertInfo.getAlertName(),
                    getEmailContent(alertInfo));
        } catch (MessagingException e) {
            logger.severe("Error sending alert email. Error: " + e.getMessage());
            EmailAlerts.getInstance().add(alertInfo);
        }
    }

    private String getEmailContent(AlertInfo alertInfo){
        StringBuffer buff = new StringBuffer();
        buff.append("Timestamp: ");
        buff.append(alertInfo.getFormattedTimeStamp());
        buff.append("\n");
        buff.append("Application Name: ");
        buff.append(alertInfo.getApplicationName());
        buff.append("\n");
        buff.append("Alert Name: ");
        buff.append(alertInfo.getAlertName());
        buff.append("\n");
        buff.append("Message: ");
        buff.append(alertInfo.getMessage());
        buff.append("\n");
        buff.append("Source: ");
        buff.append(alertInfo.getObjectName());
        return buff.toString();
    }
}
