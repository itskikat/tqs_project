FROM maven:latest as buildstage

COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline

COPY . .
RUN mvn install -DskipTests


FROM openjdk:11

ADD wait-for-it.sh /
RUN chmod u+x ./wait-for-it.sh
COPY --from=buildstage ./target/handymanservicesapp-0.0.1-SNAPSHOT.jar ./
CMD ["./wait-for-it.sh","backend-distribution-platform:8080","--","java","-jar","handymanservicesapp-0.0.1-SNAPSHOT.jar"]
