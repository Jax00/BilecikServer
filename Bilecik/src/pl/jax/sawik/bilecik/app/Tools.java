package pl.jax.sawik.bilecik.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import pl.jax.engine.tools.Convert;
import pl.jax.engine.tools.InputValidator;

/**
 * 
 * @author Rafa³ Podkoñski (RPI)
 * 
 */
public class Tools {

	/**
	 * Kod który proponujê do³¹czyc do com.innotion.engine.tools.InputValidator,
	 * z pe³n¹ obs³ug¹ wyj¹tków.
	 */

	/**
	 * Sta³a wykorzystywana przy pobieraniu liczb ca³kowitych z request'a
	 */
	public static final int INT_VALIDATION_FAILURE = -1000000000;

	/**
	 * Bezpieczne wyci¹ganie atrybutu typu int z requestu. Dzia³a niepoprawnie
	 * jeœli wartoœæ atrybutu == INT_VALIDATION_FAILURE
	 * 
	 * @param request
	 * @param attrName
	 * @return wartoœæ atrybutu lub null
	 */
	public static Integer getInteger(HttpServletRequest request, String attrName) {
		return getInteger(request.getParameter(attrName));
	}

	/**
	 * Bezpieczne wyci¹ganie atrybutu typu int ze Stringa. Dzia³a niepoprawnie
	 * jeœli wartoœæ atrybutu == INT_VALIDATION_FAILURE
	 * 
	 * @param value
	 * @return wartoœæ atrybutu lub null
	 */
	public static Integer getInteger(String value) {
		int val = InputValidator.validate(value, INT_VALIDATION_FAILURE);
		if (val != INT_VALIDATION_FAILURE)
			return val;
		return null;
	}

	/**
	 * Bezpieczne wyci¹ganie atrybutu typu boolean z requestu.
	 * 
	 * @param request
	 * @param attrName
	 * @return wartoœæ boolowska lub null
	 */
	public static Boolean getBoolean(HttpServletRequest request, String attrName) {
		String s = InputValidator
				.validate(request.getParameter(attrName), null);
		if (s != null)
			try {
				return Boolean.parseBoolean(s);
			} catch (Exception e) {
			}
		return null;
	}

	public static Boolean getBoolean(String s) {
		if (s != null)
			try {
				return Boolean.parseBoolean(s);
			} catch (Exception e) {
			}
		return null;
	}

	/**
	 * Bezpieczne wyci¹ganie atrybutu typu String z requestu.
	 * 
	 * @param request
	 * @param attrName
	 * @return 'bezpieczny' String lub null
	 */
	public static String getEncodedString(HttpServletRequest request,
			String attrName) {
		try {
			return InputValidator.encode(getString(request, attrName));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getString(HttpServletRequest request, String attrName) {
		return InputValidator.validate(request.getParameter(attrName), null);
	}

	public static String getString(HttpServletRequest request, String attrName,
			boolean encoded) {
		if (encoded)
			return getEncodedString(request, attrName);
		else
			return getString(request, attrName);
	}

	public static String getNotNullString(HttpServletRequest request,
			String attrName) {
		return InputValidator.validate(request.getParameter(attrName), "");
	}

	public static Date getDate(HttpServletRequest request, String attrName) {
		try {
			return Convert.stringToDate(getString(request, attrName));
		} catch (Exception ex) {
			return null;
		}
	}

	public static BigDecimal getBigDecimal(HttpServletRequest request,
			String attrName) {
		try {
			return new BigDecimal(getString(request, attrName).replaceAll(",",
					"."));
		} catch (Exception e) {
			return null;
		}
	}

	public static Date tomorrowAtMidnight() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);
		return calendar.getTime();
	}

	public static Date partToDate(String part) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(part);
		} catch (Exception ex) {
			return null;
		}
	}

	public static String partToString(String part) throws IOException {
		if (part == null) {
			return null;
		}
		return part;
	}

	public static Integer partToInt(String part) throws IOException {
		try {
			return Integer.parseInt(part);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean partToBoolean(String part) throws IOException {
		return Boolean.parseBoolean(part);
	}

	public static BigDecimal partToBigDecimal(String part) {
		try {
			return new BigDecimal(part.replaceAll(",", "."));
		} catch (Exception e) {
			return null;
		}
	}

	public static Long getLong(String initParameter) {
		Long ret = null;
		try {
			ret = Long.parseLong(initParameter);
		} catch (NumberFormatException ex) {
			ret = null;
		}
		return ret;
	}

	public static String removeAccents(String text) {
	    return text == null ? null
	        : Normalizer.normalize(text.replaceAll("£", "L").replaceAll("³","l"), Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
}
