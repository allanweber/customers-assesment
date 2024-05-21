FROM openjdk:17-alpine

EXPOSE 8080

ARG Xmx="-Xmx1500m"
ARG Xms="-Xmx1500m"

ENV JAVA_OPTS "$Xmx \
              $Xms \
              -XX:+UseParallelGC"

ADD target/customers*.jar customers.jar

CMD [ "sh", "-c", "java $JAVA_OPTS -jar customers.jar" ]