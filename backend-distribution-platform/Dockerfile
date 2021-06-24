FROM maven:latest as buildstage

COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline

COPY . .
RUN mvn install -DskipTests


FROM openjdk:11

ADD wait-for-it.sh /
RUN chmod u+x ./wait-for-it.sh

COPY --from=buildstage concelhos.csv concelhos.csv
COPY --from=buildstage distritos.csv distritos.csv
COPY --from=buildstage ./target/servicemanagement-0.0.1-SNAPSHOT.jar ./
CMD ["./wait-for-it.sh","db:5432","--","java","-jar","servicemanagement-0.0.1-SNAPSHOT.jar"]
