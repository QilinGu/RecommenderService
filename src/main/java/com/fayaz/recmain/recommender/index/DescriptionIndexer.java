package com.fayaz.recmain.recommender.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

import com.fayaz.recmain.recommender.similarity.DocumentVector;
import com.fayaz.recmain.recommender.util.StringRepo;

public class DescriptionIndexer implements Runnable {

	private static IndexWriter writer = null;
	private static IndexReader reader;
	private static IndexSearcher searcher;
	
	private static Directory indexDirectory;

	private static boolean prepared = false;
	private static boolean needsRefreshing = false;
	private static BlockingQueue<Document> writerQueue;
	
	private static final int REFRESH_LIMIT = 1000;
	
	static {
		File indexDirectoryFile = new File(StringRepo.INDEX_DIRECTORY);
		try {
			indexDirectory = FSDirectory.open(indexDirectoryFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void prepareReaderWriterAndSearcher() {
		if (prepared)
			return;
		prepared = true;		
		
		try {			
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
			writerQueue = new LinkedBlockingQueue<Document>();
			//initialize Writer
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(indexDirectory, iwc);
			Document doc  = new Document();
			doc.add(new StringField(StringRepo.LUCENE_TYPE,StringRepo.LUCENE_TYPE_DUMMY,Field.Store.NO));
			writer.addDocument(doc);
			writer.commit();
			Thread indexWriterThread = new Thread(new DescriptionIndexer());
			indexWriterThread.start();			
			//initialize reader
			DirectoryReader dirReader = DirectoryReader.open(indexDirectory);			
			reader = DirectoryReader.open(indexDirectory);
			//Initialize Searcher
			searcher = new IndexSearcher(dirReader);			
			System.out.println("Started indexing thread");
		} catch (IOException e) {
			e.printStackTrace();
			try {
				indexDirectory.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static void refreshReaderAndSearcher() throws RuntimeException{
		
		DirectoryReader dirReader;
		try {
			//Reinitialize reader
			dirReader = DirectoryReader.open(indexDirectory);
			synchronized(reader){
				reader = DirectoryReader.open(indexDirectory);
			}			
			//Reinitialize Searcher
			synchronized(searcher){
				searcher = new IndexSearcher(dirReader);
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
			throw new RuntimeException("Reader and Searcher Reinitialization failed");
		}			
		
		
	}

	private static void actualcloseReaderAndWriter() {
		try {
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("actual close done");
	}

	public static void closeWriter() {
		Document shutDownDoc = new Document();
		shutDownDoc.add(new StringField(StringRepo.LUCENE_TYPE,
				StringRepo.LUCENE_TYPE_SHUTDOWN, Field.Store.NO));
		try {
			if(writerQueue!=null)
				writerQueue.put(shutDownDoc);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static synchronized void indexDocument(List<Document> docList)
			throws RuntimeException {
		if (!prepared)
			throw new RuntimeException("Indexer not yet initialized");
		try {
			for (Document doc : docList) {
				writerQueue.put(doc);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to submit doc for indexing");
		}
	}
	
	private void triggerRefresh() throws IOException{
		writer.commit();
		refreshReaderAndSearcher();
		needsRefreshing=false;
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			try {
				if(writerQueue.isEmpty()&&needsRefreshing){
					triggerRefresh();
					count=0;					
				}
				Document doc = writerQueue.take();
				if (doc == null
						|| doc.get(StringRepo.LUCENE_TYPE).equals(
								StringRepo.LUCENE_TYPE_SHUTDOWN))
					break;
				BooleanQuery mainQuery = new BooleanQuery();
				long customerId = Long.parseLong(doc
						.get(StringRepo.LUCENE_CUSTOMER_ID));
				long id = Long.parseLong(doc.get(StringRepo.LUCENE_ID));
				Query q1 = NumericRangeQuery.newLongRange(
						StringRepo.LUCENE_CUSTOMER_ID, customerId, customerId,
						true, true);
				Query q2 = new TermQuery(new Term(StringRepo.LUCENE_TYPE,
						doc.get(StringRepo.LUCENE_TYPE)));
				Query q3 = NumericRangeQuery.newLongRange(StringRepo.LUCENE_ID,
						id, id, true, true);
				mainQuery.add(q1, Occur.MUST);
				mainQuery.add(q2, Occur.MUST);
				mainQuery.add(q3, Occur.MUST);
				writer.deleteDocuments(mainQuery);
				writer.addDocument(doc);
				needsRefreshing=true;
				if(count++==REFRESH_LIMIT){
					triggerRefresh();
					count=0;
				}
				System.out.println("Indexed :"+doc.get(StringRepo.LUCENE_ID));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Index thread run exception");
			}
		}
		actualcloseReaderAndWriter();
	}
	
	

	private TopDocs getQueryResults(Query q)
			throws RuntimeException {
		try {
			synchronized(searcher){
				return searcher.search(q, Integer.MAX_VALUE);
			}			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Error while exceuting Description Queries");
		}
	}

	private Terms getTermsVector(int docId, String fieldName)
			throws RuntimeException {
		try {
			synchronized(reader){
				return reader.getTermVector(docId, fieldName);
			}			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while getting Content Vectors");
		}
	}

	public HashMap<String, Integer> getAllTermsMap(Query q) throws RuntimeException {
		HashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		try{
		TopDocs results = getQueryResults(q);
		int pos = 0;
		for (int i = 0; i < results.scoreDocs.length; i++) {
			ScoreDoc scoreDoc = results.scoreDocs[i];
			int docId = scoreDoc.doc;
			Terms vector = getTermsVector(docId, StringRepo.LUCENE_DESCRIPTION);
			
			TermsEnum termsEnum = vector.iterator(null);
			BytesRef text = null;
			while ((text = termsEnum.next()) != null) {
				String term = text.utf8ToString();
				if(map.get(term)==null)
					map.put(term, pos++);
			}
		}}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return map;
	}
	
	public HashMap<Integer,DocumentVector> getAllDocumentVectors(Query q,HashMap<String,Integer> allTermsMap, HashMap<Long, Integer> productToDocMap) throws RuntimeException {
		
		HashMap<Integer,DocumentVector> docIdVectorMap = new HashMap<Integer,DocumentVector>();
		try{
		TopDocs results = getQueryResults(q);
		for (int i = 0; i < results.scoreDocs.length; i++) {
			ScoreDoc scoreDoc = results.scoreDocs[i];
			int docId = scoreDoc.doc;
			Terms vector;
			long itemId = -1L;
			try {
				synchronized(reader){
					vector = reader.getTermVector(docId, StringRepo.LUCENE_DESCRIPTION);
					itemId = Long.parseLong(reader.document(docId).get(StringRepo.LUCENE_ID));
				}			
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Error while getting Content Vectors");
			}
			if(itemId==-1)
				throw new RuntimeException("Item Corresponding to description not found");
			productToDocMap.put(itemId, docId);
			TermsEnum termsEnum = vector.iterator(null);
            BytesRef text = null;            
            DocumentVector docVector = new DocumentVector(allTermsMap,docId,itemId);          
            while ((text = termsEnum.next()) != null) {
                String term = text.utf8ToString();
                int freq = (int) termsEnum.totalTermFreq();
                docVector.setEntry(term, freq);
            }
            docVector.normalize();
            docIdVectorMap.put(docId,docVector);
        } }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException("Error while generating content vectors");
        }
        return docIdVectorMap;
    }

}
