#!/bin/bash
./gradlew clean build
docker build . -t  anenahov/currency-cbr-adapter:1.0.0