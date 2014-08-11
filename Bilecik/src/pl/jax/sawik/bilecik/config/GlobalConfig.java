package pl.jax.sawik.bilecik.config;

import pl.jax.sawik.bilecik.config.types.DeployConfig;
import pl.jax.sawik.bilecik.config.types.IAppConfig;

public class GlobalConfig {
	
	private static IAppConfig projectConfig = new DeployConfig();
	
	public static IAppConfig getConfig() {
		return projectConfig;
	}

}
