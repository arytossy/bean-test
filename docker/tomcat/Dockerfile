FROM tomcat:9.0
RUN apt-get update && apt-get -y install less vim
COPY ./docker/tomcat/server.xml /usr/local/tomcat/conf/server.xml
