#!/bin/bash
mvn clean package -DskipTests
java -jar -Dspring.profiles.active=dev ./target/e-commerce-0.0.1-SNAPSHOT.jar