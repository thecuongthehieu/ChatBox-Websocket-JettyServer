#!/bin/sh

# Export ENV variables
source docker/setvars.sh

CONTAINER_NAME="webchat"
docker run  --user 10000:10000 --name $CONTAINER_NAME -dp 6873:6873 $IMAGE_NAME:$IMAGE_TAG