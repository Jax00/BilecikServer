package pl.jax.engine.types;

public class CommandHolder {
	
	private final BaseCommand cmd;
	private final String moduleName;
	
	public CommandHolder(String moduleName, BaseCommand cmd) {
		this.cmd = cmd;
		this.moduleName = moduleName;
	}
	
	public BaseCommand getCommand() {
		return cmd;
	}
	
	public String getModuleName() {
		return moduleName;
	}

}
