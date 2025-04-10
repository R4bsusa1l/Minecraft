#!/bin/bash
USER='$SYS.USER'
MYSQL_PW='$SYS.PW'
DUMPFILE='Edgewind-20240923110942.sql.gz'

apt-get update
apt-get upgrade
apt-get autoremove
apt-get clean

# Install Screen
apt-get install screen

mkdir /home/$USER/Installation
mkdir /home/$USER/Edgewind
cp -r /home/$USER/haven/mcServer /home/$USER/Installation
cp -r /home/$USER/haven/Scripts /home/$USER/Installation

# Script installation & cron configuration
apt-get install cron
cp /home/$USER/haven/Scripts/crontab etc
mkdir /etc/cron.edgewind
cp /home/$USER/haven/Scripts/EdgewindRebuild.bash /etc/cron.edgewind
cp /home/$USER/haven/Scripts/EdgewindBackup.bash /etc/cron.edgewind

# JDK installation
/bin/bash ./InstallJdk.bash

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
cd /home/$USER/Installation/mcServer

mysql -u root -p$MYSQL_PW -e "CREATE USER 'grafando'@'%' identified by 'Nbiwe!12';"
mysql -u root -p$MYSQL_PW -e "GRANT ALL PRIVILEGES on *.* to 'grafando'@'%' with grant option;"
mysql -u root -p$MYSQL_PW -e "FLUSH PRIVILEGES;"

mysql -u grafando -p$MYSQL_PW -e "CREATE DATABASE Edgewind;"
zcat $DUMPFILE | mysql -u grafando -p$MYSQL_PW Edgewind

# Firewall config
ufw enable
ufw allow 25565/tcp

cp -r /home/$USER/Installation/mcServer/Server /home/$USER/Edgewind
cd /home/$USER/Edgewind/Server
java -Xms12288M -Xmx12288M -XX:+AlwaysPreTouch -XX:+DisableExplicitGC -XX:+ParallelRefProcEnabled -XX:+PerfDisableSharedMem -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1HeapRegionSize=8M -XX:G1HeapWastePercent=5 -XX:G1MaxNewSizePercent=40 -XX:G1MixedGCCountTarget=4 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1NewSizePercent=30 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:G1ReservePercent=20 -XX:InitiatingHeapOccupancyPercent=15 -XX:MaxGCPauseMillis=200 -XX:MaxTenuringThreshold=1 -XX:SurvivorRatio=32 -Dusing.aikars.flags=https://mcflags.emc.gs -Daikars.new.flags=true -jar server.jar nogui






