package com.dipak.atmsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountService {
	


	@POST
	@Path("/checkBalance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response returnBalance()
	{
		String rr=null;
		
//		try {
//		DbConnection dbcon = new DbConnection(url,username,password);
//		dbcon.getResult("select * from accounts where cardno ="+)
//
//		ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts");
//
//		while (resultSet.next())
//		{
//		    String columnValue = resultSet.getString("accountid");
//		    rr=columnValue;
//		    System.out.println("Column Value: " + columnValue);
//		}
//		}
//		catch(Exception e)
//		{
//			rr=e.getMessage();
//		}
		
		return Response.ok(rr).build();
		
		
	}
}
