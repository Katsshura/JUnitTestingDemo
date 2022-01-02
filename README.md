<h1 align="center">
     <a href="#"> JUnit5 Tests Demonstration </a>
</h1>

<h3 align="center">
    The main objective of this project is to show how to test applications using JUnit5 and its features, specially how to perform Parameterized Tests.
</h3>

---

## Project

Since the goal of this project is target to the test classes the project domain itself was planned to be very simple and very understandable. That said, we have only two controllers one for `User` context `[GET and POST]` and another one for `Task` context `[GET and POST]`. This behaviour is propagated to the others layers as well.


![Imgur](https://i.imgur.com/D3XX2kq.png)

---

## Running The Project and Tests

- Go to the project root folder
- Run command `docker-compose up -d` to run PostgreSQL on Docker.
- Run command `./gradlew migrateLocal` to run flyway migrations.
- Run command `./gradlew clean build` to build the project. Tests are always executed in this step.
- If you want to run only the tests you can run this command: `./gradlew :<module>:test` replace `<module>` for either `api` or `core` depending on which layer you want to test.
- `./gradlew :api:test` | `./gradlew :core:test`
- To execute Pitest on the project: `./gradlew pitest`
- To run the project api: `./gradlew :api:bootRun`



### Pre-requisites

Before you begin, you will need to have the following tools installed on your machine:
- [Git](https://git-scm.com).
- [Docker](https://docs.docker.com/desktop/).
- [Java 11 Sdk](https://www.oracle.com/java/technologies/downloads/#java11).

---

## Get in touch
[![Linkedin Badge](https://img.shields.io/badge/-LinkedIn-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/katsshura/)](https://www.linkedin.com/in/katsshura/)
[![Gmail Badge](https://img.shields.io/badge/-Gmail-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:xr.emerson@gmail.com)](mailto:xr.emerson@gmail.com)
