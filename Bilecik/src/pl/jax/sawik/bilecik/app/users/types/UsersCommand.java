package pl.jax.sawik.bilecik.app.users.types;

import pl.jax.engine.types.BaseCommand;
import pl.jax.sawik.databse.obj.Vuser;

public class UsersCommand extends BaseCommand {
	
	private enum UsersCommandType {
		LOGIN,
		REGISTER,
		ACTIVATE,
		CHANGE_PASSWORD,
	}
	
	UsersCommandType type;

	private UsersCommand(UsersCommandType type, Object p) {
		super(p);
		this.type = type;
	}
	
	public static UsersCommand createLogin(Object param) {
		return new UsersCommand(UsersCommandType.LOGIN, param);
	}
	
	public boolean isLogin() {
		return type==UsersCommandType.LOGIN;
	}
	
	public Vuser asLogin() {
		return (Vuser) param;
	}
	
	public static UsersCommand createRegister(Object param) {
		return new UsersCommand(UsersCommandType.REGISTER, param);
	}
	
	public boolean isRegister() {
		return type==UsersCommandType.REGISTER;
	}
	
	public Vuser asRegister() {
		return (Vuser) param;
	}
	
	public static UsersCommand createActivate(Object param) {
		return new UsersCommand(UsersCommandType.ACTIVATE, param);
	}
	
	public boolean isActivate() {
		return type==UsersCommandType.ACTIVATE;
	}
	
	public String asActivate() {
		return (String) param;
	}
	
	public static UsersCommand createChangePassword(Object param) {
		return new UsersCommand(UsersCommandType.CHANGE_PASSWORD, param);
	}
	
	public boolean isChangePassword() {
		return type==UsersCommandType.CHANGE_PASSWORD;
	}
	
	public Vuser asChangePassword() {
		return (Vuser) param;
	}

}
