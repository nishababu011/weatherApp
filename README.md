# weatherApp
Application to fetch weather stats

The application is a spring boot REST API that exposes a endpoint with parameters city, country and APIKey, for retrieving the weather data. The API first queries the database and if not present, it makes call to the openweather external endpoint to fetch the data. The received data is then persisted in the database. The database table stores the minimum fields that are required as part of this.

![image](https://user-images.githubusercontent.com/46775220/112449033-1a959e80-8da7-11eb-9f66-a4be05002a3f.png)

