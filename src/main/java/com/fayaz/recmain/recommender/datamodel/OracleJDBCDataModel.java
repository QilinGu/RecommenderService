package com.fayaz.recmain.recommender.datamodel;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.AbstractJDBCDataModel;

public class OracleJDBCDataModel extends AbstractJDBCDataModel {	
	
	
	private static final long serialVersionUID = 5951678728985695119L;

	private static final String DATASOURCE_NAME="jdbc/recAdminDB";
	
	private static final String RATINGS_TABLE = "Ratings";
	private static final String USER_COL = "User_id";
	private static final String ITEM_COL = "Product_id";
	private static final String CUSTOMER_COL = "Customer_id";
	private static final String RATING_COL = "Rating";
	
	private static DataSource lookupDataSource() throws TasteException 
	{
		Context context = null;
		try 
		{
			context = new InitialContext();
			return (DataSource) context.lookup("java:comp/env/" + DATASOURCE_NAME);
		} catch (NamingException ne) {
			throw new TasteException(ne);
		} finally {
			if (context != null) {
				try {
					context.close();
				} catch (NamingException ne) {
					System.out.println("Error while closing Context; continuing..."+ ne.getMessage());
				}
			}
		}
	}

	protected OracleJDBCDataModel(DataSource dataSource,
			String getPreferenceSQL, String getPreferenceTimeSQL,
			String getUserSQL, String getAllUsersSQL, String getNumItemsSQL,
			String getNumUsersSQL, String setPreferenceSQL,
			String removePreferenceSQL, String getUsersSQL, String getItemsSQL,
			String getPrefsForItemSQL, String getNumPreferenceForItemSQL,
			String getNumPreferenceForItemsSQL, String getMaxPreferenceSQL,
			String getMinPreferenceSQL) {
		super(dataSource, getPreferenceSQL, getPreferenceTimeSQL, getUserSQL,
				getAllUsersSQL, getNumItemsSQL, getNumUsersSQL, setPreferenceSQL,
				removePreferenceSQL, getUsersSQL, getItemsSQL, getPrefsForItemSQL,
				getNumPreferenceForItemSQL, getNumPreferenceForItemsSQL,
				getMaxPreferenceSQL, getMinPreferenceSQL);		
	}

	public OracleJDBCDataModel(long customerId) throws TasteException {
		
		this(lookupDataSource(), 				
				"SELECT "+RATING_COL+" FROM "+RATINGS_TABLE+" WHERE "+USER_COL+"=? AND "+ ITEM_COL + "=? AND "+CUSTOMER_COL+" = ",  //preference SQL
				"", //preference Time SQL ..not using
				//getUserSql
				"SELECT DISTINCT "+USER_COL+","+ITEM_COL+","+RATING_COL+" FROM "+RATINGS_TABLE+" WHERE "+USER_COL+"=? AND "+CUSTOMER_COL+"="+customerId+" ORDER BY "+ITEM_COL,
				// getAllUsersSQL
				"SELECT DISTINCT "+USER_COL+","+ITEM_COL+","+RATING_COL+" FROM "+RATINGS_TABLE+" WHERE "+CUSTOMER_COL+"="+customerId+" ORDER BY "+USER_COL+","+ITEM_COL,
				//getNumItemsSQL
				"SELECT COUNT(DISTINCT "+ITEM_COL+") FROM "+RATINGS_TABLE+" WHERE "+CUSTOMER_COL+"="+customerId, 
				//getNumUsersSQL
				"SELECT COUNT(DISTINCT "+USER_COL+") FROM "+RATINGS_TABLE+" WHERE "+CUSTOMER_COL+"="+customerId,
				//setPreferenceSQL
				"INSERT INTO " + RATINGS_TABLE + '(' + USER_COL + ',' + ITEM_COL + ',' + RATING_COL+","+CUSTOMER_COL
	            + ") VALUES (?,?,?,"+customerId+") ON DUPLICATE KEY UPDATE " + RATING_COL + "=?",
				//removePreferenceSQL
	            "DELETE FROM " + RATINGS_TABLE + " WHERE " + USER_COL + "=? AND " + ITEM_COL + "=? AND "+CUSTOMER_COL+"="+customerId, 
				//getUsersSQL
	            "SELECT DISTINCT " + USER_COL + " FROM " + RATINGS_TABLE + " WHERE "+CUSTOMER_COL+"="+customerId+" ORDER BY " + USER_COL, 
				//getItemsSQL
				"SELECT DISTINCT " + ITEM_COL + " FROM " + RATINGS_TABLE + " WHERE "+CUSTOMER_COL+"="+customerId+" ORDER BY " + ITEM_COL,
				//getPrefsForItemSQL
				 "SELECT DISTINCT " + USER_COL + ", " + ITEM_COL + ", " + RATING_COL + " FROM " + RATINGS_TABLE
		            + " WHERE " + ITEM_COL + "=? AND "+CUSTOMER_COL+"="+customerId+" ORDER BY " + USER_COL,
				//getNumPreferenceForItemSQL
		        "SELECT COUNT(1) FROM " + RATINGS_TABLE + " WHERE " + ITEM_COL + "=? AND "+CUSTOMER_COL+"="+customerId, 
				//getNumPreferenceForItemsSQL
		        "SELECT COUNT(1) FROM " + RATINGS_TABLE + " tp1 JOIN " + RATINGS_TABLE + " tp2 " + "USING ("
            + USER_COL + ") WHERE tp1." + ITEM_COL + "=? and tp2." + ITEM_COL + "=? AND tp1."+CUSTOMER_COL+"="+customerId+" AND tp2."+CUSTOMER_COL+"="+customerId,
				//getMaxPreferenceSQL
            "SELECT MAX(" + RATING_COL + ") FROM " + RATINGS_TABLE+" WHERE "+CUSTOMER_COL+"="+customerId , 
				//getMinPreferenceSQL
            "SELECT MIN(" + RATING_COL + ") FROM " + RATINGS_TABLE+" WHERE "+CUSTOMER_COL+"="+customerId
		);
	}
}
