package pl.jax.sawik.bilecik.app.users;

import javax.servlet.http.HttpServletRequest;
import javax.tools.Tool;

import pl.jax.engine.interfaces.IRequestParser;
import pl.jax.engine.types.BaseCommand;
import pl.jax.engine.types.CommandHolder;
import pl.jax.sawik.bilecik.app.Tools;
import pl.jax.sawik.bilecik.app.users.types.UsersCommand;
import pl.jax.sawik.databse.obj.Vuser;

public class UsersRequestParser implements IRequestParser {
	
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";
	public static final String ACTIVATE = "activate";
	public static final String CHANGE_PASSWORD = "changePassword";

	@Override
	public CommandHolder parse(HttpServletRequest request) {
		
		BaseCommand cmd = null;
		
		String action = Tools.getNotNullString(request, "action");
		String view = Tools.getNotNullString(request, "view");
		
		if (action.equalsIgnoreCase(LOGIN)) {
			cmd = UsersCommand.createLogin(parseVuser(request));
		} else if (action.equalsIgnoreCase(REGISTER)) {
			cmd = UsersCommand.createRegister(parseVuser(request));
		}
		
		if (cmd != null) {
			return new CommandHolder(UsersModule.MODULE_NAME, cmd);
		}
		else {
			return null;
		}
	}
	
	private Vuser parseVuser(HttpServletRequest request) {
		try {
			Vuser user = new Vuser();
			Integer id = Tools.getInteger(request, "id");
			if (id != null) {
				user.setId(id);
			}
			user.setLogin(Tools.getString(request, "login"));
			user.setPassword(Tools.getString(request, "password"));
			user.setEmail(Tools.getString(request, "email"));
			return user;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
