./mvnw.cmd clean package -D skipTests=true
docker-compose exec tomcat cp /usr/local/tomcat/work/bean-test.war /usr/local/tomcat/webapps