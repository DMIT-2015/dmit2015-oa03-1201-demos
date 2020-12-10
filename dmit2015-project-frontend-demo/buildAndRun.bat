@echo off
call mvn clean package
call docker build -t ca.nait.dmit/dmit2015-project-frontend-demo .
call docker rm -f dmit2015-project-frontend-demo
call docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-project-frontend-demo ca.nait.dmit/dmit2015-project-frontend-demo