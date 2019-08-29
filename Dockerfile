FROM maven:3.6.1-slim

RUN apt-get update

WORKDIR /ca-demo

COPY ASTDemos/pom.xml .

RUN mvn dependency:go-offline

COPY . /ca-demo

WORKDIR /ca-demo/ASTDemos

RUN mvn clean package assembly:single

WORKDIR /ca-demo/ChangeDistillerDemo

RUN mvn install:install-file -Dfile=lib/changedistiller-0.0.1-SNAPSHOT-jar-with-dependencies.jar -DgroupId=changedistiller -DartifactId=changedistiller -Dversion=1.0-SNAPSHOT -Dpackaging=jar

RUN man clean package assembly:single



