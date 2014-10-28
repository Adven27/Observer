package com.urban.db.utils;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteDatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import com.urban.entity.pojo.AddressPojo;
import com.urban.entity.pojo.AdvertisingPojo;
import com.urban.entity.pojo.CategoryAdvertisingLinkPojo;
import com.urban.entity.pojo.CategoryPojo;
import com.urban.entity.pojo.CategoryPositionLinkPojo;
import com.urban.entity.pojo.ContactPojo;
import com.urban.entity.pojo.EventPlaceLinkPojo;
import com.urban.entity.pojo.EventPojo;
import com.urban.entity.pojo.ImagePojo;
import com.urban.entity.pojo.OrganizationContactLinkPojo;
import com.urban.entity.pojo.OrganizationEventLinkPojo;
import com.urban.entity.pojo.OrganizationPlaceLinkPojo;
import com.urban.entity.pojo.OrganizationPojo;
import com.urban.entity.pojo.PlaceAddressLinkPojo;
import com.urban.entity.pojo.PlacePojo;
import com.urban.entity.pojo.PositionPojo;


public class DBCreationScript {

	public static void main(String[] args) {
		List<String> list;
		try {
			ConnectionSource cs = new FakeConnection();
			list = TableUtils.getCreateTableStatements(cs, CategoryPojo.class);
			list.addAll( TableUtils.getCreateTableStatements(cs, PositionPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, CategoryPositionLinkPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, CategoryAdvertisingLinkPojo.class) );
						
			list.addAll( TableUtils.getCreateTableStatements(cs, OrganizationPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, ContactPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, PlacePojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, EventPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, EventPlaceLinkPojo.class) );
			
			list.addAll( TableUtils.getCreateTableStatements(cs, AddressPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, PlaceAddressLinkPojo.class) );
			

			list.addAll( TableUtils.getCreateTableStatements(cs, OrganizationContactLinkPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, OrganizationPlaceLinkPojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, OrganizationEventLinkPojo.class) );
			

			list.addAll( TableUtils.getCreateTableStatements(cs, ImagePojo.class) );
			list.addAll( TableUtils.getCreateTableStatements(cs, AdvertisingPojo.class) );

			for (String s : list)
				  System.out.println(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static class FakeConnection implements ConnectionSource{

		@Override
		public DatabaseConnection getReadOnlyConnection() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DatabaseConnection getReadWriteConnection() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void releaseConnection(DatabaseConnection connection)
				throws SQLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean saveSpecialConnection(DatabaseConnection connection)
				throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void clearSpecialConnection(DatabaseConnection connection) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DatabaseConnection getSpecialConnection() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() throws SQLException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void closeQuietly() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DatabaseType getDatabaseType() {
			return new SqliteDatabaseType();
		}

		@Override
		public boolean isOpen() {
			// TODO Auto-generated method stub
			return false;
		} 
		
	}


}
