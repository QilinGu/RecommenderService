package com.fayaz.recmain.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.fayaz.recmain.rest.handlers.RatingHandler;
import com.fayaz.recmain.rest.pojo.BaseResponse;
import com.fayaz.recmain.rest.pojo.PostRating;
import com.fayaz.recmain.rest.pojo.RatingItem;

@Path("rating")
public class RatingService {
	
	@POST
	@Path("add")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse addRating(String input){	
		System.out.println("Printing FCC"+input);
		RatingHandler handler = new RatingHandler();
		BaseResponse response = new BaseResponse();
		ObjectMapper mapper = new ObjectMapper();
		try{
			PostRating rating = mapper.readValue(input, PostRating.class);
			List<RatingItem> ratingsList = rating.getRatings();
			long customerId = rating.getCustomerId();
			String customerSecret = rating.getCustomerSecret();
			int count = handler.addRatings(ratingsList,customerId,customerSecret);
			response.setStatus("SUCCESS");
			response.setStatusMessage("Added "+count+" ratings");			
		}catch(Exception e){
			response.setStatus("ERROR");
			response.setStatusMessage(e.getMessage());
		}
		return response;
	}

}
