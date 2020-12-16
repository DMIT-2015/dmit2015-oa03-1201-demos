package dmit2015.service;

import org.junit.jupiter.api.Test;

import javax.json.JsonObject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherMapJaxRsClientTest {

    @Test
    void findByCity() {
        OpenWeatherMapJaxRsClient jaxRsClient = new OpenWeatherMapJaxRsClient();
        Optional<JsonObject> optionalJsonObject = jaxRsClient.findByCity("Edmonton");
        assertTrue(optionalJsonObject.isPresent());
        JsonObject resultJsonObject = optionalJsonObject.get();
        System.out.println(resultJsonObject);
        assertEquals(-5, resultJsonObject.getInt("celsiusTemperature"));
        assertEquals(-9, resultJsonObject.getInt("feelsLike"));
        assertEquals("broken clouds",resultJsonObject.getString("description"));
    }
}