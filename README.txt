# To build the application
maven clean install

# To access the service endpoint
use port 8080

# To Dockerize the application
1) Go to the folder containing Dockerfile.
2) Sudo docker build -t name:tag .  ( sudo docker build -t spring-boot:2.0 . )
3) Start the docker container spring-boot:2.0, run the /usr/local/runme/app.jar file during startup
  Add run -d to start the container in detach mode – run the container in the background.
  Add run -p to map ports.

  sudo docker run -d -p 8080:8080 -t spring-boot:2.0