package com.breez.weather_app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayForecast {

	private String datetime;
	private double tempmax;
	private double tempmin;
	private double temp;
	private double precip;
	private String conditions;

}
