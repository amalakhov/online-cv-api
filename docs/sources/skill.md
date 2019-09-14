# Skill API

This page explains hot to use Skill API.

# Company API

This page explains hot to use Company API.

## Get skill categories

- *Url:* http://127.0.0.1:9080/api/skill/categories
- *Method:* GET
- *Content-Type:* application/json
- *Authorization:* 'token'

Response body

```json
[
  {
    "id": "BACKEND",
    "title": "Backend"
  },
  {
    "id": "FRONTEND",
    "title": "Frontend"
  },
  {
    "id": "DEVOPS",
    "title": "DevOps"
  },
  {
    "id": "IDE",
    "title": "Integrated development environment (IDE)"
  },
  {
    "id": "VC",
    "title": "Version control"
  }
]
```

## Insert skill

- *Url:* http://127.0.0.1:9080/api/skill
- *Method:* POST
- *Content-Type:* application/json
- *Authorization:* 'token'
- *Body:*
```json
{
	"name": "Java",
	"category": "BACKEND"
}
```

- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
{
  "id": 1,
  "name": "Java",
  "category": {
    "id": "BACKEND",
    "title": "Backend"
  }
}
```

## Get all skills

- *Url:* http://127.0.0.1:9080/api/skill/list
- *Method:* GET
- *Content-Type:* application/json
- *Authorization:* 'token'

- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
[
  {
    "id": 1,
    "name": "Java",
    "category": {
      "id": "BACKEND",
      "title": "Backend"
    }
  },
  {
    "id": 2,
    "name": "TypeScript",
    "category": {
      "id": "FRONTEND",
      "title": "Frontend"
    }
  },
  {
    "id": 3,
    "name": "Go",
    "category": {
      "id": "BACKEND",
      "title": "Backend"
    }
  },
  {
    "id": 4,
    "name": "JavaScript",
    "category": {
      "id": "FRONTEND",
      "title": "Frontend"
    }
  }
]
```

## Get skills by category (you can specify category ID in URL)

- *Url:* http://127.0.0.1:9080/api/skill/category/BACKEND
- *Method:* GET
- *Content-Type:* application/json
- *Authorization:* 'token'

- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
[
  {
    "id": 1,
    "name": "Java",
    "category": {
      "id": "BACKEND",
      "title": "Backend"
    }
  },
  {
    "id": 3,
    "name": "Go",
    "category": {
      "id": "BACKEND",
      "title": "Backend"
    }
  }
]
```

## Get all skills per category

- *Url:* http://127.0.0.1:9080/api/skill/per/category
- *Method:* GET
- *Content-Type:* application/json
- *Authorization:* 'token'

- *Response code:* 200 - Ok, 4xx/5xx - Failed

Response body

```json
{
  "FRONTEND": [
    {
      "id": 2,
      "name": "TypeScript",
      "category": {
        "id": "FRONTEND",
        "title": "Frontend"
      }
    },
    {
      "id": 4,
      "name": "JavaScript",
      "category": {
        "id": "FRONTEND",
        "title": "Frontend"
      }
    }
  ],
  "BACKEND": [
    {
      "id": 1,
      "name": "Java",
      "category": {
        "id": "BACKEND",
        "title": "Backend"
      }
    },
    {
      "id": 3,
      "name": "Go",
      "category": {
        "id": "BACKEND",
        "title": "Backend"
      }
    }
  ]
}
```

