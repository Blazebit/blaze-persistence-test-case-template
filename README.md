Blaze-Persistence Test Case Template
==========
This template tries to reduce complexity but still give the most possible flexibility to users for creating test cases for Blaze-Persistence.

It's generally recommended to put entities into `src/main/java` into the package `com.blazebit.persistence.bugs.entity` and list the fully qualified class names in `src/main/resources/META-INF/persistence.xml`.

Example tests that show how you can write tests for the *core*, *entity-view* and *jpa-criteria* modules are contained in `com.blazebit.persistence.bugs.IssueTest`.
If you add new entities, add the `class` references to `getEntityClasses()`, otherwise they are not considered.

If you have an issue, you can create a pull request against this repository if you want and post the link of the PR to the issue in Blaze-Persistence.

## Building

This project uses Maven and by default, builds with *Hibernate 4.2* and *H2*. If you want to build against a different JPA provider or DBMS use the following command.

`mvn -P "$JPA_PROVIDER,$DBMS" clean install`

You can skip the tests with `-DskipTests` if you want.

Possible values for *JPA_PROVIDER*

 - hibernate
 - hibernate-4.3
 - hibernate-5.0
 - hibernate-5.1
 - hibernate-5.2
 - eclipselink
 - datanucleus-4
 - datanucleus-5
 - openjpa

Possible values for *DBMS*

 - h2
 - mysql
 - postgresql
 - sqlite
 - db2
 - firebird
 - oracle
 - mssql

Since SQLite and Firebird are currently not officially supported by Blaze-Persistence, these DBMS are not automatically tested.

## Local DB

Some databases require a little setup before you can test against them. In general you can configure the JDBC Url, user and password via command line properties.

 - `-Djdbc.url` for overriding the JDBC Url
 - `-Djdbc.user` for overriding the user
 - `-Djdbc.password` for overriding the password
 
All databases use some kind of default.

### MySQL

Host: localhost
Port: 3306
Database: test
User: root
Password:

### PostgreSQL

Host: localhost
Port: 5432
Database: test
User: postgres
Password: postgres

### DB2

Host: localhost
Port: 50000
Database: test
User: db2inst1
Password: db2inst1-pwd

#### JDBC Driver

You have to install the JDBC driver manually. If you install DB2 Express locally, you can take it from $DB2_HOME/sqllib/java otherwise download it from http://www-01.ibm.com/support/docview.wss?uid=swg21363866

When using the docker container, extract the jdbc driver from the container via:

`docker cp db2:/home/db2inst1/sqllib/java/db2jcc4.jar db2jcc4.jar`

`docker cp db2:/home/db2inst1/sqllib/java/db2jcc_license_cu.jar db2jcc_license_cu.jar`

And install with the following command:

`mvn -q install:install-file -Dfile=db2jcc4.jar -DgroupId=com.ibm.db2 -DartifactId=db2jcc4 -Dversion=9.7 -Dpackaging=jar -DgeneratePom=true`

`mvn -q install:install-file -Dfile=db2jcc_license_cu.jar -DgroupId=com.ibm.db2 -DartifactId=db2jcc_license_cu -Dversion=9.7 -Dpackaging=jar -DgeneratePom=true`

### Oracle

Host: localhost
Port: 1521
Database: xe
User: SYSTEM
Password: oracle

#### JDBC Driver

You have to install the JDBC driver manually. If you install Oracle XE locally, you can take it from $ORACLE_HOME/jdbc otherwise download it from http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html
Copy the jar to $M2_HOME/com/oracle/ojdbc14/10.2.0.4.0/ojdbc14-10.2.0.4.0.jar and you should be good to go.

If you use the docker container, extract the jdbc driver from the container via `docker cp oracle:/u01/app/oracle/product/11.2.0/xe/jdbc/lib/ojdbc6.jar ojdbc.jar`

`mvn -q install:install-file -Dfile=ojdbc.jar -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.4.0 -Dpackaging=jar -DgeneratePom=true`

### SQL Server

Host: localhost
Port: 1433
Database: 
User: sa
Password: Blaze-Persistence

## Docker DB

As convenience, we provide a script `docker_db.sh` that can start docker containers for various DBMS. Depending on your local setup(i.e. you are running docker natively or on a VM) you might have to adapt the `jdbc.url` property if you want to run the test against a DB inside of a docker container.

- `./docker_db.sh mysql_5_6`
- `./docker_db.sh mysql_5_7`
- `./docker_db.sh db2`
- `./docker_db.sh oracle`
- `./docker_db.sh mssql`