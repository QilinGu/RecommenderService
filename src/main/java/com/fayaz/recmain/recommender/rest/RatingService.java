package com.fayaz.recmain.recommender.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fayaz.recmain.recommender.rest.handlers.RatingHandler;
import com.fayaz.recmain.recommender.rest.pojo.BaseResponse;
import com.fayaz.recmain.recommender.rest.pojo.PostRating;
import com.fayaz.recmain.recommender.rest.pojo.RatingItem;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationItem;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationRequest;
import com.fayaz.recmain.recommender.rest.pojo.RecommendationResponse;

@Path("rating")
public class RatingService {
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse addRating(PostRating rating){	
		RatingHandler handler = new RatingHandler();
		BaseResponse response = new BaseResponse();
		try{			
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
	
	@POST
	@Path("retrieve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RecommendationResponse getRecommendations(RecommendationRequest request){
		RatingHandler handler = new RatingHandler();
		RecommendationResponse response = new RecommendationResponse();
		try{		
			List<RecommendationItem>  recommendList= handler.getRecommendations(request.getUserId(),request.getCustomerId(),request.getCustomerSecret());
			response.setStatus("SUCCESS");
			response.setStatusMessage("Retrieved recommendations");
			response.setRecommendationList(recommendList);
		}catch(Exception e){
			response.setStatus("ERROR");
			response.setStatusMessage(e.getMessage());
		}
		return response;
	}

}
