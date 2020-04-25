# Spring Boot REST API MySQL JPA CRUD example

Tools and Technologies: Java8, Spring Boot, MySQL, Maven, IntelliJ IDEA, REST API, Postman, JUnit, Mockito.

## Database configuration

Project uses an in-memory database MySQL which gets populated at startup with data.

## Security configuration

### Basic Authentication

REST API is secured with HTTP Basic Authentication. The existing roles are listed below with the corresponding percompanies:

- Content Manager — administrator able to manage comanies and products

login: Manager
password: Manager

- Customer — able to search and order products

login: Customer
password: Customer

## Layered Architecture

| Layer | Source |
|--|--|
| JavaBean domain| [domain folder](src/main/java/com/almielka/springbootrestapimysqljpacrudexample/domain) |
|Repositories| [repository folder](src/main/java/com/almielka/springbootrestapimysqljpacrudexample/repository) |
|Services| [service folder](src/main/java/com/almielka/springbootrestapimysqljpacrudexample/service) |
| REST API Controllers | [controller folder](src/main/java/com/almielka/springbootrestapimysqljpacrudexample/controller) |
|Security Configuration| [security folder](src/main/java/com/almielka/springbootrestapimysqljpacrudexample/security) |
| Tests | [tetst controllers](src/test/java/com/almielka/springbootrestapimysqljpacrudexample/controller) |

## Functionality to support managing

### Company Management

____
###	Add Company:
____
URL: http://localhost:8080/api/companies

HTTP-method: POST

Data:
- Unique company name
- Company type — a type the company. Possible types are: TYPE1, TYPE2, TYPE3

RequestBody:
```JSON
{
    "name": "company6-3",
    "companyType": "TYPE3"
}
``` 

### Edit Company by ID:
URL: http://localhost:8080/api/companies/6

HTTP-method: PUT

Data:
- Unique company name
- Company type — a type the company. Possible types are: TYPE1, TYPE2, TYPE3

RequestBody:
```JSON
{
    "name": "UpdateCompany6-1",
    "companyType": "TYPE1"
}
```
### Edit Company by Name:
URL: http://localhost:8080/api/companies/name/UpdateCompany6-1

HTTP-method: PUT

Data:
- Unique company name
- Company type — a type the company. Possible types are: TYPE1, TYPE2, TYPE3

RequestBody:
```JSON
{
    "name": "UpdateCompany2-2",
    "companyType": "TYPE2"
}
```

### Get Company by ID:
URL: http://localhost:8080/api/companies/2

HTTP-method: GET

### Get Company/Companies by Name:
URL: http://localhost:8080/api/companies/name/comp

HTTP-method: GET

### Remove Company by ID:
URL: http://localhost:8080/api/companies/6

HTTP-method: DELETE

### Remove Company by Name:
URL: http://localhost:8080/api/companies/name/UpdateCompany6-1

HTTP-method: DELETE

____
### Product Management
____
### Add Product:
URL: http://localhost:8080/api/products

HTTP-method: POST

Data:
- Company name
- Product name
- Product created date—  the date when the product was made (UTC)
- Price – the price of the product

RequestBody:
```JSON
{
	"name": "product7",
	"createdDate": "2019-12-05T06:50:08Z",
	 "price": 20.5,
	 "company": {
            "name": "company4-3"
        }
}	
```

### Remove Product by ID:
URL: http://localhost:8080/api/products/7

HTTP-method: DELETE

____
### Product Search
____
### by Product name:
URL: http://localhost:8080/api/products/search-by-name/product

HTTP-method: GET

### by Company name:
URL: http://localhost:8080/api/products/search-by-company-name/company2-2

HTTP-method: GET

### by Company Type:
URL: http://localhost:8080/api/products/search-by-company-type/TYPE2

HTTP-method: GET

### before date:
URL: http://localhost:8080/api/products/search-before-date?date=2018-12-03T10:15:30.00Z

HTTP-method: GET

### after date:
URL: http://localhost:8080/api/products/search-after-date?date=2018-12-03T10:15:30.00Z

HTTP-method: GET

### between dates:
URL: http://localhost:8080/api/products/search-between-dates?afterDate=2017-02-03T10:15:30.00Z&beforeDate=2018-05-03T10:15:30.00Z

HTTP-method: GET

____
### Product Ordering
____
### add Order:
URL: http://localhost:8080/api/products/order

HTTP-method: POST

RequestBody: 
```JSON
[{"id":2},
{"id": 4}]
```
Another RequestBody: any result of search for example:

URL: http://localhost:8080/api/products/search-by-company-name/company

HTTP-method: GET

### get History:
URL: http://localhost:8080/api/products/order/history

HTTP-method: GET

### get a list of the most ordered products:
URL: http://localhost:8080/api/products/order/history-of-the-most-ordered-products

HTTP-method: GET

### get a list of the most ordered companies:
URL: http://localhost:8080/api/products/order/history-of-the-most-ordered-companies

HTTP-method: GET
