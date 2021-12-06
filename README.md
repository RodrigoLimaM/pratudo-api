# PraTudo API

BackEnd that serves the PraTudo application.
* **Architecture:**

  ![Alt text](https://user-images.githubusercontent.com/51386403/144732219-f2f276b2-ea52-4a4f-880c-48456f07f7ff.png "Architecture")
    * 1 - ***PraTudo APP*** make an HTTP request with username and password;
    * 2 - ***PraTudo API*** returns a JWT token;
    * 3 - ***PraTudo APP*** make an HTTP requests passing the returned JWT token;
    * 4 - ***PraTudo API*** make an HTTP request requests data from the ***Elasticsearch***;
    * 5 - ***Elasticsearch*** returns data;
    * 6 - ***PraTudo API*** returns data.