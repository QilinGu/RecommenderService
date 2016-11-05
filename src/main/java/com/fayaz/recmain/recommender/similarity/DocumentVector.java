package com.fayaz.recmain.recommender.similarity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math.linear.OpenMapRealVector;
import org.apache.commons.math.linear.RealVectorFormat;

public class DocumentVector {

	private Map<String,Integer> terms;
	private OpenMapRealVector vector;
	private HashMap<Long,Double> similarities;
	
	private int docId;
	private long id;
	

	public DocumentVector(Map<String,Integer> terms,int docId,long id) {
		this.terms = terms;
		this.vector = new OpenMapRealVector(terms.size());
		this.similarities = new HashMap<Long,Double>();
		this.id = id;
		this.docId = docId;
	}

	public int getDocId() {
		return docId;
	}

	public long getId() {
		return id;
	}	

	public void setEntry(String term, int freq) {
		if (terms.containsKey(term)) {
			int pos = terms.get(term);
			vector.setEntry(pos, (double) freq);
		}
	}

	public void normalize() {
		double sum = vector.getL1Norm();
		vector = (OpenMapRealVector) vector.mapDivide(sum);
	}

	@Override
	public String toString() {
		RealVectorFormat formatter = new RealVectorFormat();
		return formatter.format(vector);
	}
	
	public void addSimilarity(long productId,double similarity){
		similarities.put(productId, similarity);
	}
	
	public double getSimilarity(long id){
		return similarities.get(id);
	}

	public OpenMapRealVector getVector() {
		return vector;
	}

	public void setVector(OpenMapRealVector vector) {
		this.vector = vector;
	}
}
