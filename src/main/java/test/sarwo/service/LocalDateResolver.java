package test.sarwo.service;

import java.util.TimeZone;

import org.joda.time.DateTime;

public interface LocalDateResolver {
	TimeZone resolveTimeZone(final double latitude, final double longitude) throws Exception; 
	DateTime resolveDateTime(final String timeZoneId) throws Exception;
}
