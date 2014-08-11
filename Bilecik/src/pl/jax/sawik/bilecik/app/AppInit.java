package pl.jax.sawik.bilecik.app;

import pl.jax.engine.log.registeries.ModuleRegistry;
import pl.jax.sawik.bilecik.app.users.UsersModule;

public class AppInit {
	
	public static void registerModules() {
		ModuleRegistry registry = ModuleRegistry.getInstance();
		registry.register(UsersModule.MODULE_NAME, new UsersModule());
	}

}
