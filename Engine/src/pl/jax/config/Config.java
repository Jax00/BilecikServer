package pl.jax.config;

import pl.jax.config.types.IConfig;

public class Config {
	
	private static IConfig config = null;
	
	public static void init(IConfig config_) {
		config = config_;
	}
	
	public static IConfig getConfig() {
		return config;
	}

}
