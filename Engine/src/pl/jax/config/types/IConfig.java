package pl.jax.config.types;

import org.apache.torque.util.DbContext;

public interface IConfig {
	
	public DbContext getPublicContext();

	//DB config
	public String getDbUser();
	public String getDbPass();
	public String getDbName();
	public String getDbAddress();
	public int getPoolConnectionSize();
	
	//Session config
	public long getSessionTime();
	public void setSessionTime(long SessionTime);
	
}
