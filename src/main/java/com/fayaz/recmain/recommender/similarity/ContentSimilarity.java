package com.fayaz.recmain.recommender.similarity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.similarity.AbstractItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;

import com.fayaz.recmain.recommender.index.DescriptionIndexer;
import com.fayaz.recmain.recommender.util.StringRepo;

public class ContentSimilarity extends AbstractItemSimilarity {
	
	private double[][] similarities = null;
	private HashMap<Long,Integer> productToDocMap;
	private HashMap<Integer,DocumentVector> docIdToVectorMap;
	
	public ContentSimilarity(long customerId,DataModel dataModel)throws RuntimeException {
		super(dataModel);
		productToDocMap = new HashMap<Long,Integer>();
		DescriptionIndexer luceneHandler = new DescriptionIndexer();
		Query getDocsByCustomer = NumericRangeQuery.newLongRange(StringRepo.LUCENE_CUSTOMER_ID,
				customerId, customerId, true, true);
		HashMap<String,Integer> allTermsMap = luceneHandler.getAllTermsMap(getDocsByCustomer);
		docIdToVectorMap = luceneHandler.getAllDocumentVectors(getDocsByCustomer,allTermsMap,productToDocMap);
		Collection<DocumentVector> documentVectorSet = docIdToVectorMap.values();
		Iterator<DocumentVector> iter1 = documentVectorSet.iterator();
		Iterator<DocumentVector> iter2 ;
		while(iter1.hasNext()){
			DocumentVector d1 = iter1.next();
			iter2 = documentVectorSet.iterator();
			while(iter2.hasNext()){
				DocumentVector d2 = iter2.next();
				double similarity = cosineSimilarity(d1,d2);
				d1.addSimilarity(d2.getId(), similarity);
			}			
		}
	}
	
	private double cosineSimilarity(DocumentVector d1,DocumentVector d2) throws RuntimeException{
        double cosinesimilarity;
        try {
            cosinesimilarity = (d1.getVector().dotProduct(d2.getVector()))
                    / (d1.getVector().getNorm() * d2.getVector().getNorm());
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException("Error while calculating cosine similarity");
        }
        return cosinesimilarity;
    }

	@Override
	public double[] itemSimilarities(long i, long[] jList)
			throws TasteException {
		double[] similarityForI = new double[jList.length];		
		int d1 = productToDocMap.get(i);
		for(int j=0;j<jList.length;j++){			
			similarityForI[j] = docIdToVectorMap.get(d1).getSimilarity(jList[j]);		
		}
		return similarityForI;
	}

	@Override
	public double itemSimilarity(long item1, long item2) throws TasteException {
		int d1 = productToDocMap.get(item1);		
		return docIdToVectorMap.get(d1).getSimilarity(item2);
	}
}
