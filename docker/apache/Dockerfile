FROM httpd:2.4
RUN apt-get update && apt-get -y install less vim
COPY ./docker/apache/httpd.conf /usr/local/apache2/conf/httpd.conf
