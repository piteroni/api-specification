FROM gradle:6.6.1-jdk11

ENV TZ=Asia/Tokyo

COPY application /application

WORKDIR /application

RUN gradle shadowJar

ENTRYPOINT [ "./scripts/run-compiled" ]
