# manager_system

## sample admin request:
url: localhost:8080/admin/addUser
sample header:
{
    "userId": 2134,
    "role": "admin"
}

base64 encoded header: ewogICAgInVzZXJJZCI6IDIxMzQsCiAgICAicm9sZSI6ICJhZG1pbiIKfQ==

request body:
{
    "userId": 1237,
    "endpoint": [
        "Resource A",
        "Resource E"
    ]
}

## sample user request
url: localhost:8080/user/{Resource}
