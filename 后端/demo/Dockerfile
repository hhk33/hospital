FROM openjdk8

EXPOSE 8081

ADD demo.jar app.jar

#执行命令
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=pro"]
