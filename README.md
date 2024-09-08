# TODO LIST - 7 hours challenge

To build and run locally using docker:

```bash
mvn compile jib:dockerBuild
docker run -p 127.0.0.1:8080:8080 todo-list
```

To test the API check out the file `test-request.http` in the root of the project.


References:
- https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html
- https://www.danvega.dev/blog/spring-security-jwt
