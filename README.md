# Dental-link-app

# Dental Link Application Deployment with Docker

This guide explains how to deploy the **Dental Link** application using Docker. It covers building the project, setting up the MySQL database, and running the application in a Docker container.

## Prerequisites

Make sure you have the following installed on your machine:
- Docker
- SBT (Scala Build Tool)
- MySQL client (optional, for database interaction)

---

## Step 1: Build the Project

First, clean and assemble the Scala project into a fat JAR file.

```bash
sbt clean assembly
```

This will generate the necessary JAR file in the `target/scala-2.13/` directory.

---

## Step 2: Build the Docker Image

Once the project is assembled, build the Docker image for the Spring Boot application.

```bash
docker build -t dental-link-app .
```

This command creates a Docker image named `dental-link-app`.

---

## Step 3: Create a Docker Network

Create a Docker network to allow the MySQL database and the application to communicate.

```bash
docker network create dental-network
```

---

## Step 4: Run MySQL in a Docker Container

Run a MySQL container, configured with the necessary environment variables, and mount a volume to persist the data.

```bash
docker run --name mysqlDB --network dental-network \
    -e MYSQL_ROOT_PASSWORD=Password1234 \
    -e MYSQL_USER=malek \
    -e MYSQL_PASSWORD=Password1234 \
    -e MYSQL_DATABASE=dentLinkDB \
    -p 3306:3306 \
    -d -v /home/malek/repos/mysqlDB-volume:/var/lib/mysql \
    -e TZ=Europe/Stockholm \
    mysql:latest
```

- `--network dental-network`: Connects MySQL to the Docker network.
- `-v /home/malek/repos/mysqlDB-volume:/var/lib/mysql`: Persists database data on the host system.

---

## Step 5: Run PHPMyAdmin (Optional)

Run PHPMyAdmin to interact with the MySQL database via a web interface.

```bash
docker run --name phpmyadmin --network dental-network \
    -d --link mysqlDB:db -p 8080:80 phpmyadmin/phpmyadmin
```

Access PHPMyAdmin in your browser at: [http://localhost:8080](http://localhost:8080).

---

## Step 6: Run the Application in Docker

Now, run the **Dental Link** application inside a Docker container, linked to the MySQL container.

### Option 1: Run in Foreground

```bash
docker run --name dental-link-app --network dental-network -p 8081:8081 dental-link-app
```

### Option 2: Run in Detached Mode

```bash
docker run -d --name dental-link-app --network dental-network -p 8081:8081 dental-link-app
```

The application will be reachable at [http://localhost:8081](http://localhost:8081).

---

## API Access

Once the application is running, you can access the API at:

```bash
http://localhost:8081
```

Use tools like [Insomnia](https://insomnia.rest/) or [Postman](https://www.postman.com/) to send API requests to the running service.

---

## Additional Notes

- Ensure that your Spring Boot application is configured to connect to the MySQL container using the correct environment variables or connection settings (`jdbc:mysql://mysqlDB:3306/dentLinkDB`).
- Use `docker logs <container-name>` to view logs if any issues arise.
