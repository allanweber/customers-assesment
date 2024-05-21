#!/bin/bash

docker rm -f customers_api mysql-db

docker rmi -f customers_api:latest mysql:8.4

rm -rf target