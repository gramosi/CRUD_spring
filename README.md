<h1>Simple CRUD REST API with spring.</h1>

ABOUT
This is a simple application that has two entities: users, roles. There are only two roles in this application: USER and ADMIN. All users and admins can make CRUD requests on /users api, whereas only admin can make CRUD operations on /roles api. Every request on this application should be authenticated. Type of authentication used is basic authentication: username, password.
 
RUNNING THE APPLICATION:
Before running the application first execute the file, tables.sql, which will create the database and tables(MYSQL)(Located under resources folder)

Then run the application, either by executing 
java -jar  that is located under /target/ folder
or
by opening in eclipse or intelliJ 

**Use default user : 
username:admin@email.com   password:123qwe
or
username:user@email.com    password:123qwe

Base URL:
localhost:8081

AVAILABLE REQUESTS:

**USERS:**


GET    /users - Retrieve all users

GET    /users/{id} - Retrieve one user

POST   /users - Add user
     {
      "firstName":"first",
       "lastname":"last",
       "email":"email@email.com",
       "password":"123qwe"
     }
     
     *Every new user added has by default Role: USER
     
PUT    /users/{id} - Update user
    {
       "firstName":"first",
       "lastname":"last",
       "email":"email@email.com",
       "password":"123qwe",
       "roles":[
            {
                "id": 1
            }
       ]
    }
     
DELETE users/{id} - Delete user

 **ROLES:**

 
GET    /roles - Get all roles
GET    /roles/{id} - Get one role
POST    /users - Add role
     {
        "name":"ROLE",
     }
DELETE roles/{id} - Delete role
