# Registration

This page explains how to create a new User.

- *Url:* http://127.0.0.1:9080/api/user/registration
- *Method:* POST
- *Content-Type:* application/json
- *Body:*
```
{
	"email": "bar@gmail.com",	// mandatory
	"password": "foo",		// mandatory
	"confirmPassword": "foo"	// mandatory
	"firstName": "Alex",		// optional
	"lastName": "",			// optional
	"middleName": "", 		// optional
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

### Success registration

*Curl:*
```
curl -v --url http://127.0.0.1:9080/api/user/registration --header 'content-type: application/json' --data '{"email": "foo@gmail.com", "firstName": "Alex", "password": "foo", "confirmPassword": "foo"}'
```

*Response:*
```
*   Trying 127.0.0.1...
< HTTP/1.1 200
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Length: 0
< Date: Sun, 18 Aug 2019 11:13:24 GMT
<
* Connection #0 to host 127.0.0.1 left intact
```

### Failed registration

*Curl:*
```
curl -v --url http://127.0.0.1:9080/api/user/registration --header 'content-type: application/json' --data '{"email": "foo@gmail.com", "firstName": "Alex", "password": "foo", "confirmPassword": "foo"}'
```

*Response:*
```
< HTTP/1.1 400
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Sun, 18 Aug 2019 11:13:56 GMT
< Connection: close
<
* Closing connection 0
{"code":100,"description":"User with the specified email already exists"}
```

