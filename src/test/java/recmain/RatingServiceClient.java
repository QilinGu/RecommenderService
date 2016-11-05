package recmain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.fayaz.recmain.recommender.AlgorithmType;
import com.fayaz.recmain.recommender.rest.pojo.BaseResponse;
import com.fayaz.recmain.recommender.rest.pojo.PostRating;
import com.fayaz.recmain.recommender.rest.pojo.RatingItem;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RatingServiceClient {
	
	private static final String BASE_URI = "http://localhost:8070/RecMain/rating/add";
	private static final String DELIMITTER  = "\\t";
	private static final String RATINGS_DATA = "ml-100k/u.data";
	private static final long CUSTOMER_ID = 1;
	private static final String CUSTOMER_SECRET = "grouplens";
	
	private WebResource webResource;

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		RatingServiceClient client = new RatingServiceClient();
		//client.prepareWebResource();
		//client.parseRatingsFile();
		client.enumCheck();
	}
	
	private void prepareWebResource() {
		ClientConfig clientConfig = new DefaultClientConfig();              
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
		Client client = Client.create(clientConfig);
		webResource = client.resource(BASE_URI);
		
	}
	
	private void postRating(List<RatingItem> ratingsList){
		
		PostRating mainJson = new PostRating(CUSTOMER_ID,CUSTOMER_SECRET,ratingsList);
		BaseResponse response = webResource.accept("application/json")
				                .type("application/json").post(BaseResponse.class, mainJson);
		System.out.println(response.getStatus());
		System.out.println(response.getStatusMessage());
	}
	
	private void parseRatingsFile(){
		BufferedReader br = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(RATINGS_DATA).getFile());
			br = new BufferedReader(new FileReader(file));
			ArrayList<RatingItem> ratingsList = new ArrayList<RatingItem>();
			String input;			
			while((input=br.readLine())!=null){
				String[] tokens = input.split(DELIMITTER);
				long userId = Long.parseLong(tokens[0]);
				long productId = Long.parseLong(tokens[1]);
				long rating = Long.parseLong(tokens[2]);
				ratingsList.add(new RatingItem(userId,productId,rating));
			}
			postRating(ratingsList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static String getHashFromPassword(String saltPass) throws RuntimeException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		md.update(saltPass.getBytes());
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}
	
	private void enumCheck(){
		AlgorithmType type;
		type = AlgorithmType.getAlgorithmTypeInstance("Item-Item Collaborative Filtering");
		//type = AlgorithmType.valueOf("Item-Item Collaborative Filtering");
	}

}
