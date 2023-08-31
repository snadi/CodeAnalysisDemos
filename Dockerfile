FROM maven:3.9-eclipse-temurin-11

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

<<<<<<< HEAD

=======
>>>>>>> e0784e0ebf1f4890f47d65bb260d396b0e83b1ee
#change back to main working directory
WORKDIR /ca-demo



