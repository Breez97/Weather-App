package com.breez.weather_app.service;

import com.breez.weather_app.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class WeatherApiService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private RedisTemplate<String, WeatherData> redisTemplate;

	@Value("${weather.api.url}")
	private String apiUrl;
	@Value("${weather.api.key}")
	private String apiKey;
	@Value("${weather.cache.duration}")
	private Duration cacheDuration;

	public WeatherData getWeatherData(String location) {
		WeatherData cachedData = redisTemplate.opsForValue().get(location);
		if (cachedData != null) {
			return cachedData;
		}
		String url = String.format("%s/%s?unitGroup=metric&include=days&key=%s&contentType=json",
				apiUrl, location, apiKey);
		try {
			WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);
			if (weatherData != null) {
				redisTemplate.opsForValue().set(location, weatherData, cacheDuration);
			}
			return weatherData;
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