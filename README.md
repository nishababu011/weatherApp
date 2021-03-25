# weatherApp
Application to fetch weather stats

The application is a spring boot REST API that exposes a endpoint with parameters city, country and APIKey, for retrieving the weather data. The API first queries the database and if not present, it makes call to the openweather external endpoint to fetch the data. The received data is then persisted in the database. The database table stores the minimum fields that are required as part of this.


URL	http://localhost:8090/weatherApp/weather
Query Params	City*, country*
Header Params	apiKey* (Allowed values for apiKey are "WAPP-001", "WAPP-002", "WAPP-003", "WAPP-004", "WAPP-005"
)

![image](https://user-images.githubusercontent.com/46775220/112448846-eae69680-8da6-11eb-9e69-ff61ff0d4e2d.png)
