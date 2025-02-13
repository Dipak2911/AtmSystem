package com.dipak.atmsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Withdraw {

	public static final String url = "jdbc:postgresql://localhost:5432/postgres";
	public static final String username = "postgres";
	public static final String password = "root";
	private Lock lock = new ReentrantLock();

	
	Resp Transaction(int accountId, double amount)
	{
		Resp resp;
		
		Connection conn = null;
        PreparedStatement withdrawStmt = null;
        PreparedStatement balanceStmt = null;
        ResultSet rs = null;
		
		 try {
			 	Class.forName("org.postgresql.Driver");
	            conn = DriverManager.getConnection(url, username, password);
	            conn.setAutoCommit(false);
	            String withdrawSQL = "update accounts set balance = balance - ? where accountid = ? AND balance >= ?";
	            withdrawStmt = conn.prepareStatement(withdrawSQL);
	            withdrawStmt.setDouble(1, amount);
	            withdrawStmt.setInt(2, accountId);
	            withdrawStmt.setDouble(3, amount);
	            
	            lock.lock();
	            
	            int rowsAffected = withdrawStmt.executeUpdate();

	            if (rowsAffected == 0) {
	                conn.rollback();
	                System.out.println("Insufficient balance");
	                return new Resp(false,0);
	            }
	            
	            String balanceSQL = "select balance from accounts where accountid = ?";
	            
                balanceStmt = conn.prepareStatement(balanceSQL);
                balanceStmt.setInt(1, accountId);
                rs = balanceStmt.executeQuery();
                
                int remainingBalance = 0;
                if (rs.next()) {
                    remainingBalance = rs.getInt("balance");
                }

	            conn.commit();
	            
	            
	            
	            System.out.println("Withdrawal successful");
	            return new Resp(true,remainingBalance);
	        } catch (SQLException e) {
	            try {
	            	
	                if (conn != null) {
	                    conn.rollback();
	                    System.out.println("Transaction rolledback");
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	            e.printStackTrace();
	            return new Resp(false, 0);
	        } catch (ClassNotFoundException e) {
	        	return new Resp(false, 0);
			} finally {
	            try {
	            	lock.unlock();
	                if (conn != null) {
	                	conn.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	}
	
}
