package java.servlet.service.util;

import java.servlet.service.listenner.Listenner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DBUtil {
	private static final Logger logger = LogManager.getLogger();
	public static Connection getConnection(){
		Connection conn = Listenner.getDbConn();
		return conn;
	}
	
	public static String insertWithAutoId(PreparedStatement pstmt, int paramIndex, IdGenerator idGenerator) throws SQLException{
		int retryCount = 5;
		String id = null;
		while(true){
			id = idGenerator.getId();
			try{
				pstmt.setString(paramIndex, id);
				pstmt.executeUpdate();
				break;
			} catch (SQLException e) {
				if (--retryCount <= 0 || 1062 != e.getErrorCode() || !"23000".equals(e.getSQLState())) {
					throw e;
				}
				logger.debug("Duplicate Id:" + id);
			}
		}
		return id;
	}
	
	public static interface IdGenerator {
		public String getId();
	}
}
