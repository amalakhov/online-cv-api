# Company API

This page explains hot to use Company API.

## Insert company

- *Url:* http://127.0.0.1:9080/api/company
- *Method:* POST
- *Content-Type:* application/json
- *Authorization:* 'token'
- *Body:*
```json
{
	"name": "P24",
	"description": "The biggest bank of Ukraine",
	"photoId": 1
}
```

- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
{
  "id": 1,
  "name": "P24",
  "description": "The biggest bank of Ukraine",
  "photo": {
    "id": 1,
    "uri": "201908/3d528b80-fa46-4b79-9f80-b19d38902387.JPG",
    "url": "http://localhost:80/img/201908/3d528b80-fa46-4b79-9f80-b19d38902387.JPG"
  }
}
```

## Get all companies

- *Url:* http://127.0.0.1:9080/api/company/list
- *Method:* GET
- *Content-Type:* application/json
- *Authorization:* 'token'

- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
[
  {
    "id": 1,
    "name": "P24",
    "description": "The biggest bank of Ukraine",
    "photo": {
      "id": 1,
      "uri": "201908/3d528b80-fa46-4b79-9f80-b19d38902387.JPG",
      "url": "http://localhost:80/img/201908/3d528b80-fa46-4b79-9f80-b19d38902387.JPG"
    }
  },
  {
    "id": 2,
    "name": "1xBet",
    "description": "1xBet",
    "photo": null
  }
]
```

