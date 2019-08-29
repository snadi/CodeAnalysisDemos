FROM maven:3.6.1-slim

RUN apt-get update && \
	apt-get install vim -y

WORKDIR /ca-demo

#install all pom dependencies
COPY ASTDemos/pom.xml .
RUN mvn dependency:go-offline
COPY spoon-examples/pom.xml .

COPY . /ca-demo

#compile Eclipse JDT demo
WORKDIR /ca-demo/ASTDemos
RUN mvn clean package assembly:single

#compile ChangeDistiller demo
WORKDIR /ca-demo/ChangeDistillerDemo

RUN mvn install:install-file -Dfile=lib/changedistiller-0.0.1-SNAPSHOT-jar-with-dependencies.jar -DgroupId=changedistiller -DartifactId=changedistiller -Dversion=1.0-SNAPSHOT -Dpackaging=jar

RUN mvn clean package assembly:single

#install SrcML

WORKDIR /ca-demo/SrcML

RUN dpkg -i srcML-Ubuntu12.04-64.deb

#change back to main working directory
WORKDIR /ca-demo



