package java.servlet.service.exception;

import java.sql.SQLException;

import com.shihou.summer.activities.exception.base.BaseException;

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
