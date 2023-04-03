#!/bin/bash

# Build
source bin/build_jar.sh

# Run
java -jar ./src/webchat/build/libs/webchat-1.0.jar
