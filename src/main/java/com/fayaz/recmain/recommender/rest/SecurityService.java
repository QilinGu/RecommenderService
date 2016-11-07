package com.fayaz.recmain.recommender.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fayaz.recmain.recommender.rest.handlers.SecurityHandler;
import com.fayaz.recmain.recommender.rest.pojo.Login;
import com.fayaz.recmain.recommender.rest.pojo.LoginResponse;
import com.fayaz.recmain.recommender.rest.pojo.Signup;

@Path("/security")
public class SecurityService {

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginResponse doLogin(Login login) {
		SecurityHandler handler = new SecurityHandler();
		LoginResponse response = new LoginResponse();
		try{
			String token = handler.doLogin(login);
			response.setStatus("SUCCESS");
			response.setStatusMessage(token);
		}catch(Exception e){
			response.setStatus("ERROR");
			response.setStatusMessage(e.getMessage());
		}		
		return response;
	}

	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginResponse doSignup(Signup signup) {
		SecurityHandler handler = new SecurityHandler();
		LoginResponse response = new LoginResponse();
		try {
			handler.doSignup(signup);
			response.setStatus("SUCCESS");
			response.setStatusMessage(signup.getUsername()
					+ " Account Created Successfully!!!");
		} catch (Exception e) {
			response.setStatus("ERROR");
			response.setStatusMessage(e.getMessage());
		}
		return response;
	}

}
