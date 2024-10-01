package com.breez.weather_app.service;

import com.breez.weather_app.model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiService {

	private final RestTemplate restTemplate;
	private final String apiUrl;
	private final String apiKey;

	public WeatherApiService(RestTemplate restTemplate,
							 @Value("${weather.api.url}") String apiUrl,
							 @Value("${weather.api.key}") String apiKey) {
		this.restTemplate = restTemplate;
		this.apiUrl = apiUrl;
		this.apiKey = apiKey;
	}

	public WeatherData getWeatherData(String location) {
		String url = String.format("%s/%s?unitGroup=metric&include=days&key=%s&contentType=json",
				apiUrl, location, apiKey);
		try {
			return restTemplate.getForObject(url, WeatherData.class);
		} catch (HttpClientErrorException.NotFound e) {
			WeatherData errorData = new WeatherData();
			errorData.setMessage("City not found");
			return errorData;
		} catch (RestClientException e) {
			WeatherData errorData = new WeatherData();
			errorData.setMessage("Error fetching weather data");
			return errorData;
		}
	}
}