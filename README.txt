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
  
4) Once Image get created name (spring-boot:2.00) use below command to deploy image in the kubernetes
kubectl create -f deployment.yml
we are assuming before above command Kubernets services are running.
5) You can then list pods by running the command 
   kubectl get pods
