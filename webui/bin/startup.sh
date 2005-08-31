################################################################################
#  Copyright 2004-2005 jManage.org. All rights reserved.
################################################################################
#!/bin/sh

. ./setenv.sh

if [ ! -n "$JMANAGE_CLASSPATH" ]; then
	echo "JMANAGE_CLASSPATH is not set."
	exit 0
fi

$JAVA_HOME/bin/java -ea -classpath $JMANAGE_CLASSPATH $DEBUG_OPTIONS -Djmanage.root=$JMANAGE_HOME -Djava.util.logging.config.file=$JMANAGE_HOME/config/logging.properties -Djava.security.auth.login.config=$JMANAGE_HOME/config/jmanage-auth.conf org.jmanage.webui.Startup $*
