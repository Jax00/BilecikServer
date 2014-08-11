package pl.jax.engine.tools.json;

import java.util.LinkedList;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class JSONBuilder {

	StringBuffer sb = new StringBuffer();
	boolean lastAdded = false;
	LinkedList<Boolean> commaOnLevel = new LinkedList<Boolean>();

	public static String empty() {
		return "{}";
	}

	public static String entry(String property, String value) {
		try {
			JSONBuilder json = new JSONBuilder();
			json.item(property, value);
			return json.generateJSONString();
		} catch (Exception e) {
			return new String();
		}
	}

	public static String entry(String property, int value) {
		try {
			JSONBuilder json = new JSONBuilder();
			json.itemAsInt(property, value);
			return json.generateJSONString();
		} catch (Exception e) {
			return new String();
		}
	}

	public static String entry(String property, boolean value) {
		try {
			JSONBuilder json = new JSONBuilder();
			json.item(property, value);
			return json.generateJSONString();
		} catch (Exception e) {
			return new String();
		}
	}

	public JSONBuilder() {
		levelBegin();
	}

	@Override
	public String toString() {
		try {
			return generateJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return sb.toString();
		}
	}

	public String generateJSONString() throws Exception {
		if (!lastAdded) {
			levelEnd();
			lastAdded = true;
			if (commaOnLevel.size() != 0)
				throw new Exception(
						"Invalid costrunction of the JSON former requests.");
		}
		return sb.toString();
	}

	private void genericLevelBegin(String levelIndicator) {
		sb.append(levelIndicator);
		commaOnLevel.addLast(false);
	}

	private void genericLevelEnd(String levelIndicator) {
		sb.append(levelIndicator);
		commaOnLevel.removeLast();
	}

	private void levelBegin() {
		genericLevelBegin("{");
	}

	private void levelEnd() {
		genericLevelEnd("}");
	}

	private void listLevelBegin() {
		genericLevelBegin("[");
	}

	private void listLevelEnd() {
		genericLevelEnd("]");
	}

	private void commaIfNeeded() throws Exception {
		if (commaOnLevel.isEmpty())
			throw new Exception(
					"Invalid costrunction of the JSON former requests.");

		if (!commaOnLevel.getLast()) {
			commaOnLevel.removeLast();
			commaOnLevel.addLast(true);
		} else
			sb.append(",");
	}

	public void value(String value) throws Exception {
		if (value == null)
			value = new String();

		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(value) + "\"");
	}

	public void value(int value) throws Exception {
		commaIfNeeded();
		sb.append(value);
	}

	public void value(boolean value) throws Exception {
		commaIfNeeded();
		sb.append(value);
	}

	public void itemObject(String property, Object value) throws Exception {
		if (value instanceof Boolean)
			item(property, (Boolean) value);
		else if (value instanceof Integer)
			itemAsInt(property, (Integer) value);
		else {
			if (value == null)
				value = new String();
			item(property, value.toString());
		}
	}

	public void item(String property, String value) throws Exception {
		if (value == null) {
			value = "";
		}

		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		sb.append("\"" + JSONObject.escape(value) + "\"");
	}

	public void item(String property, JSONAware value) throws Exception {
		if (value == null)
			item(property, "");
		else
			itemNoQuote(property, value.toJSONString());
	}

	public void itemNoQuote(String property, String value) throws Exception {
		if (value == null) {
			value = "";
		}

		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		sb.append(value);
	}

	public void item(String property, boolean value) throws Exception {
		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		sb.append(value);
	}

	public void item(String property, int value) throws Exception {
		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		sb.append("\"" + value + "\"");
	}
	
	public void item(String property, double value) throws Exception {
		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		sb.append("\"" + value + "\"");
	}

	public void itemAsInt(String property, int value) throws Exception {
		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		sb.append(value);
	}

	public void compoundItemBegin(String property) throws Exception {
		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		levelBegin();
	}

	public void compoundItemEnd() {
		levelEnd();
	}

	public void listBegin(String property) throws Exception {
		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		listLevelBegin();
	}

	public void listEnd() {
		listLevelEnd();
	}

	public void listItemBegin() throws Exception {
		commaIfNeeded();
		levelBegin();
	}

	public void listItemEnd() {
		levelEnd();
	}

	public void itemAsDate(String property, long value) throws Exception {
		commaIfNeeded();
		sb.append("\"" + JSONObject.escape(property) + "\"");
		sb.append(":");
		sb.append("new Date(" + value + ")");
	}
}
