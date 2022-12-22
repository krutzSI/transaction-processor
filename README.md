# transaction-processor


to run application.
1. clone code gh repo clone krutzSI/transaction-processor
2. cd <path>/transaction-processor
3. mvn clean install
4. cd target
5. java -jar .\transaction-processor-1.0.0.jar
6. to check if application is up, use actuator health
  curl --location --request GET 'http://localhost:8080/transaction-processor/actuator/health'
