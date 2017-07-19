package java_servlet_service.exception;

import java_servlet_service.exception.base.BaseException;

import java.sql.SQLException;


public class DatabaseException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DatabaseException(SQLException e) {
		super(e, new Object[0]);
	}

	protected DatabaseException() {
		super(new Object[0]);
	}

	@Override
	public String getErrorCode() {
		return "DB0010";
	}

}
