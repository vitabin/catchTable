FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD
ENV MYSQL_DATABASE=$MYSQL_USER
ENV MYSQL_ROOT_USER=$MYSQL_USERNAME

ARG TZ="Asia/Seoul"
ENV TZ="Asia/Seoul"

RUN echo '[mysqld]\n\
character-set-server=utf8mb4\n\
collation-server=utf8mb4_unicode_ci\n\
default-authentication-plugin=mysql_native_password' > /etc/mysql/conf.d/custom.cnf
