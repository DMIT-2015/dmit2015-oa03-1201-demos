@echo off
call mvn clean package
call docker build -t org.example/courseassignment-singleuser-customformauth-customidentitystore .
call docker rm -f courseassignment-singleuser-customformauth-customidentitystore
call docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-customformauth-customidentitystore org.example/courseassignment-singleuser-customformauth-customidentitystore