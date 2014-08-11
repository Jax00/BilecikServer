package pl.jax.engine.log;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import pl.jax.engine.types.ResultCommand;

public class ResponseParser {
	
	public static void createResponse(ResultCommand result, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		if (result.isJSONData()) {
			createJSONDataResponse(result.asJSONData(), response);
		}
		else if (result.isError()) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private static void createJSONDataResponse(String jsonString, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			response.getWriter().write(jsonString);
		}
		catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

}
