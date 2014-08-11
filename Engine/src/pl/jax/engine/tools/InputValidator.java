package pl.jax.engine.tools;

import java.io.UnsupportedEncodingException;

public class InputValidator {

	public static final String DEFAULT_INPUT_CHARSET = "iso-8859-1";
	public static final String DEFAULT_OUTPUT_CHARSET = "UTF-8";

	public static String validate(String value) {
		return validate(value, new String());
	}

	public static Integer validate(Integer value) {
		if (value == null)
			return -1;
		return value;
	}

	// ta funkcja powinna zapobiegac mozliwym atakom sql injection
	public static String validate(String value, String def) {
		if (value != null)
			return value.trim();
		return def;
	}

	public static Double validate(String value, double def) {
		try {
			String result = validate(value, Double.toString(def));
			result = result.replace(",", ".");
			return Double.parseDouble(result);
		} catch (Exception e) {
			return def;
		}
	}

	// sprawdz czy podana wartosc jest liczba
	public static Integer validate(String value, int def) {
		try {
			String result = validate(value, Integer.toString(def));
			return Integer.parseInt(result);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static boolean validate(String value, boolean def) {
		String result = validate(value, Boolean.toString(def));
		if (result.equalsIgnoreCase("true") || result.equalsIgnoreCase("false")) {
			return result.equalsIgnoreCase("true");
		}
		return def;
	}

	public static Object validate(Object value) {
		if (Integer.class.isInstance(value))
			return validate((Integer) value);
		if (String.class.isInstance(value))
			return validate((String) value);
		return value;
	}

	public static String encode(String param, String from, String to)
			throws UnsupportedEncodingException {
		return new String(param.getBytes(from), to);
	}

	public static String encode(String param)
			throws UnsupportedEncodingException {
		return encode(param, DEFAULT_INPUT_CHARSET, DEFAULT_OUTPUT_CHARSET);
	}

	public static String leaveOnlyNumbers(String param) {
		return validate(param).replaceAll("[^0-9]", "");
	}
}
