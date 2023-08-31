FROM maven:3.6.3-jdk-11

RUN apt-get update && \
	apt-get install vim -y

WORKDIR /ca-demo

#install all pom dependencies
COPY ASTDemos/pom.xml .
RUN mvn dependency:go-offline
COPY spoon-examples/pom.xml .
RUN mvn dependency:go-offline

COPY . /ca-demo

#compile Eclipse JDT demo
WORKDIR /ca-demo/ASTDemos
RUN mvn clean package assembly:single

#compile GumTreeDiffDemo
WORKDIR /ca-demo/GumTreeDiffDemo
RUN mvn clean package assembly:single

#change back to main working directory
WORKDIR /ca-demo



