package org.jmanage.test.jsr160;

import org.jmanage.core.util.Loggers;
import org.jmanage.test.mbeans.Configuration;
import org.jmanage.test.mbeans.ObjectNames;
import org.jmanage.test.mbeans.Calculator;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * date:  Dec 19, 2004
 * @author	Rakesh Kalra
 */
public class JMXHelper {

    private static final Logger logger = Loggers.getLogger(JMXHelper.class);

    // the mbeanServer used by the application
    private static MBeanServer mbeanServer;

    // the lock used to obtain the first mbeanServer
    private static Object mbeanServerLock = new Object();

    /**
     * Obtains a reference to the MBean server. If at least one
     * MBean server already exists, then a reference to that MBean
     * server is returned. Otherwise a new instance is created.
     */
    private static MBeanServer getMBeanServer() {

        if (mbeanServer == null) {
            mbeanServer = _getMBeanServer();
        }
        return mbeanServer;
    }

    /**
     * registers the MBeans used in the Upromise application
     * and starts the HTML Adaptor
     */
    public static void registerMBeans() {

        /* Configuration */
        registerMBean(new Configuration(), ObjectNames.CONFIGURATION);
        /* Configuration */
        registerMBean(new Calculator(), ObjectNames.CALCULATOR);
        /* start RMI connector */
        startJMXConnectorServer();
    }

    public static void registerMBean(Object instance, String objName) {
        try {
            ObjectName objectName = new ObjectName(objName);
            MBeanServer server = getMBeanServer();
            server.registerMBean(instance, objectName);
            logger.info("registered MBean: " + objName);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to register MBean: " + objName, e);
        }
    }

    /* start JSR160 RMI connector*/
    private static void startJMXConnectorServer(){
        try {
            final int port = 9999;
            /* attempt to start the rmi registry */
            startRMIRegistry(port);
            /* start the connector server */
            JMXServiceURL url = new JMXServiceURL(
              "service:jmx:rmi:///jndi/rmi://localhost:" + port + "/odt");
            JMXConnectorServer cs =
                 JMXConnectorServerFactory.newJMXConnectorServer(url,
                 null, getMBeanServer());
            cs.start();
            logger.info("JMXConnectorServer started. URL: " + url.toString());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failure while starting RMI connector", e);
        }
    }

    /* start rmi registry */
    private static void startRMIRegistry(int port){
        try {
            LocateRegistry.createRegistry(port);
            logger.info("rmiregistry started on: " + port);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error starting rmiregistry on " + port, e);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * look up or create a MBeanServer
     * @return instance of MBeanServer
     */
    private static MBeanServer _getMBeanServer() {

        // synchronize to make sure that we don't create multiple MBeanServers
        synchronized (mbeanServerLock) {
            // if we got the instance in the mean time, just return it
            if (mbeanServer != null)
                return mbeanServer;
            return MBeanServerFactory.createMBeanServer();
        }
    }
}