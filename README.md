# shopping-online-microservices
I've created those services in order to show you how implement a micro-service architecture, just a small services with CRUD operations :

The following are list of our services :
<ul>
<li>
  <b>Product Service :</b> Allow us to create and view products.
</li>
<li>
  <b> Order Service : </b>Allow us to order products.
</li>
<li>
  <b> Inventory Service :</b> Check if a product is in stock or not.
</li>
<li>
  <b> Notification Service :</b> Send a notification after creation of order.
</li>
<li>
  <b> Api Gateway :</b> Redirect calls to appropriate service.
</li>
<li>
  <b> Discovery Server :</b> Holds the information about all services, every service will register into the Eureka server and it can knows all the applications running on each port and IP address.
</li>
  
</ul>

Security is configured by Keycloak service and implemented on api-gateway, then all services are protected. 

A picture below shows architecture of micro-services :

<p align="center">
  <img src="https://github.com/najiboulhouch/shopping-online-microservices/blob/master/shopping-online-schema.drawio.png?raw=true" />
</p>

I've added a docker compose file that groups all services needed, to run application you should do these steps : 


 1. Run `mvn compile jib:build` to build the applications and pull the docker images to your Docker registry.
 2. Run `docker-compose up -d` to start the applications.
  

<br/>
<br/>

Feel free to downaload my code and make any updates.:fire:


<b>NAJIB</b> :v:

