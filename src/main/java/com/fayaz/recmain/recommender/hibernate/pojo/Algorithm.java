package com.fayaz.recmain.recommender.hibernate.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="algorithms", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"algo_id"})})
public class Algorithm {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "algo_seq")
	@SequenceGenerator(name = "algo_seq", sequenceName = "rec_admin_sequence")
	@Column(name="algo_id", nullable=false, unique=true, length=18)
	private long algoId;
	
	@Column(name="algo_name", length=360, nullable=false,unique=true)
	private String algoName;	
	
	@Column(name="description", length=360, nullable=false)
	private String description;

	public long getAlgoId() {
		return algoId;
	}

	public void setAlgoId(long algoId) {
		this.algoId = algoId;
	}

	public String getAlgoName() {
		return algoName;
	}

	public void setAlgoName(String algoName) {
		this.algoName = algoName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
