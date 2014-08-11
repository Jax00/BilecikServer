package pl.jax.engine.tools.json;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class JSONParser {

	Map json = null;

	public JSONParser(String text) throws Exception {
		json = (Map) new org.json.simple.parser.JSONParser().parse(text);
	}

	public Map getMap() {
		return json;
	}

	public JSONParser getChild(String key) throws Exception {
		return new JSONParser(getParam(key).toString());
	}

	public Object getParam(String key) throws Exception {
		try {
			String keys[] = key.split("\\.");
			Map obj = json;

			for (int i = 0; i < keys.length - 1; i++)
				obj = (Map) obj.get(keys[i]);

			return obj.get(keys[keys.length - 1]);
		} catch (Exception e) {
			throw new Exception(
					"Error while parsing the JSON text - most likely the key you have provided was not found in the JSON text.");
		}
	}

	public String getString(String key) throws Exception {
		return getParam(key).toString();
	}

	public int getInteger(String key) throws Exception {
		return ((Long) getParam(key)).intValue();
	}

	public boolean getBoolean(String key) throws Exception {
		Object o = getParam(key);
		if (o instanceof Boolean)
			return (Boolean) getParam(key);
		return o.toString().equalsIgnoreCase("true");
	}

	public String getStringSafe(String key) {
		try {
			return getString(key);
		} catch (Exception e) {
			return new String();
		}
	}

	public boolean getBooleanSafe(String key) {
		try {
			return getBoolean(key);
		} catch (Exception e) {
			return false;
		}
	}
}

