package dmit2015.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.Optional;

public class OpenWeatherMapJaxRsClient {

    public Optional<JsonObject> findByCity(String cityName) {
        Optional<JsonObject> optionalJsonObject = Optional.empty();

        Response response = ClientBuilder
                .newClient()
                .target("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", cityName)
                .queryParam("appid","83fa06166ad4e994191f3b6a7c0431ab")
                .request()
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String responseBody = response.readEntity(String.class);
            JsonReader reader = Json.createReader(new StringReader(responseBody));
            JsonObject responseJsonObject = reader.readObject();

            JsonObject mainJsonOject = (JsonObject) Json.createPointer("/main").getValue(responseJsonObject);
            double kelvinTemperature = mainJsonOject.getJsonNumber("temp").doubleValue();
            double feelsLikeKevlinTempeature = mainJsonOject.getJsonNumber("feels_like").doubleValue();
            double celsiusTemperature = kelvinToCelsius(kelvinTemperature);
            double feelsLikeCelius = kelvinToCelsius(feelsLikeKevlinTempeature);
            String description = Json
                    .createPointer("/weather/0")
                    .getValue(responseJsonObject)
                    .asJsonObject()
                    .getString("description");

            JsonObject resultJsonObject = Json.createObjectBuilder()
                    .add("celsiusTemperature", celsiusTemperature)
                    .add("feelsLike", feelsLikeCelius)
                    .add("description", description)
                    .build();
            optionalJsonObject = Optional.of(resultJsonObject);

        }

        return optionalJsonObject;
    }

    public long kelvinToCelsius(double kelvinTemperature) {
        return Math.round(kelvinTemperature - 273.14);
    }
}
