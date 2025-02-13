package com.dipak.atmsystem;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {
	
	public static final String url = "jdbc:postgresql://localhost:5432/postgres";
	public static final String username = "postgres";
	public static final String password = "root";



	public ResultSet getResult(String query)
     {
//    	 ResultSet result = null;
         try {
        	 Class.forName("org.postgresql.Driver");
             
             Connection con = DriverManager.getConnection(url,username,password); 
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(query);
//             while(rs.next())
//             {
//            	 String columnValue = rs.getString("accountId");
//            	    System.out.println("Column Value: " + columnValue);
//             }
             return rs;
         }
         catch(Exception e)
         {
        	 return null;
         }
//         return result;   
     }
    
   


}
