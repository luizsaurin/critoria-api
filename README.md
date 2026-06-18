<h1 align="center"><strong>CRITORIA API</strong></h1>

REST API that perform CRUD operations for Titles and Reviews. It takes inspiration from websites such as IMDb and Rotten Tomatoes, popular for reviewing movies and tv shows. This project has features such as:

- JPA
- flyway migrations
- PostgreSQL database
- unit tests
- web layers tests
- database integrations tests
- query with specifications
- custom repository using jdbcTemplate
- MapStruct mapper
- swagger documentation
- error handling

The topics below will explain how to setup and run this project. Additional informations will be available at the *docs* directory. Swagger UI can be accessed [here](http://localhost:5000/swagger-ui/index.html).

&nbsp;

# Setup database

Run docker compose to build and init database:
```
docker-compose up -d
```

Execute a shell inside the container and login using psql:
```
docker exec -it postgres psql -U postgres
```

List existing databases:
```
\l
```

Create main and test databases:
```
create database critoria_main;
create database critoria_test;
```

Exit:
```
\q
```

&nbsp;

# Setup and run project

Make to sure to have installed:
- Java JDK 25
- Maven 3.9.12

Install dependencies, build and run tests:
```
mvn install
```

The previous command will do all theses tasks. If required, build with:
```
mvn clean compile
```

And if also required, run tests:
```
mvn test
```

Finally, run project:
```
mvn spring-boot:run
```
