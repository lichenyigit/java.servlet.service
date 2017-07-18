package java.servlet.service.servlet.base;

import java.io.IOException;
import java.servlet.service.bean.Result;
import java.servlet.service.exception.DatabaseException;
import java.servlet.service.exception.JsonConvertFailedException;
import java.servlet.service.exception.RequestToMapException;
import java.servlet.service.exception.base.BaseException;
import java.servlet.service.util.CommonUtil;
import java.servlet.service.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



abstract public class BaseGetServlet<T> extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logger;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger = LogManager.getLogger(this.getClass());
		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			logger.trace(CommonUtil.resquestParameter2Map(req));
		} catch (RequestToMapException e1) {
			e1.printStackTrace();
		}
		req.setCharacterEncoding("UTF-8");
		Result<Object> result = new Result<>();
		try{
			result.setResult(processGet(req, resp));
		}catch(DatabaseException e){
			result = new Result<>(e);
			logger.error(e.getMessage(), e);
		}catch(BaseException e){
			result = new Result<>(e);
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
		}
		returnJson(result, resp);
	}
	
	public void returnJson(Object val, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String json = null;
        try {
            json = JsonUtil.writeValue(val);
            logger.trace("responseMessage:" + json);
        } catch (JsonConvertFailedException e) {
            logger.error("JSON转换失败(toJson error)", e);
            try {
                json = JsonUtil.writeValue(new Result<T>(e));
            } catch (JsonConvertFailedException e1) {
                logger.fatal("Convert empty Result 2 json failed!", e1);
                e1.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            logger.error("Get response writer failed!", e);
        }
    }
	
	protected T processGet(HttpServletRequest request, HttpServletResponse response) throws BaseException {
		return null;
	}
	
}
