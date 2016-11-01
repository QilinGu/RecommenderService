package com.fayaz.hibernate.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="RATINGS")
public class Rating implements Serializable {	

	private static final long serialVersionUID = -8465004589570294305L;
	
	@EmbeddedId
	private RatingsPK ratingsPk;
	
	@Column(name="rating")
	private long rating;

	public Rating(long userId, long productId, long customerId, long rating2) {
		this.ratingsPk = new RatingsPK(userId, productId, customerId);
		this.rating = rating2;
	}

	public RatingsPK getRatingsPk() {
		return ratingsPk;
	}

	public void setRatingsPk(RatingsPK ratingsPk) {
		this.ratingsPk = ratingsPk;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

}

@Embeddable
class RatingsPK implements Serializable{	
	
	private static final long serialVersionUID = -1593237881525992695L;
	
	@Column(name="customer_id")
	private long customerId;
	@Column(name="user_id")
	private long userId;
	@Column(name="product_id")
	private long productId;
	
	public RatingsPK(){}
	
	public RatingsPK(long userId,long productId,long customerId){
		this.userId = userId;
		this.productId = productId;
		this.customerId = customerId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
}