package pl.jax.engine.log.sessions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.torque.util.Criteria;
import org.apache.torque.util.DbContext;

import pl.jax.config.Config;
import pl.jax.engine.log.crypto.InnCrypto;
import pl.jax.sawik.databse.obj.Sessions;
import pl.jax.sawik.databse.obj.SessionsPeer;
import pl.jax.sawik.databse.obj.Vuser;

public class SessionManager {
	
	private static Date generateExpiryTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + Config.getConfig().getSessionTime());
		return cal.getTime();
	}
	
	private static void prolongExpiryDate(Sessions s) {
		try {
			s.setExpiryTime(generateExpiryTime());
			s.save();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void removeOtherSessionsByUser(Vuser user) throws Exception {
		Criteria c = new Criteria();
		c.add(SessionsPeer.VUSER_ID, user.getId());
		SessionsPeer.doDelete(c, Config.getConfig().getPublicContext());
	}
	
	public static Sessions createSession(Vuser user) throws Exception {
		String sessionId = null;
		DbContext dbContext = Config.getConfig().getPublicContext();
		while (true) {
			sessionId = InnCrypto.createSessionId(user.getLogin());
			if (SessionsPeer.doSelect(new Criteria().add(SessionsPeer.SESSION_ID, sessionId), dbContext).size() == 0) {
				break;
			}
			
		}
		removeOtherSessionsByUser(user);
		
		Sessions s = new Sessions(dbContext);
		s.setSessionId(sessionId);
		s.setExpiryTime(generateExpiryTime());
		s.setVuser(user);
		s.save();
		return s;
	}
	
	public static void deleteSession(Vuser user) {
		try {
			removeOtherSessionsByUser(user);
		}
		catch (Exception e) {
		}
	}
	
	public static boolean isValidSession(Sessions s) {
		return (s.getExpiryTime().getTime() >= Calendar.getInstance().getTime().getTime());
	}
	
	private static String getSessionFromCookies(Cookie[] cookies) {
		if (cookies == null)
			return null;
		
		for (int i=0; i<cookies.length; i++) {
			if (new String("session").equalsIgnoreCase(cookies[i].getName()))
				return cookies[i].getValue();
		}
		
		return null;
	}
	
	public static Vuser getUserFromSession(HttpServletRequest request, DbContext dbContext) throws Exception {
		String sessionId = getSessionFromCookies(request.getCookies());
		if (sessionId == null)
			throw new Exception("Session is null");
		
		Criteria c = new Criteria();
		c.add(SessionsPeer.SESSION_ID, sessionId);
		List<Sessions> sessions = SessionsPeer.doSelect(c, dbContext);
		
		if (sessions.isEmpty())
			throw new Exception("No sessions with such id");
		
		Sessions s = sessions.get(0);
		
		if (!isValidSession(s))
			throw new Exception("This session has already expired.");
		
		prolongExpiryDate(s);
		
		return s.getVuser();
	}

}
