#!/bin/bash

# Función para esperar a que RabbitMQ esté disponible
wait_for_rabbitmq() {
    while ! /usr/bin/nc -z rabbitmq 5672; do
        echo "Esperando a que RabbitMQ esté disponible..."
        sleep 1
    done
}

# Esperar a que RabbitMQ esté disponible antes de iniciar cualquier aplicación
wait_for_rabbitmq

# Determinar qué aplicación se está iniciando
if [ "$APP_NAME" = "pjgl-micro-users" ]; then
    # Si es el servicio pjgl-micro-users, iniciar esa aplicación
    java -jar /app/pjgl-micro-users.jar
else
    # Si no se especifica ninguna aplicación válida, mostrar un mensaje de error
    echo "ERROR: Nombre de aplicación no válido."
    exit 1
fi
