#!/bin/sh
if [ $(docker ps -a -f name=courseassignment-singleuser-basicauth-embeddedidentitystore | grep -w courseassignment-singleuser-basicauth-embeddedidentitystore | wc -l) -eq 1 ]; then
  docker rm -f courseassignment-singleuser-basicauth-embeddedidentitystore
fi
mvn clean package && docker build -t org.example/courseassignment-singleuser-basicauth-embeddedidentitystore .
docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-basicauth-embeddedidentitystore org.example/courseassignment-singleuser-basicauth-embeddedidentitystore
