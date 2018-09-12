# RESTful API for simple web blog

The application have one user role - PUBLISHER.

Implemented REST resources:
- Resource to authenticate a particular user.
- Create, update, delete users and blog posts.
- Get the list of all blog posts. Filter option to get only own author's posts.
- Get a particular blog post by id.

## Running application
### Running from an IDE
You can run a Website Monitoring Tool application from your IDE as a simple Java application. However, you first need to import project. Import steps vary depending on your IDE and build system. Most IDEs can import Maven projects directly. 

If you cannot directly import your project into your IDE, you may be able to generate IDE metadata by using a build plugin. Maven includes plugins for Eclipse and IDEA.

### Running as a Packaged Application

If you use the Spring Boot Maven or Gradle plugins to create an executable jar, you can run your application using java -jar, as shown in the following example:
```
$ java -jar target/blogapi-0.0.1-SNAPSHOT.jar
```

## cURL requests examples

Add new user:
```
curl --data "username={username}&password={password}&role=PUBLISHER" http://localhost:8080/users
```

Add blog post:
```
curl -u username:password -d "title={title}&content={content}" http://localhost:8080/posts/add
```

Edit blog post:
```
curl -u username:password -d "title={title}&content={content}" http://localhost:8080/posts/{id}/edit
```

Delete blog post:
```
curl -u username:password http://localhost:8080/posts/{id}/delete
```

Get a particular blog post:
```
curl -u username:password http://localhost:8080/posts/{id}
```

Get the list of all blog posts:
```
curl -u username:password http://localhost:8080/posts/all
```

Get the list of all authorized user's blog posts:
```
curl -u username:password http://localhost:8080/posts/all/own
```
