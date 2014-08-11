package pl.jax.engine.log;

import javax.servlet.http.HttpServletRequest;

import pl.jax.engine.log.registeries.ModuleRegistry;
import pl.jax.engine.types.CommandHolder;

public class RequestParser {
	
	public static CommandHolder tryConvert(HttpServletRequest request) {
		try {
			String module = request.getParameter("module");
			return ModuleRegistry.getInstance().getModule(module).getRequestParser().parse(request);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
