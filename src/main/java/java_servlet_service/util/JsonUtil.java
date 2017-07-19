package java_servlet_service.util;

import java_servlet_service.exception.JsonConvertFailedException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;


@SuppressWarnings("all")
public class JsonUtil {
	
	public static String writeValue(Object object) throws JsonConvertFailedException {
		try {
			return JSON.toJSONString(object);
		} catch (JSONException  e) {
			throw new JsonConvertFailedException(e);
		}
	}
	
	public static Map<String,Object> readMap(String json) throws JsonConvertFailedException{
		try {
			return JSON.parseObject(json, Map.class);
		} catch (JSONException e) {
			throw new JsonConvertFailedException(e);
		}
	}
	
	public static <T> T readObj(String json,Class<? extends T> clazz) throws JsonConvertFailedException{
		try {
			return JSON.parseObject(json, clazz);
		} catch (JSONException e) {
			throw new JsonConvertFailedException(e);
		}
	}
	
	public static List<Map> readList(String json) throws Exception {
        try {
        	return JSON.parseArray(json, Map.class);
		} catch (JSONException e) {
			throw new JsonConvertFailedException(e);
		}
    }
}
