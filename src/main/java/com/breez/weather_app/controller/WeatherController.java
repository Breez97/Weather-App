package com.breez.weather_app.controller;

import com.breez.weather_app.model.WeatherData;
import com.breez.weather_app.service.WeatherApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

	private final WeatherApiService weatherApiService;

	public WeatherController(WeatherApiService weatherApiService) {
		this.weatherApiService = weatherApiService;
	}

	@GetMapping("/weather")
	public WeatherData getWeather(@RequestParam String location) {
		return weatherApiService.getWeatherData(location);
	}
}
