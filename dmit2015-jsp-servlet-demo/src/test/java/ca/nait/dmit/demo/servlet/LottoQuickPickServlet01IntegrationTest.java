package ca.nait.dmit.demo.servlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LottoQuickPickServlet01IntegrationTest {

	private static String URL;

//    @BeforeAll
//    public static void init() {
//        URL = "http://localhost:8080/LottoQuickPickServlet01";
//    }
//
//    @Test
//    public void testHtmlResponset() throws Exception {
//    	HttpClient client = HttpClient.newHttpClient();
//
//    	HttpRequest requestGet = HttpRequest.newBuilder()
//    		.GET()
//    		.header("Content-Type", "text/html")
//    		.uri(URI.create(URL))
//    		.build();
//
//   		HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
//
//   		int statusCode = response.statusCode();
//
//   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP GET failed");
//
//   		String responseBody = response.body();
//
//   		assertTrue(responseBody.contains("The LOTTO MAX quick pick numbers are"),"Unexpected response body");
//
//    }
//
//    @Test
//    public void testTextResponset() throws Exception {
//    	HttpClient client = HttpClient.newHttpClient();
//
//    	HttpRequest requestGet = HttpRequest.newBuilder()
//    		.GET()
//    		.uri(URI.create(URL))
//    		.header("Content-Type", "text/plain")
//    		.build();
//
//   		HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
//
//   		int statusCode = response.statusCode();
//
//   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP GET failed");
//
//   		String responseBody = response.body();
//
//   		String[] quickPickNumbersArray = responseBody.split(",");
//
//   		assertEquals(7, quickPickNumbersArray.length);
//   		System.out.print("Text data: ");
//   		Arrays.asList( quickPickNumbersArray ).stream()
//   			.forEach(num -> System.out.print(num + " "));
//
//    }
//
//    @Test
//    public void testJsonDataResponse() throws Exception {
//    	HttpClient client = HttpClient.newHttpClient();
//
//    	HttpRequest requestGet = HttpRequest.newBuilder()
//    		.GET()
//    		.uri(URI.create(URL))
//    		.header("Content-Type", "application/json")
//    		.build();
//
//   		HttpResponse<String> response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
//
//   		int statusCode = response.statusCode();
//
//   		assertEquals(HttpServletResponse.SC_OK, statusCode, "HTTP GET failed");
//
//   		String responseBody = response.body();
//
//   		JsonReader reader = Json.createReader(new StringReader(responseBody));
//   		JsonArray jsonArray = reader.readArray();
//
//   		assertEquals(7, jsonArray.size());
//   		System.out.print("JSON data: ");
//   		jsonArray.stream()
//			.forEach(num -> System.out.print(num + " "));
//
//    }

}
