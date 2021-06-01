FROM openjdk:11
COPY ./target/servicemanagement-0.0.1-SNAPSHOT.jar servicemanagement-0.0.1-SNAPSHOT.jar
ADD wait-for-it.sh /
RUN chmod u+x ./wait-for-it.sh

CMD ["./wait-for-it.sh","db:5432","--","java","-jar","servicemanagement-0.0.1-SNAPSHOT.jar"]
