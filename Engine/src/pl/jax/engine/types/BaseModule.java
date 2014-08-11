package pl.jax.engine.types;

import pl.jax.engine.interfaces.IEventExecutor;
import pl.jax.engine.interfaces.ILogic;
import pl.jax.engine.interfaces.IRequestParser;

public abstract class BaseModule {
	
	public static final String MODULE_NAME = "base";
	public static final String FRIENDLY_NAME = "Modu³y podstawowe";
	
	public abstract String getName();
	public abstract String getFriendlyName();
	
	public abstract IRequestParser getRequestParser();
	public abstract ILogic getLogic();
	
	// Disable permission for this module
	public boolean isRestricted() {
		return true;
	}
	
	// Disable login checking for actions
	public boolean noLoginCheckForAction(String action) {
		return false;
	}
	
	public abstract IEventExecutor getEventExecutor();

}
