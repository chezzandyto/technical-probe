## Technical Probe

This application have two principal java-projects created using JAVA v17 and SpringBoot. There are others criteria that will be explained during the interview.

There are two microservices
* CLIENTS: Client manage 
* CORE: Manage Accounts and Transactions

## Steps to deploy
*  Step 1: Configure Database

    In a PostgreSQL database engine, create a empty new database, there you need to open **DataBaseScript.sql** and replace the field **postgres** with your default database user.
    Then, run **DataBaseScript.sql** to create all tables, relations and the first insert.

*  Step 2: Configure Database in Applications

    In each microservice, change the dataBaseName, host, user, password, port of your database. Put it in each **application.yaml**, two files to modify.

* Step 3: Run the application

    Previously, you need to install DOCKER.
    Open a command line in the root, and execute the command `docker-compose up --build`. It will run tests, compile both microservices, create containers and the internal network.

* Step 4: Test endpoints
    
    Import the file **Technical Probe.postman_collection.json** in your local POSTMAN to import all endpoint definition, then use to test endpoints.
