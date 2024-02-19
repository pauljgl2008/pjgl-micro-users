FROM maven:3.9.6-ibm-semeru-21-jammy as build

# Copia el código fuente de la aplicación a la imagen
COPY . /app

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Construye la aplicación con Maven
RUN mvn clean package

# Usa una imagen base de OpenJDK para Java 21 con Alpine Linux
FROM openjdk:23-jdk-slim AS final

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR de tu aplicación Spring Boot desde la imagen de construcción
COPY --from=build /app/target/pjgl-micro-users-0.0.1-SNAPSHOT.jar /app/pjgl-micro-users.jar

# Expone el puerto en el que tu aplicación Spring Boot está escuchando
EXPOSE 8080

# Nombre de la imagen
LABEL app="pjgl-config-server"

# Comando para ejecutar la aplicación Spring Boot al iniciar el contenedor
CMD ["java", "-jar", "/app/pjgl-micro-users.jar"]
