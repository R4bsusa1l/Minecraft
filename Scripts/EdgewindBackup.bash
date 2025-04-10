#!/bin/bash

# vars
DB_USER="grafando"
DB_PASSWORD="Nbiwe!12"
DB_NAME="Edgewind"
BACKUP_DIR="/home/grafando/Edgewind/Backup"
TIMESTAMP=$(date +"%Y%m%d%H%M%S")
SESSION_NAME="edgewind"

# Stop the server via screen instance
screen -S "$SESSION_NAME" -X stuff "stop$(printf '\r')"

# Create backup directory if it doesn't exist
mkdir -p $BACKUP_DIR

# Backup the MySQL database
mysqldump -u$DB_USER -p$DB_PASSWORD $DB_NAME > $BACKUP_DIR/$DB_NAME-$TIMESTAMP.sql

# Compress the backup
gzip $BACKUP_DIR/$DB_NAME-$TIMESTAMP.sql

# Optionally, you can remove backups older than a certain period
# find $BACKUP_DIR -type f -name "*.gz" -mtime +7 -exec rm {} \;

echo "Backup completed: $BACKUP_DIR/$DB_NAME-$TIMESTAMP.sql.gz"
cp -r /home/grafando/Edgewind/Server $BACKUP_DIR
echo "Server files copied"

# Restart server
SESSION_NAME="edgewind"
cd /home/grafando/Edgewind/Server
screen -dmS "$SESSION_NAME" java -Xms12288M -Xmx12288M -XX:+AlwaysPreTouch -XX:+DisableExplicitGC -XX:+ParallelRefProcEnabled -XX:+PerfDisableSharedMem -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1HeapRegionSize=8M -XX:G1HeapWastePercent=5 -XX:G1MaxNewSizePercent=40 -XX:G1MixedGCCountTarget=4 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1NewSizePercent=30 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:G1ReservePercent=20 -XX:InitiatingHeapOccupancyPercent=15 -XX:MaxGCPauseMillis=200 -XX:MaxTenuringThreshold=1 -XX:SurvivorRatio=32 -Dusing.aikars.flags=https://mcflags.emc.gs -Daikars.new.flags=true -jar server.jar nogui

