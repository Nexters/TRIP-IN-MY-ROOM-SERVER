#!/bin/bash

REPOSITORY=/home/ubuntu/back-end/product
PROJECT_NAME=tripinmyroom

CURRENT_PID=$(pgrep java)
echo "> 이전 product pid $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

bash ../run.sh


echo "> Deployment finished."
