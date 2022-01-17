# Pokedex
Pokedex for TrueLayer..

* Prerequisites
  * Java 11 (https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
  * Maven
  * IDE (Ideally Intellij) (https://www.jetbrains.com/idea/download/#section=mac)
  * Docker (https://docs.docker.com/get-docker/)

    
* Install packages
  * Terminal: cd into the root dir. Run 'mvn clean install'.
  * Intellij: Find the maven window (top right). Click into 'lifecycle' and highlight both 'clean' & 'install'. Click the green run arrow.

* Running the application
  * Intellij: Click the green run arrow in the MainApplicationClass. This will run the REST API on port 6001.
  * Docker: In your terminal, cd into the root of the pokedex project. Make sure you have built & compiled the project.
    * The target folders should be present.
    * Terminal: docker build -t pokedex-app:v1 .
    * Terminal: docker images (verify the image has built and is present)
    * Terminal: docker run -p 6001:6001 <image ID>
    * Terminal: docker container ls (displays all containers)
    * Terminal: docker stop <container ID> (kills the container)

* Calling the REST API
  * Intellij: Run the standaloneApiTest.class and verify output in the logs.
  * Browser: http://localhost:6001/pokemon/translated/charmander
    * http://localhost:6001/pokemon/charmander
  
* TODO for production release:
  * Make the rest calls HTTPS. To do this you will need to create a self-signed certificate. https://www.baeldung.com/spring-boot-https-self-signed-certificate