# User API

This page explains User API.

## Get user profile

- *Url:* http://127.0.0.1:9080/api/user/profile
- *Method:* GET
- *Content-Type:* application/json
- *Authorization:* 'token'

- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
{
  "id": 7,
  "lastName": null,
  "firstName": "Alex",
  "middleName": null,
  "email": "foo@gmail.com",
  "role": {
    "id": 2,
    "name": "User",
    "applicationRole": [
      "USER"
    ]
  },
  "photo": {
    "id": 2,
    "uri": "201908/30569233-fb75-4ae1-99ca-3ed859459666.JPG",
    "url": "http://localhost:80/img/201908/30569233-fb75-4ae1-99ca-3ed859459666.JPG"
  },
  "status": "ACTIVE"
}
```

## Update user photo

- *Url:* http://127.0.0.1:9080/api/user/photo/update/
- *Method:* PUT
- *Content-Type:* application/json
- *Authorization:* 'token'
- *Body:*

```json
{
	"fileId": 2
}
```

- *Response code:* 200 - Ok, 4xx/5xx - Failed

