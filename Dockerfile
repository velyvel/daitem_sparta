# 프로젝트랑 자바버전이랑 맞춰주어야 함 여기서 좀 오래걸렸음
FROM openjdk:17-jdk
LABEL authors="sulim"
ARG JAR_FILE=build/libs/daitem-0.0.1-SNAPSHOT.jar
#도커에 표시할 이름 지정
ADD ${JAR_FILE} daitem-docker.jar

#컨테이너가 실행될 때 반드시 실행될 명령어
ENTRYPOINT ["java", "-jar", "/daitem-docker.jar"]
