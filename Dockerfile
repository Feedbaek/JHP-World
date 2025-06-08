FROM openjdk:21

COPY ./build/libs/JHP-World-0.0.1-SNAPSHOT.jar /JHP-World-0.0.1-SNAPSHOT.jar

ENV TZ=Asia/Seoul

RUN apt-get update && apt-get install -y tzdata

ENTRYPOINT ["java", "-jar", "/JHP-World-0.0.1-SNAPSHOT.jar"]