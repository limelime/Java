#!/bin/bash

# Get the path location of the executing script
## http://stackoverflow.com/questions/630372/determine-the-path-of-the-executing-bash-script
NET_XNGO_HOME="`dirname \"$0\"`"                    # relative
NET_XNGO_HOME="`( cd \"$NET_XNGO_HOME\" && pwd )`"  # absolutized and normalized
if [ -z "$NET_XNGO_HOME" ] ; then
  # error; for some reason, the path is not accessible
  # to the script (e.g. permissions re-evaled after suid)
  exit 1  # fail
fi

## Execute build.xml
ant -f ${NET_XNGO_HOME}/build.xml

cp ${NET_XNGO_HOME}/releases/net.xngo.utils.jar ${NET_XNGO_HOME}/../../FilesHub/FilesHub/lib