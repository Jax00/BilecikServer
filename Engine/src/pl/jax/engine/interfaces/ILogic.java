package pl.jax.engine.interfaces;

import org.apache.torque.util.DbContext;

import pl.jax.engine.types.BaseCommand;
import pl.jax.engine.types.ResultCommand;
import pl.jax.sawik.databse.obj.Vuser;

public interface ILogic {
	
	public ResultCommand execute(BaseCommand cmd, Vuser user, DbContext dbContext);

}
