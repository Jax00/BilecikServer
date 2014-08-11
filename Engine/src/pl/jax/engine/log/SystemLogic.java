package pl.jax.engine.log;

import org.apache.torque.util.DbContext;

import pl.jax.engine.log.registeries.ModuleRegistry;
import pl.jax.engine.types.CommandHolder;
import pl.jax.engine.types.ResultCommand;
import pl.jax.sawik.databse.obj.Vuser;

public class SystemLogic {
	
	public static ResultCommand execute(CommandHolder cmd, Vuser user, DbContext dbContext) {
		try {
			return ModuleRegistry.getInstance().getModule(cmd.getModuleName()).getLogic().execute(cmd.getCommand(), user, dbContext);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResultCommand.createError();
		}
	}

}
