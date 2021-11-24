# 添加 Java 8 镜像来源
FROM openjdk:8-jdk-alpine
COPY market-collector-rest/target/*.jar /app.jar
CMD ["--server.port=4010"]
EXPOSE 4010
ENTRYPOINT ["java","-Djava.security.egd=file:/prod/./urandom", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
#ENTRYPOINT exec java -Xms512m -Xmx512m -Xss1024K -XX:PermSize=512m -XX:MaxPermSize=512m -XX:-OmitStackTraceInFastThrow  -Djava.security.egd=file:/prod/./urandom  -Dspring.profiles.active=prod -jar /app.jar