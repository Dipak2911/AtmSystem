package com.dipak.atmsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FetchBalance {
	
	DbConnection dbConnection = new DbConnection();
	
	public int getBalance(int accountId) throws SQLException
	{
		int bal = 0;
		ResultSet rs = dbConnection.getResult("select balance from accounts where accountid = '"+accountId+"'");
		if(rs.next())
		{
			bal = rs.getInt("balance");
		}
		return bal;
	}
	

}
