#!/bin/bash

PLUGIN_NAME="ViaVersion"
PLUGINS_DIR="/home/grafando/Edgewind/Server/plugins"
BACKUP_DIR="$PLUGINS_DIR/backup"

# --- Function to download the latest JAR ---
download_latest_jar() {
	OUTPUT_FILENAME="$PLUGINS_DIR/ViaVersion-latest.jar"

	API_URL="https://ci.viaversion.com/job/ViaVersion/lastStableBuild/api/json?tree=artifacts%5BfileName%2CrelativePath%5D"
	API_DATA=$(curl "$API_URL")

	LATEST_JAR_RELATIVE_PATH=$(echo "$API_DATA" | jq -r '.artifacts[0].relativePath') # Simplified jq

	if [ -z "$LATEST_JAR_RELATIVE_PATH" ]; then
		echo "Error: Could not find the latest ViaVersion JAR URL from the API."
		return 1
	fi

	BASE_ARTIFACT_URL="https://ci.viaversion.com/job/ViaVersion/lastStableBuild/artifact/"
	LATEST_JAR_URL="$BASE_ARTIFACT_URL$LATEST_JAR_RELATIVE_PATH"

	OUTPUT_FILENAME="$PLUGINS_DIR/${PLUGIN_NAME}-latest.jar" # Assuming PLUGINS_DIR is defined elsewhere

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


if [ -n "$current_jar_file" ]; then
echo "Backing up current $current_jar_file to $BACKUP_DIR/${PLUGIN_NAME}-${DATE}.jar"
mv "$current_jar_file" "$BACKUP_DIR/${PLUGIN_NAME}-${DATE}.jar"
fi

if download_latest_jar; then
mv "$PLUGINS_DIR/ViaVersion-latest.jar" "$PLUGINS_DIR/ViaVersion.jar"
chmod +x "$PLUGINS_DIR/ViaVersion.jar"
fi

exit 0
