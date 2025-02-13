package com.dipak.atmsystem;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class loginResponse {
    private int user;
    private int account;
    private String jwt;

    public loginResponse() {}

    public loginResponse(int user, int account ,String jwt) {
        this.user = user;
        this.jwt = jwt;
        this.account= account;
    }

    
    
    public int getUser() { return user; }
    public void setUser(int user) { this.user = user; }

    public String getJwt() { return jwt; }
    public void setJwt(String jwt) { this.jwt = jwt; }

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}
}
