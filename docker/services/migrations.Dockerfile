FROM liquibase/liquibase:latest

WORKDIR /liquibase

COPY ./src/main/resources/db/changelog/db.changelog-master.xml \
     /liquibase/db.changelog-master.xml

COPY ./src/main/resources/db/changelog/changesets \
     /liquibase/changesets

COPY ./drivers/postgresql-42.7.10.jar \
     /liquibase/lib/postgresql-42.7.10.jar

ENTRYPOINT ["liquibase"]