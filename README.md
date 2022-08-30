# RESTful API for simple web blog

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