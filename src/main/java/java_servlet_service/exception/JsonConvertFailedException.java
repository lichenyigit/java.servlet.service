package java_servlet_service.exception;


import java_servlet_service.exception.base.BaseException;

public class JsonConvertFailedException extends BaseException {
	private static final long serialVersionUID = -7257315519804301492L;
	
	public JsonConvertFailedException(Throwable t) {
		super(t);
	}

	@Override
	public String getErrorCode() {
		return "0101";
	}

}
