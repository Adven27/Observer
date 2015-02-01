package com.urban.dao;

import android.content.Context;

import com.urban.appl.Settings;
import com.urban.data.dao.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBInitializer {

    public static void initDB(Context context) throws Exception{
        if (!isDbFileExist(context)) {
            copyDataBase(context, Settings.getDBName());
        }
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
        String pathToDBDir = getDBPath(context);

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

    private static boolean isDbFileExist(Context context) {
        File dbFile = new File(getDBPath(context) + Settings.getDBName());
        return dbFile.exists();
    }

    private static String getDBPath(Context context) {
        System.out.println(context.getApplicationInfo().dataDir + "/databases/");
        return android.os.Build.VERSION.SDK_INT >= 4.2 ?
                context.getApplicationInfo().dataDir + "/databases/"
                : "/data/data/" + context.getPackageName() + "/databases/";
    }


}
