package com.breez.weather_app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherData {

	private List<DayForecast> days;
	private String message;

}
