#!/bin/bash

PLUGIN_NAME="ViaVersion"
PLUGINS_DIR="/home/grafando/Edgewind/Server/plugins"
CURRENT_JAR="$PLUGINS_DIR/${PLUGIN_NAME}*.jar" # Matches any ViaVersion JAR
BACKUP_DIR="$PLUGINS_DIR/backup"
DATE=$(date +%Y%m%d%H%M%S)

# --- Function to stop the Minecraft server ---
stop_server() {
  SESSION_NAME="edgewind"
  screen -S "$SESSION_NAME" -X stuff "stop$(printf '\r')"
  echo "Stopping Edgewind Server..."
  sleep 15 # Give the server time to shut down gracefully
}

# --- Function to start the Minecraft server ---
start_server() {
  /home/grafando/Edgewind/Scripts/EdgewindRebuild.sh
  echo "Starting Edgewind Server..."
}

# --- Function to download the latest JAR ---
download_latest_jar() {
  LATEST_JAR_URL="https://ci.viaversion.com/job/ViaVersion/lastStableBuild/artifact/build/libs/ViaVersion-*.jar" # **REPLACE THIS**
  OUTPUT_FILENAME="$PLUGINS_DIR/ViaVersion-latest.jar"

  echo "Downloading latest $PLUGIN_NAME JAR from $LATEST_JAR_URL..."
  wget -O "$OUTPUT_FILENAME" "$LATEST_JAR_URL"
  if [ $? -ne 0 ]; then
    echo "Error downloading latest $PLUGIN_NAME JAR."
    return 1
  fi
  return 0
}

# --- Main script ---

# Ensure backup directory exists
mkdir -p "$BACKUP_DIR"

# Find the current ViaVersion JAR
current_jar_file=$(find "$PLUGINS_DIR" -maxdepth 1 -name "${PLUGIN_NAME}*.jar")

echo "Current $PLUGIN_NAME JAR: $current_jar_file"
echo "New $PLUGIN_NAME build available. Proceeding with update."

stop_server

if [ -n "$current_jar_file" ]; then
echo "Backing up current $current_jar_file to $BACKUP_DIR/${PLUGIN_NAME}-${DATE}.jar"
mv "$current_jar_file" "$BACKUP_DIR/${PLUGIN_NAME}-${DATE}.jar"
fi

if download_latest_jar; then
mv "$PLUGINS_DIR/ViaVersion-latest.jar" "$PLUGINS_DIR/ViaVersion.jar"

# You might need a more sophisticated way to determine the final filename
# based on the downloaded JAR's actual version.

start_server


exit 0
