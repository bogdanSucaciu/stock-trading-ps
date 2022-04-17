## How to start the project?
Run the following gradle task to start the Stock Trading application project:
```
gradle bootRun
```

>All projects are able to start except the Stock Trading application from the /m3/before folder.
> The reason this is happening is that the project does not contain the MongoDB dependencies for non-reactive setup. 

Starting with `m4/after` you will need to publish the Stock Trading API dependency in the local maven repository. This can be easily achieved using gradle:
```
cd m4/after/stock-market-api
gradle publishToMavenLocal
```
After that you will be able to start both the Stock Market application, as well the Stock Trading application.
## How can I execute requests?

The Stock Trading application server is running on port `8010`, so you can easily execute requests using `curl:
### Create Stock
```
curl --request POST \
  --url http://localhost:8010/stocks \
  --header 'Content-Type: application/json' \
  --data '{ 
	"stockName": "Globomantics",
	"price": 100.00,
	"currency": "USD"
}'

curl --request POST \
  --url http://localhost:8010/stocks \
  --header 'Content-Type: application/json' \
  --data '{ "stockName": "Globomantics", "price": 100.00, "currency": "USD" }'
```
### Get One Stock
```
curl --request GET \
  --url http://localhost:8010/stocks/62229629d349927f8a9cda94
```
### Get All Stocks
```
curl --request GET \
  --url http://localhost:8010/stocks
```