package pl.jax.sawik.bilecik.app.users;

import pl.jax.engine.interfaces.IEventExecutor;
import pl.jax.engine.interfaces.ILogic;
import pl.jax.engine.interfaces.IRequestParser;
import pl.jax.engine.types.BaseModule;

public class UsersModule extends BaseModule {
	
	public static final String MODULE_NAME = "users";
	public static final String FRIENDLY_NAME = "Zarz¹dzanie U¿ytkownikami";

	@Override
	public String getName() {
		return MODULE_NAME;
	}

	@Override
	public String getFriendlyName() {
		return FRIENDLY_NAME;
	}

	@Override
	public IRequestParser getRequestParser() {
		return new UsersRequestParser();
	}

	@Override
	public ILogic getLogic() {
		return new UsersLogic();
	}

	@Override
	public IEventExecutor getEventExecutor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isRestricted() {
		return false;
	};
	
	@Override
	public boolean noLoginCheckForAction(String action) {
		switch (action) {
			case UsersRequestParser.LOGIN:
			case UsersRequestParser.REGISTER:
			case UsersRequestParser.CHANGE_PASSWORD:
			case UsersRequestParser.ACTIVATE:
				return true;
			default:
				return false;
		}
	}

}
