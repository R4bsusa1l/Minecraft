#!/bin/bash

SESSION_NAME="edgewind"

# Stop the server via screen instance
screen -S "$SESSION_NAME" -X stuff "stop$(printf '\r')"

echo "Edgewind Server Interupted"