#!/bin/bash
MYSQL_PW=SYS.SQL_PW
export DEBIAN_FRONTEND="noninteractive"
debconf-set-selections <<< "mysql-server mysql-server/root_password password $MYSQL_PW"
debconf-set-selections <<< "mysql-server mysql-server/root_password_again password $MYSQL_PW"
apt-get install -y mysql-server
apt-get install expect -y
SECURE_MYSQL=$(expect -c '
spawn /usr/bin/mysql_secure_installation
expect "Enter current password for root (enter for none):"
send "\r"
expect "Set root password?"
send "y\r"
expect "New password:"
send "$MYSQL_PW\r"
expect "Re-enter new password:"
send "$MYSQL_PW\r"
expect "Remove anonymous users?"
send "y\r"
expect "Disallow root login remotely?"
send "y\r"
expect "Remove test database and access to it?"
send "y\r"
expect "Reload privilege tables now?"
send "y\r"
expect eof')
echo "$SECURE_MYSQL"

service mysql restart