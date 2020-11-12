#!/bin/sh
if [ $(docker ps -a -f name=courseassignment-singleuser-formauth-databaseidentitystore | grep -w courseassignment-singleuser-formauth-databaseidentitystore | wc -l) -eq 1 ]; then
  docker rm -f courseassignment-singleuser-formauth-databaseidentitystore
fi
mvn clean package && docker build -t org.example/courseassignment-singleuser-formauth-databaseidentitystore .
docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-formauth-databaseidentitystore org.example/courseassignment-singleuser-formauth-databaseidentitystore
