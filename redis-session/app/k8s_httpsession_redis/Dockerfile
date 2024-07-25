# Use the official Tomcat base image
FROM tomcat:10.1-jdk17

# Remove the default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy the WAR file to the webapps directory
COPY target/k8s_httpsession-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
