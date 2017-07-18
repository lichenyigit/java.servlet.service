package java.servlet.service.listenner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class Listenner implements ServletContextListener {
	private static Logger logger = LogManager.getLogger(Listenner.class);
	
	private static BasicDataSource bds = null;
	private static Context rootCtx = null;
	private static final String JNDI_PROPERTIES = "jdbc/2017_summer/properties";
	
	public void contextInitialized(ServletContextEvent sce){
		Context ctx;
		try {
			ctx = new InitialContext();
			rootCtx = (Context) ctx.lookup("java:/comp/env");
		} catch (NamingException e) {
			return;
		}
		
		logger.info("2017暑期活动初始化成功");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	private static BasicDataSource getDataSource() {
		if (bds == null) {
			Properties properties = new Properties();
			try {
				Context ctx = (Context) rootCtx.lookup(JNDI_PROPERTIES);
				NamingEnumeration<NameClassPair> pairs = rootCtx.list(JNDI_PROPERTIES);
				NameClassPair pair = null;
				while(true){
					try{
						pair = pairs.nextElement();
					}catch(java.util.NoSuchElementException e){
						break;
					}
					properties.setProperty(pair.getName(),(String) getJndi(ctx, pair.getName(), null));
				}
				try {
					bds = BasicDataSourceFactory.createDataSource(properties);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (NamingException e1) {
				e1.printStackTrace();
			}
		}
		return bds;
	}
	
	public static Connection getDbConn(){
		return getDbConn(false);
	}
	
	public static Connection getDbConn(boolean autoCommit){
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return conn;
	}
	
	@SuppressWarnings("all")
	private static <T> T getJndi(String jndiName,T defVal){
		return getJndi(rootCtx, jndiName, defVal);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getJndi(Context ctx,String jndiName,T defVal){
		if(ctx != null){
			try {
				return (T) ctx.lookup(jndiName);
			} catch (NamingException e) {
				logger.warn(e.getMessage());
			}
		}
		return defVal;
	}
	
	public static String getJndiString(String jndi,String defaultVal){
		try{
			return (String) rootCtx.lookup(jndi);
		}catch(Exception e){
			return defaultVal;
		}
	}
	
	public static String getJndiString(String jndi){
		return getJndiString(jndi, null);
	}

}
