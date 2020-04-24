!#/bin/sh
javac -cp hamcrest-all-1.3.jar:hamcrest-core-1.3.jar:json-simple-1.1.jar:junit-4.13.jar:junit.jar:mockito-all-2.0.2-beta.jar:mysql-connector-java-8.0.17.jar *.java
jar -cvmf MANIFEST.MF SteamNews.jar *.class