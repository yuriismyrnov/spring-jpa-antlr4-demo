# Spring + JPA + Antlr4 demo

Example shows how to build a simple query language using JPA and Antlr4 for a REST API service. 

Naive e-wallet domain model is used here: 

```
User -< Wallet >- Currency 
          |
          ^
      Transaction
```

Service allows to create and query transactions for pre-populated wallets.

### Usage

Start a database: 

```
./mvnw docker:start
```

Start a micro-service:

```
./mvnw clean spring-boot:run
```

Query transactions, for example those with amount less than or equal to 1200 USD:

```
curl -G "http://localhost:8080/v1/transactions" --data-urlencode "filter=amount le 1500 and to.currency.currencyCode eq 'USD'" | jq
```

Create a new transaction (change `from` and `to` values to ones from your database):

```
 curl -d '{"from": "1571170777883", "to": "1571170777884", "amount": 45}'  -H 'content-type: application/json' -X POST http://localhost:8080/v1/transactions | jq
```
