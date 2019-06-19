# MoneyTransfer

Money Transfer API starts on http://localhost:7000 by default
Only 2 frameworks have been used as part of this project - 
 - **Javalin** - Web Framework
 - **JUnit** - Unit Test Frameworrk

Application can be started with a maven goal

```sh
mvn exec:java
```
## Account API - `/account`

**POST** - create a new account 
**Request Body** - Account object

Sample body to post:
```javascript
{
	"currency":"IND",
	"balance":"40",
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
  "balance":0,
  "bank":"test",
  "userName":"sumeet"
}
```

---
**GET**
**/account/{id}** - get account id
Sample request - **/account/1**

Response:
**Status: 200 OK**
```javascript
{
  "id":1,
  "currency":"IND",
  "balance":0,
  "bank":"test",
  "userName":"sumeet"
}
```
For invalid reads proper reponse, status and exceptions are thrown. Please check the test cases for more details. 

## Transfer API - `/transfer`

**POST** - transfer

**Request Body** - Transfer object

Sample request:
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

For invalid transfers like insuffucient balance proper reponse, status and exceptions are thrown. are thrown. Please check the test cases for more details. 
