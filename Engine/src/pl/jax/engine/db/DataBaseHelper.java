package pl.jax.engine.db;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.torque.Torque;

import pl.jax.config.Config;

public class DataBaseHelper {
	
	public static boolean initTorque() {
		try {
			Configuration conf = new BaseConfiguration();
			conf.setProperty("torque.database.default", Config.getConfig().getDbName());
			conf.setProperty("torque.database."	+ Config.getConfig().getDbName() + ".adapter", "postgresql");
			conf.setProperty("torque.dsfactory." + Config.getConfig().getDbName() + ".factory",	"org.apache.torque.dsfactory.SharedPoolDataSourceFactory");
			conf.setProperty("torque.dsfactory." + Config.getConfig().getDbName() + ".connection.url", Config.getConfig().getDbAddress());
			conf.setProperty("torque.dsfactory." + Config.getConfig().getDbName() + ".connection.driver", "org.postgresql.Driver");
			conf.setProperty("torque.dsfactory." + Config.getConfig().getDbName() + ".connection.user", Config.getConfig().getDbUser());
			conf.setProperty("torque.dsfactory." + Config.getConfig().getDbName() + ".connection.password", Config.getConfig().getDbPass());
			conf.setProperty("torque.dsfactory." + Config.getConfig().getDbName() + ".pool.maxActive", String.valueOf(Config.getConfig().getPoolConnectionSize()));
			
			Torque.USER_NAME = Config.getConfig().getDbUser();
			Torque.USER_PASSWD = Config.getConfig().getDbPass();
			
			Torque.init(conf);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
