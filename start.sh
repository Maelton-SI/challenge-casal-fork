#!/bin/bash

#Removes pre-existent containers
docker compose down

#Removes previous volume if existent
docker volume rm vehicle-rental-api-database-volume

#Builds project's image
docker build -t vehicle-rental-api:latest ./

#Creates and runs the project containers
docker compose up --build --force-recreate --remove-orphans --renew-anon-volumes -d