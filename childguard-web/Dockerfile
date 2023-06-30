#base image: linux alpine os with open jdk 8
#Dockerfile - cmd vao folder chua Dockerfile
FROM eclipse-temurin:11-jre-alpine
#copy jar from local into docker image
COPY childguard-1.0.jar childguard-1.0.jar
#command line to run jar
ENTRYPOINT ["java","-jar","/childguard-1.0.jar"]
