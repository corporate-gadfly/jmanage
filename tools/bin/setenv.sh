################################################################################
#  Copyright 2004-2005 jManage.org. All rights reserved.
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

JMANAGE_CLASSPATH=

for i in $JMANAGE_HOME/lib/*.jar
do
    JMANAGE_CLASSPATH="$i:$JMANAGE_CLASSPATH"
done

echo classpath=$JMANAGE_CLASSPATH