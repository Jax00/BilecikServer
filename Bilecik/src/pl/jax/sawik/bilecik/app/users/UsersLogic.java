package pl.jax.sawik.bilecik.app.users;

import java.sql.Connection;
import java.util.List;

import org.apache.torque.TorqueException;
import org.apache.torque.util.Transaction;
import org.apache.torque.util.Criteria;
import org.apache.torque.util.DbContext;

import pl.jax.engine.interfaces.ILogic;
import pl.jax.engine.log.crypto.InnCrypto;
import pl.jax.engine.log.sessions.SessionManager;
import pl.jax.engine.tools.ResultCommandHelper;
import pl.jax.engine.tools.json.JSONBuilder;
import pl.jax.engine.types.BaseCommand;
import pl.jax.engine.types.ResultCommand;
import pl.jax.sawik.bilecik.app.users.types.Json;
import pl.jax.sawik.bilecik.app.users.types.UsersCommand;
import pl.jax.sawik.databse.obj.Sessions;
import pl.jax.sawik.databse.obj.Vuser;
import pl.jax.sawik.databse.obj.VuserPeer;

public class UsersLogic implements ILogic {

	@Override
	public ResultCommand execute(BaseCommand cmd, Vuser user, DbContext dbContext) {
		
		UsersCommand command = (UsersCommand) cmd;
		
		if (command.isLogin()) {
			return executeLogin(command.asLogin(), dbContext);
		} else if (command.isRegister()) {
			return executeRegister(command.asRegister(), dbContext);
		}
		
		return ResultCommandHelper.createJSONSuccess(false);
	}
	
	private ResultCommand executeLogin(Vuser user, DbContext dbContext) {
		Criteria c = new Criteria();
		c.add(VuserPeer.LOGIN, user.getLogin());
		try {
			List<Vuser> us = VuserPeer.doSelect(c, dbContext);
			if (us.isEmpty()) {
				throw new Exception("No such user");
			}
			Vuser u = us.get(0);
			if (!u.getActive())
				throw new Exception("Active user first");
			String pass = InnCrypto.hashPassword(user.getPassword(), u.getSalt());
			if (!pass.equals(u.getPassword())) {
				throw new Exception("Wrong password"); 
			}
			Sessions s = SessionManager.createSession(u);
			JSONBuilder json = ResultCommandHelper.createGenericSuccess(true);
			try {
				json.item(Json.SESSION_ID, s.getSessionId());
				json.compoundItemBegin("user");
				json = sendVuser(json, u);
				json.compoundItemEnd();
			}
			catch (Exception e) {
			}
			return ResultCommand.createJSONData(json.generateJSONString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ResultCommandHelper.createJSONSuccess(false);
	}
	
	private ResultCommand executeRegister(Vuser user, DbContext dbContext) {
		try {
			if (!canRegister(user, dbContext))
				return ResultCommandHelper.createJSONSuccess(false);
		} catch (TorqueException e1) {
			e1.printStackTrace();
			return ResultCommandHelper.createJSONSuccess(false);
		}
		user.setNew(true);
		user.setActive(false);
		String activationCode = null;
		try {
			while (true) {
				activationCode = InnCrypto.generateSalt(64);
				Criteria c = new Criteria();
				c.add(VuserPeer.ACTIVATION_CODE, activationCode);
				if (VuserPeer.doSelect(c, dbContext).size() == 0)
					break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResultCommandHelper.createJSONSuccess(false);
		}
		user.setActivationCode(activationCode);
		user.setSalt(InnCrypto.generateSalt(256));
		user.setPassword(InnCrypto.hashPassword(user.getPassword(), user.getSalt()));
		Connection con = null;
		try {
			con = Transaction.begin(dbContext);
			user.save(con);
			Transaction.commit(con);
		}
		catch (Exception e) {
			if (con != null)
				Transaction.safeRollback(con);
			e.printStackTrace();
			return ResultCommandHelper.createJSONSuccess(false);
		}
		return ResultCommandHelper.createJSONSuccess(true);
	}
	
	private boolean canRegister(Vuser user, DbContext dbContext) throws TorqueException {
		boolean result = true;
		if (user.getEmail() == null || user.getEmail().equals(""))
			result = false;
		if (user.getLogin() == null || user.getLogin().equals(""))
			result = false;
		if (user.getPassword() == null || user.getPassword().equals(""))
			result = false;
		if (VuserPeer.doSelect(new Criteria().add(VuserPeer.LOGIN, user.getLogin()), dbContext).size() > 0)
			result = false;
		return result;
	}
	
	private JSONBuilder sendVuser(JSONBuilder json, Vuser user) throws Exception {
		json.item(Json.ID, user.getId());
		json.item(Json.LOGIN, user.getLogin());
		json.item(Json.POINTS, user.getPoints());
		return json;
	}

}
