#!/bin/bash
#find ./target/classes -name "*.properties"|xargs rm -f
#find ./target/classes -name "*.xml"|xargs rm -f
#find ./target/classes -name "*.dic"|xargs rm -f
#find ./target/classes/spring |xargs rm -f -r
#export CLASSPATH=$CURR_DIR/lib:$CURR_DIR:$JAVA_HOME/lib:$JAVA_HOME/jre/lib

tmp='./bin/resources'
tmp='./target/classes':$tmp
tmp='./target/tianchi-1.0-SNAPSHOT-jar-with-dependencies-without-resources/*':$tmp

CLASSPATH=$tmp:$CLASSPATH


echo $CLASSPATH
#JVM_ARGS="-Xmn48m -Xmx128m -Xms128m -XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=2"
#echo JVM_ARGS=$JVM_ARGS
#ulimit -n 400000
#echo "" > nohup.out
java $JVM_ARGS -classpath $CLASSPATH com.tianchi.lucene.LuceneUtil>>log/createIndex.log 2>&1 &