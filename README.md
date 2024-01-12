# Message Queue Example With RabbitMQ and Docker

The project consists of 3 projects

- A Maven library for some utilities providing an abstraction to accessing the message queue in `messaging-utilities-3.4` which are installled using `mvn install` through the build script
- The facade microservice in `facade-service` which communicates with other services using MQ. The service offers a REST interface that is used by the end-to-end tests.
- The end-to-end tests in `end-to-end-queue-demo` ignored for now...

### The main `docker-compose.yml` file is in the `end-to-end-queue-demo`. Only run this file after building an image called payment-service!!!!

To know how the project is build, deployed, and tested, inspect `build_and_run.sh`.

Note that the end-to-end tests may get stuck. The reason is, that one of the services has not finished starting yet, or has crashed because RabbitMq was not yet ready to accept connections. To make sure that all services are running, you can run `docker-compose up -d` repeatedly in the `end-to-end-queue-demo` directory until all services show that they are running.
Don't forget to check the logs from `facade-service` to inspect any errors.