#!/bin/bash

echo "Hello from Bash script!"

filePath="$1"

echo "Received file path: $filePath"
cd "$filePath"
pwd

# Initialize Git repository if not already initialized
if git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  # Add all files to Git
  git add .
fi

echo "All files added to Git."
