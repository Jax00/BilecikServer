package pl.jax.sawik.bilecik.servlets;

import javax.servlet.ServletContext;

import pl.jax.config.Config;
import pl.jax.engine.servlets.BaseServlet;
import pl.jax.sawik.bilecik.app.AppInit;
import pl.jax.sawik.bilecik.config.GlobalConfig;
import pl.jax.sawik.bilecik.config.types.IAppConfig;

public class MainServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void onInit() {
		setupConfiguration(this.getServletContext());
		Config.init(GlobalConfig.getConfig());
	}

	@Override
	protected void afterInit() {
		AppInit.registerModules();
		System.out.println("Application Started");
	}
	
	private void setupConfiguration(ServletContext servletContext) {
		IAppConfig appConfig = GlobalConfig.getConfig();
		String dbUser = servletContext.getInitParameter("DB_USER");
		String dbPass = servletContext.getInitParameter("DB_PASS");
		String dbName = servletContext.getInitParameter("DB_NAME");
		String dbAddress = servletContext.getInitParameter("DB_ADDRESS");
		Integer poolConnectionSize = Integer.parseInt(servletContext.getInitParameter("POOL_CONNECTION_SIZE"));
		appConfig.initDatabase(dbUser, dbPass, dbName, dbAddress, poolConnectionSize);
	}

}
