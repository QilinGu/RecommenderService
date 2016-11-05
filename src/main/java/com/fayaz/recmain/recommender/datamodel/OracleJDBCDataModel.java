package com.fayaz.recmain.recommender.datamodel;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.AbstractJDBCDataModel;

import com.fayaz.recmain.recommender.util.StringRepo;

public class OracleJDBCDataModel extends AbstractJDBCDataModel {

	private static final long serialVersionUID = 5951678728985695119L;	

	private static DataSource lookupDataSource() throws TasteException {
		Context context = null;
		try {
			context = new InitialContext();
			return (DataSource) context.lookup("java:comp/env/"
					+ StringRepo.DATASOURCE_NAME);
		} catch (NamingException ne) {
			throw new TasteException(ne);
		} finally {
			if (context != null) {
				try {
					context.close();
				} catch (NamingException ne) {
					System.out
							.println("Error while closing Context; continuing..."
									+ ne.getMessage());
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
				getAllUsersSQL, getNumItemsSQL, getNumUsersSQL,
				setPreferenceSQL, removePreferenceSQL, getUsersSQL,
				getItemsSQL, getPrefsForItemSQL, getNumPreferenceForItemSQL,
				getNumPreferenceForItemsSQL, getMaxPreferenceSQL,
				getMinPreferenceSQL);
	}

	public OracleJDBCDataModel(long customerId) throws TasteException {

		this(lookupDataSource(), "SELECT " + StringRepo.RATING_COL + " FROM "
				+ StringRepo.RATINGS_TABLE + " WHERE " + StringRepo.USER_COL + "=? AND " + StringRepo.ITEM_COL
				+ "=? AND " + StringRepo.CUSTOMER_COL + " = " + customerId, // preference
																	// SQL
				"", // preference Time SQL ..not using
				// getUserSql
				"SELECT DISTINCT " + StringRepo.USER_COL + "," + StringRepo.ITEM_COL + ","
						+ StringRepo.RATING_COL + " FROM " + StringRepo.RATINGS_TABLE + " WHERE "
						+ StringRepo.USER_COL + "=? AND " + StringRepo.CUSTOMER_COL + "="
						+ customerId + " ORDER BY " + StringRepo.ITEM_COL,
				// getAllUsersSQL
				"SELECT DISTINCT " + StringRepo.USER_COL + "," + StringRepo.ITEM_COL + ","
						+ StringRepo.RATING_COL + " FROM " + StringRepo.RATINGS_TABLE + " WHERE "
						+ StringRepo.CUSTOMER_COL + "=" + customerId + " ORDER BY "
						+ StringRepo.USER_COL + "," + StringRepo.ITEM_COL,
				// getNumItemsSQL
				"SELECT COUNT(DISTINCT " + StringRepo.ITEM_COL + ") FROM " + StringRepo.RATINGS_TABLE
						+ " WHERE " + StringRepo.CUSTOMER_COL + "=" + customerId,
				// getNumUsersSQL
				"SELECT COUNT(DISTINCT " + StringRepo.USER_COL + ") FROM " + StringRepo.RATINGS_TABLE
						+ " WHERE " + StringRepo.CUSTOMER_COL + "=" + customerId,
				// setPreferenceSQL
				"INSERT INTO " + StringRepo.RATINGS_TABLE + '(' + StringRepo.USER_COL + ','
						+ StringRepo.ITEM_COL + ',' + StringRepo.RATING_COL + "," + StringRepo.CUSTOMER_COL
						+ ") VALUES (?,?,?," + customerId
						+ ") ON DUPLICATE KEY UPDATE " + StringRepo.RATING_COL + "=?",
				// removePreferenceSQL
				"DELETE FROM " + StringRepo.RATINGS_TABLE + " WHERE " + StringRepo.USER_COL
						+ "=? AND " + StringRepo.ITEM_COL + "=? AND " + StringRepo.CUSTOMER_COL + "="
						+ customerId,
				// getUsersSQL
				"SELECT DISTINCT " + StringRepo.USER_COL + " FROM " + StringRepo.RATINGS_TABLE
						+ " WHERE " + StringRepo.CUSTOMER_COL + "=" + customerId
						+ " ORDER BY " + StringRepo.USER_COL,
				// getItemsSQL
				"SELECT DISTINCT " + StringRepo.ITEM_COL + " FROM " + StringRepo.RATINGS_TABLE
						+ " WHERE " + StringRepo.CUSTOMER_COL + "=" + customerId
						+ " ORDER BY " + StringRepo.ITEM_COL,
				// getPrefsForItemSQL
				"SELECT DISTINCT " + StringRepo.USER_COL + ", " + StringRepo.ITEM_COL + ", "
						+ StringRepo.RATING_COL + " FROM " + StringRepo.RATINGS_TABLE + " WHERE "
						+ StringRepo.ITEM_COL + "=? AND " + StringRepo.CUSTOMER_COL + "="
						+ customerId + " ORDER BY " + StringRepo.USER_COL,
				// getNumPreferenceForItemSQL
				"SELECT COUNT(1) FROM " + StringRepo.RATINGS_TABLE + " WHERE " + StringRepo.ITEM_COL
						+ "=? AND " + StringRepo.CUSTOMER_COL + "=" + customerId,
				// getNumPreferenceForItemsSQL
				"SELECT COUNT(1) FROM " + StringRepo.RATINGS_TABLE + " tp1 JOIN "
						+ StringRepo.RATINGS_TABLE + " tp2 " + "USING (" + StringRepo.USER_COL
						+ ") WHERE tp1." + StringRepo.ITEM_COL + "=? and tp2." + StringRepo.ITEM_COL
						+ "=? AND tp1." + StringRepo.CUSTOMER_COL + "=" + customerId
						+ " AND tp2." + StringRepo.CUSTOMER_COL + "=" + customerId,
				// getMaxPreferenceSQL
				"SELECT MAX(" + StringRepo.RATING_COL + ") FROM " + StringRepo.RATINGS_TABLE
						+ " WHERE " + StringRepo.CUSTOMER_COL + "=" + customerId,
				// getMinPreferenceSQL
				"SELECT MIN(" + StringRepo.RATING_COL + ") FROM " + StringRepo.RATINGS_TABLE
						+ " WHERE " + StringRepo.CUSTOMER_COL + "=" + customerId);
	}
}
