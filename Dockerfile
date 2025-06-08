FROM openjdk:21

COPY ./build/libs/JHP-World-0.0.1-SNAPSHOT.jar /JHP-World-0.0.1-SNAPSHOT.jar

ENV TZ=Asia/Seoul

RUN apk update && apk upgrade && apk add -y tzdata

ENTRYPOINT ["java", "-jar", "/JHP-World-0.0.1-SNAPSHOT.jar"]