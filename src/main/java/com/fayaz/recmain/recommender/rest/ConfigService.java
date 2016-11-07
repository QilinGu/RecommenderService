package com.fayaz.recmain.recommender.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fayaz.recmain.recommender.rest.handlers.ConfigHandler;
import com.fayaz.recmain.recommender.rest.pojo.Config;
import com.fayaz.recmain.recommender.rest.pojo.ConfigResponse;

@Path("/config")
public class ConfigService {
	@GET
	@Path("{clientSecret}")
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigResponse getConfig(@PathParam("clientSecret") String secret) {
		ConfigHandler handler = new ConfigHandler();
		ConfigResponse response = new ConfigResponse();
		try{
			Config configResponseItem = handler.getConfig(secret);
			response.setStatus("SUCCESS");
			response.setStatusMessage("Requried Data found");
			response.setConfig(configResponseItem);
		}catch(Exception e){
			response.setStatus("ERROR");
			response.setStatusMessage(e.getMessage());
		}		
		return response;
	}
	
	@POST
	@Path("{clientSecret}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigResponse updateConfig(@PathParam("clientSecret") String secret,Config config) {
		ConfigHandler handler = new ConfigHandler();
		ConfigResponse response = new ConfigResponse();
		try{
			String msg = handler.updateConfig(secret,config);	
			response.setStatus("SUCCESS");
			response.setStatusMessage(msg);
		}catch(Exception e){
			response.setStatus("ERROR");
			response.setStatusMessage(e.getMessage());
		}		
		return response;
	}
	

}
