package com.dipak.atmsystem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/user")
public class Hello {

	@GET
	public String hi()
	{
		return "Hello Dipak:";
	}
}
