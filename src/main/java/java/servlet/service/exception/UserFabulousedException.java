package java.servlet.service.exception;

import com.shihou.summer.activities.exception.base.BaseException;

public class UserFabulousedException extends BaseException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getErrorCode() {
		return "0102";
	}

}
