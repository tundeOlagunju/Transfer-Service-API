# Transfer-Service-API

This is a simple **REST** based transfer service API using **Spring Boot**. This service allows you to create an account with an initial amount 
or balance and also transfer money from one account to another. All transactions history are saved in **MySQL** database.

## Built With 
 - **Spring Boot** - Spring based production ready project initializer for Stand alone Applications
 - **MySQL** - for database

## Steps to Setup

**1. Clone the repository** 

```bash
git clone https://github.com/tundeOlagunju/Transfer-Service-API.git
```

**2. Configure MySQL database**

Create a MySQL database named `mysql`, and change the username and password in `src/main/resources/application.properties` as per user's MySQL
installation -

```properties
spring.datasource.username= <YOUR MYSQL USERNAME>
spring.datasource.password= <YOUR MYSQL PASSWORD>
```

**3. Run the app using maven**

```bash
cd spring-boot-file-upload-download-rest-api-example
mvn spring-boot:run
```

The application can be accessed at `http://localhost:8080`.

## EndPoints

| EndPoint | Method | Description | Payload Example |
| --- | --- | --- | --- |
| /api/balance | POST| create a new account with initial balance | { "balance": 1000 } | 
| /api/balance | GET | get all the account numbers and their balances| |
| /api/balance/{accNo} | GET | get the balance of an account number | |
| /api/balance/{accNo} | POST | deposit money to an existing account | { "balance": 1000 ) |
| /api/transfer | POST | transfer money from one account to another |{"from": 10000001, "to": 10000002, "amount": 1000} |
| /api/transaction-history | GET | get all transactions history | |
| /api/transaction-history/{refNo}    | GET | get the transaction of a reference number | |

## Some Sample Calls

* **Create an account**

```javascript
POST     /api/balance/
Accept: application/json
Content-Type: application/json

{ 
"balance": "500000"
}

RESPONSE: HTTP 201 (Created)

```
* **Transfer money between two accounts**

```javascript
POST     /api/transfer/
Accept: application/json
Content-Type: application/json

{           
   "from":1000000002,
   "to":1000000001,
   "amount":10000   
 }

RESPONSE: HTTP 201 (Created)

```
* **Get All Accounts**

```javascript
GET    /api/balance/

[
    {
        "accountNumber": 1000000001,
        "balance": 2000941.45
    },
    {
        "accountNumber": 1000000002,
        "balance": 20159
    },
    {
        "accountNumber": 1000000003,
        "balance": 988899.55
    },
    {
        "accountNumber": 1000000004,
        "balance": 1010000
    },
    {
        "accountNumber": 1000000005,
        "balance": 1000000
    }
 ]   

RESPONSE: HTTP 200 (OK)

```
## Possible Transaction Errors
* Insufficient balance 
* Transferring money between same account
* Initial deposit lesser than 10.00
* Non-existent account
* Non-existent reference number
* Possible Duplicate Transaction
