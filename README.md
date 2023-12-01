# Customer-Backend

<h1>Video Description: </h1>

```
will add soon
```

<h1>About: </h1>
<p>
Customer CRUD using tokan generated after login.
</p>

<h1>Installation</h1>

<h3>1. Clone the Repository</h3>

```
https://github.com/RheaSidana/Customer-Backend.git
```

```
cd Customer-Backend
```

<h3>2. Build the project</h3>

```
./gradlew clean build
```

<h3>3. PostgreSQL </h3>
<h4>Edit the username and password details in the "build.gradle" file and "application.yml" file</h4>
</br></br>
<h4>Create the db</h4>

```
create database customer;
```

<h4>Connect the db</h4>

```
\c customer;
```

<h3>4. Migrate Tables</h3>

```
./gradlew flywayMigrate
```

<h4>Postgres: check the tables are migrated using: </h4>

```
\dt
```

<h3>5. Run the application: </h3>

```
./gradlew bootRun
```

<h3>6. API's </h3>
<a href="https://documenter.getpostman.com/view/28378586/2s9YeK3Vc7">
Postman Documentation
</a>
