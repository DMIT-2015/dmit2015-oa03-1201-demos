@echo off
call mvn clean package
call docker build -t org.example/courseassignment-singleuser-basicauth-embeddedidentitystore .
call docker rm -f courseassignment-singleuser-basicauth-embeddedidentitystore
call docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-basicauth-embeddedidentitystore org.example/courseassignment-singleuser-basicauth-embeddedidentitystore