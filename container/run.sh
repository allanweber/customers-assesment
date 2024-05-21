#!/bin/bash

mvn clean package

docker build -t customers_api:latest -f container/Dockerfile .

docker-compose -f ./container/docker-compose.yml up