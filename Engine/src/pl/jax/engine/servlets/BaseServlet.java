package pl.jax.engine.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.torque.util.BasePeer;
import org.apache.torque.util.DbContext;

import pl.jax.config.Config;
import pl.jax.engine.db.DataBaseHelper;
import pl.jax.engine.log.RequestParser;
import pl.jax.engine.log.ResponseParser;
import pl.jax.engine.log.SystemLogic;
import pl.jax.engine.log.registeries.ModuleRegistry;
import pl.jax.engine.log.sessions.SessionManager;
import pl.jax.engine.tools.ResultCommandHelper;
import pl.jax.engine.types.BaseModule;
import pl.jax.engine.types.CommandHolder;
import pl.jax.engine.types.ResultCommand;
import pl.jax.sawik.databse.obj.SessionsPeer;
import pl.jax.sawik.databse.obj.Vuser;

public abstract class BaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public BaseServlet() {
		super();
	}
	
	protected void initData() {
		
	}
	
	protected abstract void onInit();
	
	protected abstract void afterInit();
	
	@Override
	public final void init(ServletConfig config) throws ServletException {
		super.init(config);
		onInit();
		if (DataBaseHelper.initTorque()) {
		}
		afterInit();
	}

	public void doWork(HttpServletRequest request, HttpServletResponse response) throws IOException {
		initData();
		ResultCommand result = null;
		DbContext dbContext = Config.getConfig().getPublicContext();
		try {
			CommandHolder command = RequestParser.tryConvert(request);
			if (command == null) {
				ResponseParser.createResponse(ResultCommandHelper.createJSONSuccess(false), response);
				return;
			}
			String action = request.getParameter("action");
			BaseModule module = ModuleRegistry.getInstance().getModule(command.getModuleName());
			if (module.isRestricted()) {
				if (ModuleRegistry.getInstance().getModule(command.getModuleName()).noLoginCheckForAction(action)) {
					result = SystemLogic.execute(command, null, dbContext);
				}
				else {
					Vuser user = SessionManager.getUserFromSession(request, dbContext);
					if (user != null) {
						result = SystemLogic.execute(command, user, dbContext);
					}
					else {
						result = ResultCommand.createError();
					}
				}
			}
			else {
				result = SystemLogic.execute(command, null, dbContext);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			result = ResultCommand.createError();
		}
		ResponseParser.createResponse(result, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doWork(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doWork(request, response);
	}

}
