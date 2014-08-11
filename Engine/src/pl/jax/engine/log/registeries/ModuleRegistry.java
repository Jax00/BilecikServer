package pl.jax.engine.log.registeries;

import java.util.HashMap;
import java.util.Map;

import pl.jax.engine.types.BaseModule;

public class ModuleRegistry {
	
	private static ModuleRegistry instance = null;
	private Map<String, BaseModule> registry = new HashMap<String, BaseModule>(); 
	
	private ModuleRegistry() {}
	
	public static ModuleRegistry getInstance() {
		if (instance == null) {
			instance = new ModuleRegistry();
		}
		return instance;
	}
	
	public void register(String key, BaseModule module) {
		registry.put(key, module);
	}
	
	public BaseModule getModule(String key) throws Exception {
		BaseModule module = registry.get(key);
		if (module == null) {
			throw new Exception("Module '" + key + "' does not exist");
		}
		return module;
	}

}
