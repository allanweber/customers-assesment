# Assignment Overview

Register and overview of customers.

## Getting Started

### Prerequisites

* Java 11
* Maven
* Docker

### Running the application

#### Database

```bash
docker run -d --name mysql-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=customers -e MYSQL_USER=customer -e MYSQL_PASSWORD=customer -p 3306:3306 mysql:8.4
```

## Important Dependencies

* Spring Boot
* Spring Security
* Spring Data JDBC
* MySQL
* Test containers
* PMD
* Jacoco
* Lombok

## Features

- [X] Register customer with basic details: name, address, date of birth, ID document and username.
- [X] Customer must provide a unique username.
- [X] Default passwordGenerator must be generated.
- [X] Only customers above 18 years of age are allowed to register and create an account.
- [ ] Only customers from the Netherlands and Belgium are allowed to register and create an account.
- [ ] It should be possible to add new countries in the allowed list ‘easily’.
- [ ] Once the customer is registered, Unique IBAN account number should be automatically assigned as per NL IBAN format.
- [ ] Authenticate customer with username and passwordGenerator.
- [ ] Customer must be able to see some account details like account balance and type of account.
- [ ] Create Swagger OpenAPI documentation.
- [ ] Postman collection which covers different scenarios
- [ ] Assume existing database is legacy and cannot handle more than 2 requests per second. Make sure API is not putting load on DB.
- [ ] Code coverage more than 60 %
- [ ] Run REST API as container (Docker) and provide docker-compose file.