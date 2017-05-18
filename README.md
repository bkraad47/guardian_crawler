# Guardian AU webcrawler - Badruddin Kamal (Raad)

## EC2 Link - http://ec2-34-210-127-161.us-west-2.compute.amazonaws.com:8080/guardiancrawler

This simple Java Application Crawls through - The Guardian Australia website and scans for Headlines, Articles, Authors and Dates. Which it stores in MongoDB. 
The Application also implements a restful search service, allowing it to search for keywords in specific stored fields. Owing, to the scheduler, the application automatically
updates its knowledge and crawls The Guardian website everynight at 2 AM (US west time).

*** The DB has been changed to mLab from compose for testing/hosting purposes ***

*** The code is multi-threaded and will only work if number of threads running at the processor < Max MongoDB connections***

## Dependencies

1. MongoDB (mlab)
2. JSoup
3. Gson
4. Quartz
5. Glassfish
6. Java 7

## Running / Deployment

Simply compile the .war and deploy to your Glassfish server and ensure mLab DB connection is accessible. A deployment test is run to ensure there is a suitable environment.

## Restful Search

A get api, which consumes type and query as query parameters, does the search on MongoDB and returns results accordingly as a JSON array.

Endpoint - http://ec2-34-210-127-161.us-west-2.compute.amazonaws.com:8080/guardiancrawler/search

type can be author, headline, date or text.

query should be the string you want to search for.

Test Example - http://ec2-34-210-127-161.us-west-2.compute.amazonaws.com:8080/guardiancrawler/search?type=headline&query=Trump

## Test 

A simple deployment test is run to ensure resource availability.


