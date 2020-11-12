@echo off
call mvn clean package
call docker build -t org.example/courseassignment-singleuser-formauth-databaseidentitystore .
call docker rm -f courseassignment-singleuser-formauth-databaseidentitystore
call docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-formauth-databaseidentitystore org.example/courseassignment-singleuser-formauth-databaseidentitystore