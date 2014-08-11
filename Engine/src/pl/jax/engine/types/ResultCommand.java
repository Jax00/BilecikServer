package pl.jax.engine.types;

public class ResultCommand {
	
	private final ResultCommandType type;
	private final Object param;
	
	private ResultCommand(ResultCommandType type, Object param) {
		this.type = type;
		this.param = param;
	}
	
	public static ResultCommand createJSONData(String jsonString) {
		return new ResultCommand(ResultCommandType.JSON_DATA, jsonString);
	}
	
	public boolean isJSONData() {
		return type==ResultCommandType.JSON_DATA;
	}
	
	public String asJSONData() {
		return (String) param;
	}
	
	public static ResultCommand createError() {
		return new ResultCommand(ResultCommandType.ERROR, null);
	}
	
	public boolean isError() {
		return type==ResultCommandType.ERROR;
	}

}
