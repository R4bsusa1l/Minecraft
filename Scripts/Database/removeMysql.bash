#!/bin/bash
systemctl stop mysql
apt purge mysql-server mysql-client mysql-common mysql-server-core-* mysql-client-core-*
rm -rf /etc/mysql /var/lib/mysql /var/log/mysql
apt autoremove
apt autoclean