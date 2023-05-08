To clarify:

1. What should happen in case of 0 followers (division by 0) - should be returned some string info: NaN, undefined, infinitive, 0 or sth else?
2. What kind of precisions calcuations should have (integer, double, float)?
3. What kind of type/format should have createAt date?
4. What kind of type should have id (String or long)?
5. Should be login request count calculated for not-existing GitHub users?

About solution:

* to run application - gradle clean bootRun
* to run tests - gradle clean test
* access to in memory H2 DB by console - http://localhost:8080/h2-console/ - to connect change JDBC URL to value from spring.datasource.url property
* DB is created automatically, in real project migration tool should be use e.g.: Flyway, Liquibase
* health check endpoint - http://localhost:8080/actuator/health
* application use naive (corner cases not covered e.g.: more than one handler, missing handler, etc.) in-memory implementation of domain events to show proposed design. In multi-instances project executor service could be replaced with persisted rabbitMQ.
* security aspects were skipped
* logging is done by default configuration (distributed tracing not provided)
* circuit breaker not provided (such as not supported anymore hystrix)
* login request count for not-existing GitHub users is calculated
* in case of 0 followers (division by 0) - returned string NaN
* calcuations uses integers only
* createAt done as string
* optimistic locking used by version column
* used GitHub api version - X-GitHub-Api-Version:2022-11-28
* application endpoint is accessible by swagger - http://localhost:8080/swagger-ui/index.html
* api versioning by accept header:
  * application/json - always newest api
  * application/vnd.empik.v1+json - api version 1
