# WebServicesTest
# Build:
docker run -it --rm --name WebApplication -v "$PWD":/usr/src/WebApplication -w /usr/src/WebApplication maven:latest mvn clean install

# Run containers
docker-compose up

Now just go to http://localhost:8080/WebServicesTest (exposed port can be changed in tomcat section of .yml file) to see how API is working!
