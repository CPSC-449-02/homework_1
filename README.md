# Homework 1

## Endpoints

Get all books
```
GET /api/books
```
Example:
![img_1.png](images/img1.png)

Get book by id
```
GET /api/books/{id}
```
Example:
![img.png](images/img2.png)

Create book
```
POST /api/books
```
Request body
```json
{
  "id": "100",
  "title": "Learn GO",
  "author": "Max",
  "price": 30.0
}
```
Example:
![img.png](images/img3.png)


Update entire book
```
PUT /api/books/{id}
```
Request body
```json
{
  "title": "Java is fun",
  "author": "Max",
  "price": 10.0
}
```
Example:
![img.png](images/img4.png)

Update part of a book
```
PATCH /api/books/{id}
```
Request body
```json
{
  "title": "C++ for beginners"
}
```
Example:
![img.png](images/img5.png)

Delete book
```
DELETE /api/books/{id}
```
Example:
![img.png](images/img6.png)


GET books with pagination
```
GET /api/books/page
```
Request params
```
from:   2
to:     4
```
Example:
![img.png](images/img7.png)
Advanced GET endpoint with filtering, sorting, and pagination combined in the valid order

Search by title
```
GET /api/books/search
```

Request params
```
title:  o 
from:   0
to:     5
sortBy: title
```
Example:
![img_1.png](images/img8.png)
