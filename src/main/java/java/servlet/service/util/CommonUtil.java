package java.servlet.service.util;

import java.servlet.service.exception.RequestToMapException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonUtil {

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtil.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtil.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	// 创建Map
	public static Map<String, Object> createMap(String key, Object value) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (value == null) {
			value = "";
		}
		map.put(key, CommonUtil.setString(value.toString()));
		return map;
	}

	public static Map<String, Object> generateMap(Map<String, Object> map, String key, Object value) {
		if (value == null) {
			value = "";
		}
		map.put(key, value);
		return map;
	}

	public static String setString(String str) {
		return setString(str, "");
	}

	public static String setString(String str, String defaultStr) {
		if (StringUtil.isNotBlank(str)) {
			return str;
		} else {
			return defaultStr;
		}
	}
	
	public static Map<String, Object> resquestParameter2Map(HttpServletRequest request) throws RequestToMapException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String[]> url = request.getParameterMap();
		for (Entry<String, String[]> entry : url.entrySet()) {
			String key = entry.getKey();
			String[] valueArray = entry.getValue();
			String value;
			value = new String(valueArray[0]);
			map.put(key, value);
		}
		return map;
	}
	
	public static List<Map<String, Object>> resultSetToMap(ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		List<String> colums = new ArrayList<>();
		for(int i = 1;i <= columns;i++){
			String column = rsmd.getColumnName(i);
			colums.add(column);
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (String column : colums) {
				String value = rs.getString(column);
				CommonUtil.generateMap(map, column, value);
			}
			result.add(map);
		}
		return result;
	}
	
	public static void responseCORS(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("application/json");
	}

}
