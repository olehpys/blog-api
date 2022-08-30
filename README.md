# REST API service for simple blog

## Introduction
Service has 2 user roles: **PUBLISHER** and **ADMIN**. **PUBLISHER** can edit/delete only own posts and comments. **ADMIN** can do everything.

Service has auth integration with AWS Cognito User Pool. User Pool should have custom user attribute called **role** with allowed values: **PUBLISHER** or **ADMIN**.

All requests to blog API should contain **Authorization** header with **Bearer JWT** retrieved after user login in AWS Cognito.

## Post API

Create post:
```
POST /posts

{
    "title": "title",
    "content": "content"
}
```

Update post:
```
PUT /posts/{id}

{
    "title": "title",
    "content": "content"
}
```

Get particular post:
```
GET /posts/{id}
```

Get all posts:
```
GET /posts
```

Delete post:
```
DELETE /posts/{id}
```

## Comment API

Create comment:
```
POST /posts/{post-id}/comments

{
    "post_id": "post_id",
    "content": "content"
}
```

Update comment partially:
```
PATCH /posts/{post-id}/comments/{comment-id}

{
    "content": "content"
}
```

Get post comments:
```
GET /posts/{post-id}/comments
```

Delete comment:
```
DELETE /posts/{post-id}/comments/{comment-id}
```