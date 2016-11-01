package recmain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.fayaz.recmain.rest.pojo.BaseResponse;
import com.fayaz.recmain.rest.pojo.PostRating;
import com.fayaz.recmain.rest.pojo.RatingItem;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;

public class RatingsServiceTest extends JerseyTest{
	private static final String DEFAULT_TYPE = "String";
	private static final String SUCCESS_STATUS = "SUCCESS";
	private static final String ERROR_STATUS = "ERROR";
	private static final String CREATE_URI = "rating/add";
	
	@Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder("com.fayaz.recmain.rest")
        	.servletClass(ServletContainer.class)
            .initParam("com.sun.jersey.api.json.POJOMappingFeature", "true")
            .initParam("com.sun.jersey.config.property.packages", "com.fayaz.recmain.rest")
            .build();            
    } 
 
    @Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }
    
    
    public void a_testaddRating(){
//    	//System.out.println("Executing a_testCreateMap()");
//    	RatingItem item = new RatingItem(12001,12001,3);
//    	ArrayList<RatingItem> ratingsList = new ArrayList<RatingItem>();
//    	ratingsList.add(item);
//    	RatingsWrapper wrapper = new RatingsWrapper(ratingsList);
//    	PostRating postRatingRequest = new PostRating(2,"ecommerce",wrapper);
//    	BaseResponse response =resource().path(CREATE_URI).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(BaseResponse.class,postRatingRequest);
//    	System.out.println(response.getStatus());
//    	System.out.println(response.getStatusMessage());
//    	
//    	assertEquals(response.getStatus(),SUCCESS_STATUS);
//    	//assertEquals(response.getStatusMessage(),CREATE_MAP_SUCCESS);
    }

}
