#!/bin/bash

PLUGIN_NAME="ViaVersion"
API_URL="https://ci.viaversion.com/job/ViaVersion/lastStableBuild/api/json?tree=artifacts%5BfileName%2CrelativePath%5D"
API_DATA=$(curl "$API_URL")

echo "--- CURL OUTPUT ---"
echo "$API_DATA"
echo "-------------------"

echo "--- API DATA PARSED BY JQ ---"
echo "$API_DATA" | jq
echo "-----------------------------"

LATEST_JAR_RELATIVE_PATH=$(echo "$API_DATA" | jq -r '.artifacts[0].relativePath') # Simplified jq

if [ -z "$LATEST_JAR_RELATIVE_PATH" ]; then
    echo "Error: Could not find the latest ViaVersion JAR URL from the API."
    exit 1
fi

BASE_ARTIFACT_URL="https://ci.viaversion.com/job/ViaVersion/lastStableBuild/artifact/"
LATEST_JAR_URL="$BASE_ARTIFACT_URL$LATEST_JAR_RELATIVE_PATH"

OUTPUT_FILENAME="$PLUGINS_DIR/${PLUGIN_NAME}-latest.jar" # Assuming PLUGINS_DIR is defined elsewhere

echo "Downloading latest $PLUGIN_NAME JAR from $LATEST_JAR_URL..."
wget -O "$OUTPUT_FILENAME" "$LATEST_JAR_URL"
if [ $? -ne 0 ]; then
    echo "Error downloading latest $PLUGIN_NAME JAR."
    exit 1
fi

exit 0