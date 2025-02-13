package com.dipak.atmsystem;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Path("/users")
public class loginService implements Serializable {
	private String token;
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	@POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
		try {
			DbConnection dbcon = new DbConnection();
//			ResultSet resultSet = dbcon.getResult("select * from accounts where cardno ="+credentials.getCardNo()+" and pin = '"+credentials.getPin()+"'");
			ResultSet resultSet = (ResultSet) dbcon.getResult("Select * from accounts");			
			if(!resultSet.isBeforeFirst())
			{
				return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Invalid credentials\"}").build();
			}
			else
			{
				int accountId = 0;
				int userId =0;
				while(resultSet.next())
				{
					accountId = resultSet.getInt("accountid");
					userId = resultSet.getInt("userid");
				}
				
				Map<String,Object> content = new HashMap<>();
	            content.put("account", accountId);
	            
	        	TokenGenerator tokenGenerator = new TokenGenerator();
	        	tokenGenerator.setData(content);
//	        	tokenGenerator.setTechnicalData(String.valueOf(userId),"atmURL","issuerURL");
	        	tokenGenerator.setTechnicalData("withdraw",String.valueOf(userId),"issuerURL");
	        	setToken(tokenGenerator.getToken());
	        	
	        	
	        	loginResponse resp =new loginResponse(userId, accountId, getToken());
	            return Response.ok(resp).build();
			}
		}
		catch(Exception e)
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Invalid credentials\"}").build();
		}
		
//        if ("123".equals(credentials.getCardNo()) && "pass".equals(credentials.getPin())) {
//        	Map<String,Object> content = new HashMap<>();
//            content.put("userId", credentials.getCardNo());
//            content.put("sessionId","1");
//        	
//        	TokenGenerator tokenGenerator = new TokenGenerator();
//        	tokenGenerator.setData(content);
//        	tokenGenerator.setTechnicalData("Withdrawl",credentials.getCardNo(),"dipak");
//        	setToken(tokenGenerator.getToken());
//        	
//        	
////        	loginResponse resp =new loginResponse(credentials.getCardNo(),getToken());
//            return Response.ok(resp).build();
//        } else {
//            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Invalid credentials\"}").build();
//        }
    }
	
	@POST
	@Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response BalanceRequest(PayLoad pl) {
		String jwtTokenInHeader = request.getHeader("x-jwt-token");
		
		VerifyJwt verifier = new VerifyJwt(jwtTokenInHeader);
		
		boolean isVerified = verifier.validate(String.valueOf(pl.getUserid()));
		int bal = 0;
		if(isVerified)
		{
			FetchBalance fb = new FetchBalance();
			try {
				bal = fb.getBalance(pl.getAccountno());
				return Response.ok(bal).build();
			} catch (SQLException e) {

				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Internal Server Error\"}").build();
			}
		}
		else
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Invalid Token\"}").build();
		}
		
				
		
	}
	
	
	@POST
	@Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response withdrawRequest(WithdrawPayLoad pl) {
		String jwtTokenInHeader = request.getHeader("x-jwt-token");
		
		VerifyJwt verifier = new VerifyJwt(jwtTokenInHeader);
		
		boolean isVerified = verifier.validate(String.valueOf(pl.getUserid()));
		int bal = 0;
		if(isVerified)
		{
			Withdraw w = new Withdraw();
			Resp resp = w.Transaction(pl.getAccountno(),pl.getAmount());
			
			if(resp.isStatus())
			{
				return Response.ok(resp.getBal()).build();
			}
			else
			{
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Internal Server Error\"}").build();
			}
			
		}
		else
		{
			return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Invalid Token\"}").build();
		}
		
				
//		return Response.ok(bal).build();
	}
	
	

	
	
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static class Credentials {
        private String cardNo;
        private String pin;

        public String getCardNo() { return cardNo; }
        public void setUsername(String cardNo) { this.cardNo = cardNo; }

        public String getPin() { return pin; }
        public void setPassword(String pin) { this.pin = pin; }
    }
	
	
	public static class PayLoad
	{
		private int userid;
		private int accountno;
		public int getUserid() {
			return userid;
		}
		public void setUserid(int userid) {
			this.userid = userid;
		}
		public int getAccountno() {
			return accountno;
		}
		public void setAccountno(int accountno) {
			this.accountno = accountno;
		}
	}
	
	public static class WithdrawPayLoad
	{
		private int userid;
		private int accountno;
		private double amount;
		
		public int getUserid() {
			return userid;
		}
		public void setUserid(int userid) {
			this.userid = userid;
		}
		public int getAccountno() {
			return accountno;
		}
		public void setAccountno(int accountno) {
			this.accountno = accountno;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
	}
}
