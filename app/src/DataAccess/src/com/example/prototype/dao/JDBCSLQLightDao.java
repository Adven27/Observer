package com.example.prototype.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import example.entity.Category;
import example.entity.Position;

public class JDBCSLQLightDao implements IDAO {
	   
	private ConnectionSource connectionSource;

	   public JDBCSLQLightDao(String jdbcConnectionStr){
	        // create a connection source to our database
	        try {
				connectionSource =
				    new JdbcConnectionSource( jdbcConnectionStr );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
	   }
	   /* (non-Javadoc)
	 * @see com.example.prototype.dao.IDAO#save(T)
	 */
	@Override
	public <T> void save(T objToSave){	   
		   Dao<T, String> dao;
		   try {
			   dao = (Dao<T, String>)getDao(objToSave.getClass());
			   dao.createOrUpdate(objToSave);
		   } catch (SQLException e) {
			// TODO implement exception handling
			e.printStackTrace();
		   }

	   }
	   
	   /* (non-Javadoc)
	 * @see com.example.prototype.dao.IDAO#getAll(java.lang.Class)
	 */
	@Override
	public <T> Collection<T> getAll(Class<T> type){
		   Dao<T, String> dao;
		   try {
			   dao = getDao(type);
			   return dao.queryForAll();
		   } catch (SQLException e) {
			// TODO implement exception handling
			e.printStackTrace();
		   }
		   return null;
	   }
	   
	   /* (non-Javadoc)
	 * @see com.example.prototype.dao.IDAO#get(java.lang.Class, long)
	 */
	@Override
	public <T> T get(Class<T> type, long id){
		   
		   try {
			   Dao<T, String> dao = getDao(type);
			   return dao.queryForId(Long.toString(id));
		   } catch (SQLException e) {
			// TODO implement exception handling
			e.printStackTrace();
		   }
		   return null;
	   }
	   
	   private <T> Dao<T, String> getDao(Class<T> type) throws SQLException{
		  // DaoManager.registerDao(connectionSource, null);
		   
		   Dao<T, String> dao = DaoManager.lookupDao(connectionSource, type);
		   if (dao == null){
			   dao = DaoManager.createDao(connectionSource, type);
		   };
		   return dao;
	   }
}
