# First AREM Project

A Web server (Apache type) was implemented in Java. The server is able to deliver html pages and jpg images, and it is also able to search for requested resources such as java classes and receive parameters. The server attends multiple non-concurrent requests.

## Prerrequisites

If you want to download and run the source code, it is necessary to have installed java 1.8 and Apache Maven 3.6.0  on the computer where the program will run.

## Guetting started

Execute this line to clone the project.
```
git clone https://github.com/jcamilovelandiab/first-arem-projec
```

To download the project dependencies the following line must be executed.
```
mvn package
```

Execute this line to see the java documentation.
```
mvn javadoc:jar
```
To run the project.
```
mvn exec:java -Dexec.mainClass="edu.escuelaing.arem.project.Controller"
```
Now, the project is running locally on port 4567.
To see the resource "hello" go to the browser and type the following url:
```
http://localhost:4567/apps/hello
```

## Deploying

The web application is deployed in heroku. To visit the website go to the following link. [first arem project](https://first-arem-project.herokuapp.com/apps/hello)

## Testing

In this tests the heroku URL was used. If you want to test the project locally you can use localhost:4567/apps/hello, otherwise https://first-arem-project.herokuapp.com/apps/hello

### Requesting a java class.

Testing the /apps/hello URL. The application executes the hello method, and the browser receives a message.

![](https://github.com/jcamilovelandiab/first-arem-project/blob/master/images/hello-testing.PNG)

Testing the /apps/test URL. The application executes the test method, and the browser receives a message.

![](https://github.com/jcamilovelandiab/first-arem-project/blob/master/images/test-testing.PNG)

Testing the /apps/hellofriend URL with parameters. The URL receives the client's name.

![](https://github.com/jcamilovelandiab/first-arem-project/blob/master/images/hellofriend-testing.PNG)

Testing the /apps/sum URL with parameters. The URL receives two numbers, and the server returns the sum of those numbers.

![](https://github.com/jcamilovelandiab/first-arem-project/blob/master/images/sum-testing.PNG)

### Requesting a html or jpg resource.

Searching the file github.html

![](https://github.com/jcamilovelandiab/first-arem-project/blob/master/images/githubhtml-testing.PNG)

Searching the file escuelaing.jpg

![](https://github.com/jcamilovelandiab/first-arem-project/blob/master/images/escuelaingjpg-testing.PNG)

Searching the file github.jpg

![](https://github.com/jcamilovelandiab/first-arem-project/blob/master/images/githubjpg-testing.PNG)

## Build with

* [Maven](https://maven.apache.org/) - Dependency Management

## Author

Juan Camilo Velandia Botello - Escuela Colombiana de Ingeniería Julio Garavito, Colombia.

## License

This project is under the Apache license - see [LICENSE](LICENSE.md) for more details.
