@echo off
call mvn clean package
call docker build -t org.example/courseassignment-singleuser-nosecurity .
call docker rm -f courseassignment-singleuser-nosecurity
call docker run -d -p 9080:9080 -p 9443:9443 --name courseassignment-singleuser-nosecurity org.example/courseassignment-singleuser-nosecurity