package java.servlet.service.service;

import java.servlet.service.bean.Page;
import java.servlet.service.exception.DatabaseException;
import java.servlet.service.exception.UserExistException;
import java.servlet.service.exception.UserFabulousedException;
import java.servlet.service.util.CommonUtil;
import java.servlet.service.util.DBUtil;
import java.servlet.service.util.DateUtil;
import java.servlet.service.util.StringUtil;
import java.servlet.service.util.dictionnary.AuditStatusEnum;
import java.servlet.service.util.dictionnary.EnableEnum;
import java.servlet.service.util.dictionnary.OrderByTypeEnum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * 评论服务
 * @author lichenyi
 *
 * 2017年7月17日 下午2:40:22
 */
public class ForumInfoService {
	private static Logger logger = LogManager.getLogger(ForumInfoService.class);
	
	private static List<Map<String, Object>> _queryList(String uid, String content, AuditStatusEnum auditStatus, EnableEnum enableStatus, OrderByTypeEnum roderType, Long pageSize, Long pageNum, Connection conn) throws SQLException{
			StringBuffer sql = new StringBuffer("SELECT * FROM forum_info WHERE 1 = 1 ");
			if(StringUtil.isNotBlank(uid)){
				sql.append("AND uid = ? ");
			}
			if(StringUtil.isNotBlank(content)){
				sql.append("AND content like ? ");
			}
			if(auditStatus != null){
				sql.append("AND audit_status = ? ");
			}
			if(enableStatus != null){
				sql.append("AND enable = ? ");
			}
			if(roderType != null){
				sql.append("ORDER BY ? DESC ");
			}else{
				sql.append("ORDER BY create_time DESC ");
			}
			sql.append("LIMIT ? OFFSET ?");
			
			PreparedStatement pstmtQuery = conn.prepareStatement(sql.toString());
			int index = 1;
			if(StringUtil.isNotBlank(uid)){
				pstmtQuery.setString(index++, uid);
			}
			if(StringUtil.isNotBlank(content)){
				pstmtQuery.setString(index++, "%"+content+"%");
			}
			if(auditStatus != null){
				pstmtQuery.setInt(index++, auditStatus.getCode());
			}
			if(enableStatus != null){
				pstmtQuery.setInt(index++, enableStatus.getCode());
			}
			if(roderType != null){
				pstmtQuery.setString(index++, roderType.getDescription());
			}
			pstmtQuery.setLong(index++, pageSize);
			pstmtQuery.setLong(index++, pageSize*pageNum);
			
			ResultSet rs = pstmtQuery.executeQuery();
			return CommonUtil.resultSetToMap(rs);
	}
	private static Long _queryListCount(String uid, String content, AuditStatusEnum auditStatus, EnableEnum enableStatus, Connection conn) throws SQLException{
			StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM forum_info WHERE 1 = 1 ");
			if(StringUtil.isNotBlank(uid)){
				sql.append("AND uid = ? ");
			}
			if(StringUtil.isNotBlank(content)){
				sql.append("AND content like ? ");
			}
			if(auditStatus != null){
				sql.append("AND audit_status = ? ");
			}
			if(enableStatus != null){
				sql.append("AND enable = ? ");
			}
			
			PreparedStatement pstmtQuery = conn.prepareStatement(sql.toString());
			int index = 1;
			if(StringUtil.isNotBlank(uid)){
				pstmtQuery.setString(index++, uid);
			}
			if(StringUtil.isNotBlank(content)){
				pstmtQuery.setString(index++, "%"+content+"%");
			}
			if(auditStatus != null){
				pstmtQuery.setInt(index++, auditStatus.getCode());
			}
			if(enableStatus != null){
				pstmtQuery.setInt(index++, enableStatus.getCode());
			}
			
			ResultSet rs = pstmtQuery.executeQuery();
			if(rs.next())
				return rs.getLong(1);
			return 0l;
	}
	/**
	 * 查询列表
	 * @param uid
	 * @param content
	 * @param auditStatus
	 * @param enableStatus
	 * @param roderType
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws DatabaseException
	 * 2017年7月17日 下午4:19:52 by lichenyi
	 */
	public static Page<Map<String, Object>> queryList(String uid, String content, AuditStatusEnum auditStatus, EnableEnum enableStatus, OrderByTypeEnum roderType, Long pageSize, Long pageNum) throws DatabaseException {
		try(Connection conn = DBUtil.getConnection()){
			Page<Map<String, Object>> page = new Page<Map<String, Object>>();
			page.setPage(pageNum);
			page.setSize(pageSize);
			page.setTotalPage(_queryListCount(uid, content, auditStatus, enableStatus, conn));
			page.setList(_queryList(uid, content, auditStatus, enableStatus, roderType, pageSize, pageNum, conn));
			return page;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	private static boolean _add(String uid, String content, Connection conn) throws SQLException, UserExistException {
		//每人只允许评论一次
		PreparedStatement pstmtQuery = conn.prepareStatement("SELECT COUNT(*) FROM forum_info WHERE uid = ?");
		pstmtQuery.setString(1, uid);
		ResultSet rs = pstmtQuery.executeQuery();
		long re = 0l;
		if(rs.next())
			re = rs.getLong(1);
		if(re > 0){
			logger.info(String.format("uid为【%s】用户已经发表过言论", uid));
			throw new UserExistException();
		}
		
		PreparedStatement pstmtInsert = conn.prepareStatement("INSERT INTO forum_info(uid, content, fabulous_count, audit_status, enable, create_time, update_time) VALUES(?, ?, ?, ?, ?, ?, ?)");
		int index = 1;
		pstmtInsert.setString(index++, uid);
		pstmtInsert.setString(index++, content);
		pstmtInsert.setInt(index++, 0);
		pstmtInsert.setInt(index++, AuditStatusEnum.audting.getCode());
		pstmtInsert.setInt(index++, EnableEnum.enable.getCode());
		pstmtInsert.setString(index++, DateUtil.dateFormart4yyyymmdd());
		pstmtInsert.setLong(index++, System.currentTimeMillis());
		return pstmtInsert.executeUpdate() > 0;
	}
	
	/**
	 * 添加评论
	 * @param uid
	 * @param content
	 * @return
	 * @throws DatabaseException
	 * @throws UserExistException
	 * 2017年7月17日 下午3:27:46 by lichenyi
	 */
	public static boolean add(String uid, String content) throws DatabaseException, UserExistException{
		try(Connection conn = DBUtil.getConnection()){
			 boolean result = _add(uid, content, conn);
			 conn.commit();
			 return result;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	
	private static boolean _update(String id, String fabulousCount, AuditStatusEnum auditStatus, EnableEnum enableStatus, Connection conn) throws SQLException{
		StringBuffer sql = new StringBuffer("UPDATE forum_info SET ");
		if(StringUtil.isNotBlank(fabulousCount)){
			sql.append("fabulous_count = fabulous_count + ?, ");
		}
		if(auditStatus != null){
			sql.append("audit_status = ?, ");
		}
		if(enableStatus != null){
			sql.append("enable = ?, ");
		}
		sql.append("update_time = ? WHERE id = ? ");
		PreparedStatement pstmtUpdate = conn.prepareStatement(sql.toString());
		int index = 1;
		if(StringUtil.isNotBlank(fabulousCount)){
			pstmtUpdate.setString(index++, fabulousCount);
		}
		if(auditStatus != null){
			pstmtUpdate.setInt(index++, auditStatus.getCode());
		}
		if(enableStatus != null){
			pstmtUpdate.setInt(index++, enableStatus.getCode());
		}
		pstmtUpdate.setLong(index++, System.currentTimeMillis());
		pstmtUpdate.setString(index++, id);
		return pstmtUpdate.executeUpdate() > 0;
	}
	/**
	 * 根据主键id更新评论
	 * @param id
	 * @param fabulousCount
	 * @param auditStatus
	 * @param enableStatus
	 * @return
	 * @throws DatabaseException
	 * 2017年7月17日 下午3:41:01 by lichenyi
	 */
	public static boolean update(String id, String fabulousCount, AuditStatusEnum auditStatus, EnableEnum enableStatus) throws DatabaseException{
		try(Connection conn = DBUtil.getConnection()){
			boolean result = _update(id, fabulousCount, auditStatus, enableStatus, conn);
			conn.commit();
			return result;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	private static boolean _updateFabulous(String id, String uid, String toUid, Connection conn) throws SQLException, UserFabulousedException {
		//判断该用户今天是否点赞过
		PreparedStatement pstmtQuery = conn.prepareStatement("SELECT COUNT(*) FROM fabulous_info WHERE uid = ? AND create_date = ? ");
		int index = 1;
		pstmtQuery.setString(index++, uid);
		pstmtQuery.setString(index++, DateUtil.dateFormart4yyyymmdd());
		ResultSet rs = pstmtQuery.executeQuery();
		long re = 0l;
		if(rs.next())
			re = rs.getLong(1);
		if(re > 0){
			logger.info(String.format("uid为【%s】用户已经点过赞了", uid));
			throw new UserFabulousedException();
		}

		//更新forum_info
		StringBuffer sql = new StringBuffer("UPDATE forum_info SET fabulous_count = fabulous_count + 1, update_time = ? WHERE id = ? ");
		PreparedStatement pstmtUpdate = conn.prepareStatement(sql.toString());
		index = 1;
		pstmtUpdate.setLong(index++, System.currentTimeMillis());
		pstmtUpdate.setString(index++, id);
		boolean result = pstmtUpdate.executeUpdate() > 0;

		//更新fabulous_info表
		PreparedStatement pstmtInsert = conn.prepareStatement("INSERT INTO fabulous_info(uid, create_date, to_uid) VALUES(?, ?, ?)");
		index = 1;
		pstmtInsert.setString(index++, uid);
		pstmtInsert.setString(index++, DateUtil.dateFormart4yyyymmdd());
		pstmtInsert.setString(index++, toUid);
		result = result && pstmtInsert.executeUpdate() > 0;
		return result;
	}
	/**
	 * 用户点赞行为
	 * @param 
	 * @return 
	 * @author lichenyi
	 * @date 2017-7-18 13:52
	 */
	public static boolean updateFabulous(String id, String uid, String toUid) throws DatabaseException, UserFabulousedException {
		try(Connection conn = DBUtil.getConnection()){
			boolean result = _updateFabulous(id, uid, toUid, conn);
			conn.commit();
			return result;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
