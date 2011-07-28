#!/bin/sh

rggRoot=$1
scriptingCorePath=$2

echo " "
echo "Copy RGG.jar into the lib-folder of scripting core"
cp $rggRoot/RGG.jar $scriptingCorePath/lib/
echo "Done."