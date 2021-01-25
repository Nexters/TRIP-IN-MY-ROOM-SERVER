#!/bin/bash

REPOSITORY=/home/ubuntu/back-end/product
PROJECT_NAME=tripinmyroom
NOW_DATETIME=`date +"%Y-%m-%dT%T"`

CURRENT_PID=$(pgrep java)
echo "> 이전 product pid $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/build/libs/*.jar | tail -n 1)
chmod +x $JAR_NAME

echo "> JAR name: $JAR_NAME"

nohup java -jar \
    -Dspring.profiles.active=release \
    $JAR_NAME > $REPOSITORY/nohup-$NOW_DATETIME.out 2>&1 &


echo "> Deployment finished."
