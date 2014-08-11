package pl.jax.engine.interfaces;

import javax.servlet.http.HttpServletRequest;

import pl.jax.engine.types.CommandHolder;

public interface IRequestParser {
	
	public CommandHolder parse(HttpServletRequest request);

}
