#!/bin/sh

# Build JAR files
source bin/build_jar.sh

# Export ENV variables
source docker/setvars.sh

# Build image
docker build -t $IMAGE_NAME:$IMAGE_TAG -f docker/Dockerfile . || { echo "ERROR Failed to build!" >&2; exit 1; }
