package test.sarwo.service.impl;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.maps.GeoApiContext;
import com.google.maps.TimeZoneApi;
import com.google.maps.model.LatLng;

import test.sarwo.service.LocalDateResolver;

public class LocalDateResolverImpl implements LocalDateResolver {

	private static final String API_KEY = "AIzaSyAbmtUt6BZuvKRDK8dB7pPIELqTeclXCrQ";

	@Override
	public TimeZone resolveTimeZone(double latitude, double longitude) throws Exception {
		
		GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
		
		if (context == null) {
			throw new Exception(String.format(
					"Given API KEY %s is not valid, please check your api key configuration in your google api console",
					API_KEY));
		}
		
		LatLng latLong = new LatLng(latitude, longitude);
		
		TimeZone tz = TimeZoneApi.getTimeZone(context, latLong).awaitIgnoreError();

		if (tz == null) {
			throw new Exception(
					String.format("Timezone with latitude :%s and long : %s not found!", latitude, longitude));
		}

		return tz;
	}


	@Override
	public DateTime resolveDateTime(String timeZoneId) throws Exception {
		DateTimeZone dtz = DateTimeZone.forID(timeZoneId);
		DateTime dt = new DateTime(dtz);
		return dt;
	}

}
