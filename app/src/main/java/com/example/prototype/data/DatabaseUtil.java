package com.example.prototype.data;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseUtil extends OrmLiteConfigUtil {

	public static void main(String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt");
	}
}
