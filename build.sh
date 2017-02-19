#!/bin/bash
set -e

mvn -version

PROPERTIES="$PROPERTIES -Duser.country=US -Duser.language=en"
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=512m"

eval exec mvn -P ${JPAPROVIDER},${RDBMS} install -am $PROPERTIES
