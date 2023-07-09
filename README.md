# energyXChange

This is the project for the exam of "Laboratory of advanced programming" held by professor Mecella, Sapienza 2023

## How to execute
1. Clone the repository with `git clone`
2. Move into the folder with `cd energyXChange`
3. Run the command `docker-compose up`

> :warning: **If you are using a macbook with M1/M2**: Decomment the line that changes the platform attribute for the db in the docker-compose.yml

## Team Members
- Simone Teglia, 1836794
- Tommaso Bersani, 1840825
- Lakshit Bahl, 2046611
- Caroline Wie, 2054403

# Architecture 

## Backend
The backend id developed with Java Spring Boot

## Frontend
We've used React to build our frontend, with some dependencies like
- Axios (for the REST API calls)
- Material UI
- Fontawesome Icons

## Middleware
The MOM is a rabbitMQ container that uses a queue to let buyers and sellers exchange messages

## Database
A simple MySql database
