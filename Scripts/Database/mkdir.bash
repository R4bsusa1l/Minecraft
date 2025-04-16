#!/bin/bash

PASS = SYS.PASSWORD

#initial Install
apt-get update
apt-get upgrade
apt-get autoremove
apt-get clean

#install MySqls
export DEBIAN_FRONTEND="noninteractive"
debconf-set-selections <<< "mysql-server mysql-server/root_password password $PASS"
debconf-set-selections <<< "mysql-server mysql-server/root_password_again password $PASS"
apt-get install -y mysql-server
/bin/bash 
#install Jdk
/bin/bash ./InstallJdk.bash


#creating directory tree
USERNAME = SYS.USER

mkdir -p /home/$USERNAME/Edgewind/minecraftSys/{backup/server,build/spigot,build/mcron,server}