#!/bin/sh
if [ $(docker ps -a -f name=courseassignment-singleuser-nosecurity | grep -w courseassignment-singleuser-nosecurity | wc -l) -eq 1 ]; then
  docker rm -f courseassignment-singleuser-nosecurity
fi
mvn clean package && docker build -t org.example/courseassignment-singleuser-nosecurity .
docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-nosecurity org.example/courseassignment-singleuser-nosecurity
