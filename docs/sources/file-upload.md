# Upload files API

This page explains hot to upload files.

## Upload single file

- *Url:* http://127.0.0.1:9080/api/upload/img
- *Method:* POST
- *Content-Type:* multipart/form-data
- *Authorization:* 'token'
- *Form Option:* '--form file='
- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
{
  "id": 2,
  "uri": "201908/30569233-fb75-4ae1-99ca-3ed859459666.JPG",
  "url": "http://localhost:80/img/201908/30569233-fb75-4ae1-99ca-3ed859459666.JPG"
}
```

## Upload list of files

- *Url:* http://127.0.0.1:9080/api/upload/imgs
- *Method:* POST
- *Content-Type:* multipart/form-data
- *Authorization:* 'token'
- *Form Option:* '--form files='
- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
[
	{
	  "id": 2,
	  "uri": "201908/30569233-fb75-4ae1-99ca-3ed859459666.JPG",
	  "url": "http://localhost:80/img/201908/30569233-fb75-4ae1-99ca-3ed859459666.JPG"
	}
]
```
