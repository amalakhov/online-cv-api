# Login

This page explains how to get JWT token.

- *Url:* http://127.0.0.1:9080/api/login
- *Method:* POST
- *Content-Type:* application/json
- *Body:*
```
{
	"email": "foo@gmail.com",	// mandatory
	"password": "foo",		// mandatory
}
```
- *Response code:* 200 - Ok, 4xx/5xx - Failed

### Error codes
| Error code  | Description |
| ------------- | ------------- |
| 100 | User with the specified email already exists |
| 101 | Passwords don't match |
| 102 | User with the specified email and password doesn't exist |
| 103 | Request body is null |
| 104 | Registration failed |
| 105 | Token has been expired |
| 106 | Access denied |
| 107 | Full authentication is required |

## Examples

### Success login

*Curl:*
```
curl -v --url http://127.0.0.1:9080/api/login --header 'content-type: application/json' --data '{"email": "foo@gmail.com", "password": "foo"}'
```

*Response:*
```json
{
   "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb29AZ21haWwuY29tIiwiZXhwIjoxNTY2MTMxMTk0fQ.jlhGIbmCCBuOEBWDZw4BKLSXbSI4jeE9-qD5J8OePew",
   "tokenPrefix":"Bearer ",
   "tokenHeader":"Authorization",
   "expirationTs":1566131194,
   "user":{
      "id":7,
      "lastName":null,
      "firstName":"Alex",
      "middleName":null,
      "email":"foo@gmail.com",
      "role":{
         "id":2,
         "name":"User",
         "applicationRole":[
            "USER"
         ]
      },
      "photo":null,
      "status":"ACTIVE"
   }
}
```

### Failed login

*Curl:*
```
curl -v --url http://127.0.0.1:9080/api/login --header 'content-type: application/json' --data '{"email": "foo@gmail.com", "password": "bar"}'
```

*Response:*
```json
{
   "code":102,
   "description":"User with the specified email and password doesn't exist"
}
```

### Test jwt token

*Curl:*
```
curl -v --request GET --url http://127.0.0.1:9080/api/user/hey --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb29AZ21haWwuY29tIiwiZXhwIjoxNTY2MTMxMTk0fQ.jlhGIbmCCBuOEBWDZw4BKLSXbSI4jeE9-qD5J8OePew' --header 'content-type: application/json'
```

*Response:*
```
< HTTP/1.1 200
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 33
< Date: Sun, 18 Aug 2019 11:30:40 GMT
<
* Connection #0 to host 127.0.0.1 left intact
Hey foo@gmail.com! Your id is '7'
```

### Test wrong jwt token

*Curl:*
```
curl -v --request GET --url http://127.0.0.1:9080/api/user/hey --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb29AZ21haWwuY29tIiwiZXhwIjoxNTY2MTMxMTk0fQ.jlhGIbmCCBuOEBWDZw4BKLSXbSI4jeE9-qD5J8OePew_bar' --header 'content-type: application/json'
```

*Response:*
```
< HTTP/1.1 403
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Transfer-Encoding: chunked
< Date: Sun, 18 Aug 2019 11:31:49 GMT
<
* Connection #0 to host 127.0.0.1 left intact
{"code":106,"description":"Access denied"}
```
