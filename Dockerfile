FROM openjdk:21

COPY ./build/libs/JHP-World-0.0.1-SNAPSHOT.jar /JHP-World-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/JHP-World-0.0.1-SNAPSHOT.jar"]