package com.example.prototype.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class OrmligthSQLLightDAO extends OrmLiteSqliteOpenHelper implements IDAO {

    private Map<Class, RuntimeExceptionDao> cashedDao = new HashMap<Class, RuntimeExceptionDao>();

    public OrmligthSQLLightDAO(String dbName, int dbversion, Context context) {
        super(context, dbName, null, dbversion /*, R.raw.ormlite_config*/);
    }
    
    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        // TODO Auto-generated method stub
    }

	public <T> void save(T objToSave) {
		((RuntimeExceptionDao<T, Long>)getRuntimeDAO(
				objToSave.getClass())).createOrUpdate(objToSave);
		// TODO Auto-generated method stub
		
	}
	
	public <T> Collection<T> getAll(Class<T> type) {
		return getRuntimeDAO(type).queryForAll();
	}
	
	public <T> T get(Class<T> type, long id) {
		return getRuntimeDAO(type).queryForId(id);
	}
	
	private <T> RuntimeExceptionDao<T, Long> getRuntimeDAO(Class<T> type){
		RuntimeExceptionDao<T, Long> dao;
		if (cashedDao.containsKey(type)){
			dao = cashedDao.get(type);
		}else{
			dao = getRuntimeExceptionDao(type);
			cashedDao.put(type, dao);			
		}
		return dao;
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
}