package pl.jax.engine.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {
	
	private static DateFormat createTimeFormat() {
		return new SimpleDateFormat("H:mm");
	}
	
	private static DateFormat createDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public static Date stringToDateFormat(String string, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(string);
	}
	
	public static Date stringToTime(String string) throws ParseException {
		return createTimeFormat().parse(string);
	}
	
	public static Date stringToDate(String string) throws ParseException {
		return createDateFormat().parse(string);
	}
	
	public static String dateToString(Date date) {
		return createDateFormat().format(date);
	}
	
	public static String timeToString(Date date) {
		return createTimeFormat().format(date);
	}

}
