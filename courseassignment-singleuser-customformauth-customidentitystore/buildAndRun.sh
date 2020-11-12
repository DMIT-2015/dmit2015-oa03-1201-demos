#!/bin/sh
if [ $(docker ps -a -f name=courseassignment-singleuser-customformauth-customidentitystore | grep -w courseassignment-singleuser-customformauth-customidentitystore | wc -l) -eq 1 ]; then
  docker rm -f courseassignment-singleuser-customformauth-customidentitystore
fi
mvn clean package && docker build -t org.example/courseassignment-singleuser-customformauth-customidentitystore .
docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-customformauth-customidentitystore org.example/courseassignment-singleuser-customformauth-customidentitystore
