# MoneyTransfer

Money Transfer API starts on http://localhost:7000 by default

Only 2 light weight frameworks have been used as part of this project
 - **Javalin** - Web Framework
 - **JUnit** - Unit Test Frameworrk

Application can be started with a maven goal

```sh
mvn exec:java
```
## Account API - `/account`

**Creating a new account**

Sample Request:

**POST /account**

**Body**
```javascript
{
   "currency":"IND",
   "balance":10,
   "bank":"test",
   "userName":"sumeet"
}
```

Sample response:

**Status: 200 OK**
```javascript
{
   "id":1,
   "currency":"IND",
   "balance":10,
   "bank":"test",
   "userName":"sumeet"
}
```

---
**Read an account**

Request will be a GET request - **/account/{id}**

**GET /account/1**

Response:

**Status: 200 OK**

```javascript
{
  "id":1,
  "currency":"IND",
  "balance":10,
  "bank":"test",
  "userName":"sumeet"
}
```

## Transfer API - `/transfer`

**transfer amount**

Sample post with body:

```javascript
{
	"srcAcc":1,
	"destAcc":2,
	"amount":10
}
```

Sample response:

**Status: 200 OK**
```javascript
Success
```
