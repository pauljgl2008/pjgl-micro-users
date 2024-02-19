# Usa una imagen base de OpenJDK para Java 22
FROM openjdk:22-ea-34 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR de tu aplicación Spring Boot (asegúrate de que se encuentre en el mismo directorio que este Dockerfile)
COPY target/pjgl-micro-users-0.0.1-SNAPSHOT.jar /app/pjgl-micro-users.jar

# Expone el puerto en el que tu aplicación Spring Boot está escuchando (el mismo que especificas en tu aplicación)
EXPOSE 8080

# COPY entrypoint.sh /app/entrypoint.sh

# RUN chmod +x /app/entrypoint.sh

# ENTRYPOINT ["/app/entrypoint.sh"]

# Nombre de la imagen
LABEL app="pjgl-micro-users"

# Comando para ejecutar la aplicación Spring Boot al iniciar el contenedor
CMD ["java", "-jar", "/app/pjgl-micro-users.jar"]
