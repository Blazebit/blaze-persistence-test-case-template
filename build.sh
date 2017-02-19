#!/bin/bash
set -e

${MVN_BIN} -version

PROPERTIES="$PROPERTIES -Duser.country=US -Duser.language=en"
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=512m"

eval exec ${MVN_BIN} -P ${JPAPROVIDER},${RDBMS} install -am $PROPERTIES
