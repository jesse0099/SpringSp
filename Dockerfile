#Building Stage [Maven Alpine based image]
FROM maven:3.8.4-jdk-8-slim AS MAVEN_BUILD

#Para que sepan el nombre del manco
#MAINTAINER Esta en desuso, LABEL MAINTAINER le ha sustituido
LABEL MAINTAINER: Jese Chavez

#Copia el archivo de dependencias y el fuente 
#a un folder build dentro del contenedor, si no existe, lo crea
#el / del final en el path del contenedor indica que sera un directorio
#no colocarlo puede generar "cannot copy to non-directory"
COPY pom.xml /build/
COPY src /build/src/

#Setea el directorio de trabajo, si no existe, lo crea
#Todos los comandos que no especifiquen lo contrario 
#se ejecutaran aqui
WORKDIR /build
#Maven ejecuta el ciclo de vida hasta package
#https://stackoverflow.com/questions/55226062/maven-fail-in-spring-project-build-trying-to-establish-connection-with-database
RUN mvn package -DskipTests

#Java JRE basado en alpine
FROM openjdk:8-jre-alpine

WORKDIR /app 

COPY --from=MAVEN_BUILD /build/target/springsp-0.0.1-SNAPSHOT.war /app/

#The ENTRYPOINT specifies a command that will always be executed when the container starts. 
#The CMD specifies arguments that will be fed to the ENTRYPOINT.

ENTRYPOINT ["java","-jar","springsp-0.0.1-SNAPSHOT.war"]

