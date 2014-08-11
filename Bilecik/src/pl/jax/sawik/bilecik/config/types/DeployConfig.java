package pl.jax.sawik.bilecik.config.types;

import org.apache.torque.util.DbContext;

public class DeployConfig implements IAppConfig {
	
	private DbContext publicContext = null;
	private String dbUser;
	private String dbPass;
	private String dbName;
	private String dbAddress;
	private long sessionTime = 1800000L;
	private Integer poolConnectionSize;

	@Override
	public DbContext getPublicContext() {
		if (publicContext == null)
			publicContext  = new DbContext(getDbUser(), getDbPass());
		return publicContext;
	}

	@Override
	public String getDbUser() {
		return dbUser;
	}

	@Override
	public String getDbPass() {
		return dbPass;
	}

	@Override
	public String getDbName() {
		return dbName;
	}

	@Override
	public String getDbAddress() {
		return dbAddress;
	}

	@Override
	public int getPoolConnectionSize() {
		return poolConnectionSize;
	}

	@Override
	public long getSessionTime() {
		return sessionTime;
	}

	@Override
	public void setSessionTime(long sessionTime) {
		this.sessionTime = sessionTime;
	}

	@Override
	public void initDatabase(String dbUser, String dbPass, String dbName,
			String dbAddress, Integer poolConnectionSize) {
		this.dbUser = dbUser;
		this.dbPass = dbPass;
		this.dbName = dbName;
		this.dbAddress = dbAddress;
		this.poolConnectionSize = poolConnectionSize;

	}

}
