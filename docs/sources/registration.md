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
	"firstName": "", 		// optional
}
```
- *Response code:* 200 - Ok, 400 - Failed

## Examples

### Success registration

*Curl:*
```
curl -v --url http://127.0.0.1:9080/api/user/registration --header 'content-type: application/json' --data '{"email": "foo@gmail.com", "firstName": "Alex", "password": "foo", "confirmPassword": "foo"}'
```

*Response:*
```
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9080 (#0)
> POST /api/user/registration HTTP/1.1
> Host: 127.0.0.1:9080
> User-Agent: curl/7.54.0
> Accept: */*
> content-type: application/json
> Content-Length: 92
>
* upload completely sent off: 92 out of 92 bytes
< HTTP/1.1 200
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Length: 0
< Date: Sat, 17 Aug 2019 14:32:20 GMT
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
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9080 (#0)
> POST /api/user/registration HTTP/1.1
> Host: 127.0.0.1:9080
> User-Agent: curl/7.54.0
> Accept: */*
> content-type: application/json
> Content-Length: 92
>
* upload completely sent off: 92 out of 92 bytes
< HTTP/1.1 400
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 44
< Date: Sat, 17 Aug 2019 14:33:51 GMT
< Connection: close
<
* Closing connection 0
User with the specified email already exists
```

