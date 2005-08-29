################################################################################
#  Copyright 2004-2005 jManage.org
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
################################################################################
#!/bin/sh

if [ ! -n "$JAVA_HOME" ]; then
	echo "Please set JAVA_HOME environment variable. JAVA_HOME must point to a JDK 1.4 installation directory."
    exit 0
fi

if [ ! -n "$JMANAGE_HOME" ]; then
    JMANAGE_HOME=..
fi

if [ ! -f "$JMANAGE_HOME/config/jmanage.properties" ]; then
    echo "Please set JMANAGE_HOME environment variable pointing to jManage installation directory."
    exit 0
fi

if [ ! -n "$JMANAGE_LIB_ROOT" ]; then
	JMANAGE_LIB_ROOT=$JMANAGE_HOME
fi

LIB_JARS=$JMANAGE_LIB_ROOT/lib/javax.servlet.jar:$JMANAGE_LIB_ROOT/lib/org.mortbay.jetty.jar:$JMANAGE_LIB_ROOT/lib/jasper-compiler.jar:$JMANAGE_LIB_ROOT/lib/jasper-runtime.jar:$JMANAGE_LIB_ROOT/lib/ant.jar:$JMANAGE_LIB_ROOT/lib/jdom.jar:$JMANAGE_LIB_ROOT/lib/xml-apis.jar:$JMANAGE_LIB_ROOT/lib/commons-beanutils.jar:$JMANAGE_LIB_ROOT/lib/commons-logging.jar
EXT_JARS=
JMANAGE_CLASSPATH=$LIB_JARS:$EXT_JARS:$JMANAGE_HOME/classes

$JAVA_HOME/bin/java -ea -classpath $JMANAGE_CLASSPATH $DEBUG_OPTIONS -Djmanage.root=$JMANAGE_HOME -Djava.util.logging.config.file=$JMANAGE_HOME/config/logging.properties -Djava.security.auth.login.config=$JMANAGE_HOME/config/jmanage-auth.conf org.jmanage.webui.Startup $*