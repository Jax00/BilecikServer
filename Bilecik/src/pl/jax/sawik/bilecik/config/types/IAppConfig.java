package pl.jax.sawik.bilecik.config.types;

import pl.jax.config.types.IConfig;

public interface IAppConfig extends IConfig {
	
	public void initDatabase(String dbUser, String dbPass, String dbName, String dbAddress, Integer poolConnectionSize);

}
