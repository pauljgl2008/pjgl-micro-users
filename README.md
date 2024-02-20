# pjgl-micro-users
pjgl-micro-users spring rest, security, actuator, data, config client

https://docs.spring.io/spring-security/reference/servlet/architecture.html

Docker:
- $ apt-get install docker-buildx-plugin 
- $ docker buildx build -t pjgl-micro-users .
Saber las ipaddress de los contenedores corriendo
- $ docker inspect -f '{{.Name}} - {{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps -q)

Correr la imagen de RabbitMQ antes de iniciar el micro:
- $ docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

Prometheus:
- $ docker pull prom/prometheus
- $ docker run -p 9090:9090 prom/prometheus
- $ docker run -p 9090:9090 -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus

Grafana opensource:
- $ docker pull grafana/grafana-oss
- $ docker run -d -p 3000:3000 --name=grafana -e "GF_INSTALL_PLUGINS=grafana-clock-panel, grafana-simple-json-datasource" grafana/grafana-oss
