package com.example.prototype.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.prototype.appl.Settings;

import android.content.Context;

public class DBInitializer {

	public static void initDB(Context context) throws Exception{
		copyDataBase(context, Settings.getDBName());
	}
	
	public static void initDAO(Context context){
		DAO.setDAO(new OrmligthSQLLightDAO(Settings.getDBName(), 1, context));
	}
	
    /**
     * Copy the database from assets
     * @param context
     * @throws IOException
     */
    public static void copyDataBase(Context context, String dbName) throws IOException
    {
    	String pathToDBDir = android.os.Build.VERSION.SDK_INT >= 4.2 ?
             context.getApplicationInfo().dataDir + "/databases/"
           : "/data/data/" + context.getPackageName() + "/databases/";
    	
        InputStream mInput = null;
        OutputStream mOutput = null;
        try{
            File dirForDb = new File( pathToDBDir );
            if ( !dirForDb.exists() ){
                dirForDb.mkdir();
            }
            mInput = context.getAssets().open(dbName);
            String outFileName = pathToDBDir + dbName;
            mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer))>0)
            {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
        }finally{
          if (mOutput != null){
              mOutput.close();
          }
          if (mInput != null){
              mInput.close();
          }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
 /*private boolean isDbFileExist()
 {
     File dbFile = new File(DB_PATH + DATABASE_NAME);
     return dbFile.exists();
 }*/


}
