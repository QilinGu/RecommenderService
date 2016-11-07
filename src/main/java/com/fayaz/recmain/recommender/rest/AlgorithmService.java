package com.fayaz.recmain.recommender.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fayaz.recmain.recommender.hibernate.dao.AlgorithmDAO;
import com.fayaz.recmain.recommender.hibernate.pojo.Algorithm;
import com.fayaz.recmain.recommender.rest.pojo.AlgorithmItem;
import com.fayaz.recmain.recommender.rest.pojo.AlgorithmResponse;

@Path("/algo")
public class AlgorithmService {
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public AlgorithmResponse getAllAlgorithms() {
		AlgorithmDAO dao = new AlgorithmDAO();
		AlgorithmResponse response = new AlgorithmResponse();
		try{
			List<Algorithm> algoList = dao.getAllAlgorithms();
			List<AlgorithmItem> responseList = new ArrayList<AlgorithmItem>();
			AlgorithmItem item;
			int i=0;
			for(Algorithm algo:algoList){
				item = new AlgorithmItem();
				item.setAlgoId(algo.getAlgoId());
				item.setAlgoName(algo.getAlgoName());
				item.setAlgoDesc(algo.getDescription());
				responseList.add(item);
				i++;
			}
			response.setStatus("SUCCESS");
			response.setItems(responseList);
			response.setStatusMessage("Total Count :"+i);
		}catch(Exception e){
			response.setStatus("ERROR");
			response.setStatusMessage(e.getMessage());
		}		
		return response;
	}

}
