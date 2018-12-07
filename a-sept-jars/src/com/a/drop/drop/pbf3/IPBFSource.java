package com.sept.db.pdf;

import java.sql.Driver;

import javax.sql.DataSource;

public interface IPBFSource extends DataSource {
	void setDriver(Driver driver);

	void setUsername(String username);

	void setPassword(String password);

	void setUrl(String url);
}
