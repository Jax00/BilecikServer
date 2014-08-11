package pl.jax.engine.tools;

import pl.jax.engine.tools.json.JSONBuilder;
import pl.jax.engine.types.ResultCommand;

public class ResultCommandHelper {
	
	public static ResultCommand createSuccess(Boolean success, int id) {
		try {
			JSONBuilder json = ResultCommandHelper.createGenericSuccess(success);
			json.item("id", id);
			return ResultCommand.createJSONData(json.generateJSONString());
		}
		catch (Exception e) {
			return ResultCommand.createError();
		}
	}
	
	public static ResultCommand createSuccess(Boolean success, int id, String extra) {
		try {
			JSONBuilder json = ResultCommandHelper.createGenericSuccess(success);
			json.item("id", id);
			json.item("extra", extra);
			return ResultCommand.createJSONData(json.generateJSONString());
		}
		catch (Exception e) {
			return ResultCommand.createError();
		}
	}
	
	public static ResultCommand createUnsuccessful(String reason) throws Exception {
		JSONBuilder json = ResultCommandHelper.createGenericSuccess(false);
		json.item("reason", reason);
		return ResultCommand.createJSONData(json.generateJSONString());
	}

	public static JSONBuilder createGenericSuccess(Boolean success) throws Exception {
		JSONBuilder json = new JSONBuilder();
		json.item("success", success);
		return json;
	}
	
	public static ResultCommand createJSONSuccess(Boolean success) {
		return ResultCommand.createJSONData("{\"success\":" + success.toString() + "}");
	}
	
	public static ResultCommand createJSONSuccess(Boolean success, String reason) {
		try {
			JSONBuilder json = ResultCommandHelper.createGenericSuccess(success);
			json.item("reason", reason);
			return ResultCommand.createJSONData(json.generateJSONString());
		}
		catch (Exception e) {
			return ResultCommand.createError();
		}
	}

}
