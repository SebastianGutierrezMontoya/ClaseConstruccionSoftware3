FROM tomcat:10.1-jdk17

# Elimina aplicaciones por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia tu archivo WAR al contenedor
COPY target/ces3-app.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]