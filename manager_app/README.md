Test (mac):
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
mvn test

To start the Manager server run:

mvn spring-boot:run