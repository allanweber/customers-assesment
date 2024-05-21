# Assignment Overview

Register and overview of customers.

## Getting Started

### Prerequisites

* Java 11
* Maven
* Docker

### Running the application

You have the following options to run the application:

#### Everything at once

It will build the package, create a docker container, build the application image and run it all with docker compose.

```bash
./container/run.sh
```

#### Cleaning up

It will remove the docker containers and images to clean up your environment. **Will remove the mysql container and image as well, so be careful.**

```bash
./container/clean.sh
```

#### Running each step manually

If you want to run each step manually, you can follow the steps below.

#### Database

```bash
docker run -d --name mysql-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=customers -e MYSQL_USER=customer -e MYSQL_PASSWORD=customer -p 3306:3306 mysql:8.4
```

#### Build the application

```bash
mvn clean package
```

#### Run the application

```bash
java -jar target/customers-1.0.0.jar
```

### Testing the application

Yuo can import the Postman collection located at [./postman](./postman) folder.

### Other useful commands

#### Generate a new JWT secret

```bash
openssl rand -base64 16
```

#### Simulate Rate Limit exceeded

You can use any URL to simulate the rate limit exceeded.

```bash
for ((i=1;i<=5;i++)); do curl -v  "http://localhost:8080/health"; done
```

## Important Dependencies

* Spring Boot
* Spring Security
* Spring Data JDBC
* MySQL
* Test containers
* PMD
* Jacoco

## Features

- [X] Register customer with basic details: name, address, date of birth, ID document and username.
- [X] Customer must provide a unique username.
- [X] Default passwordGenerator must be generated.
- [X] Only customers above 18 years of age are allowed to register and create an account.
- [X] Only customers from the Netherlands and Belgium are allowed to register and create an account.
- [X] It should be possible to add new countries in the allowed list ‘easily’.
- [X] Once the customer is registered, Unique IBAN account number should be automatically assigned as per NL IBAN format.
- [X] Authenticate customer with username and password.
- [X] Customer must be able to see some account details like account balance and type of account (account number, account type, balance, and currency).
- [X] Create Swagger OpenAPI documentation.
- [X] Postman collection which covers different scenarios
- [X] Assume existing database is legacy and cannot handle more than 2 requests per second. Make sure API is not putting load on DB.
- [X] Code coverage more than 60 %
- [X] Run REST API as container (Docker) and provide docker-compose file.
