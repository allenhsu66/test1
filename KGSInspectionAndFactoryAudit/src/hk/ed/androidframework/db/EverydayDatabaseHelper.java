package hk.ed.androidframework.db;

import hk.ed.android.library.util.LoggingUtils;
import hk.ed.kgs.inspectionfa.data.A;
import hk.ed.kgs.inspectionfa.data.B;

import java.sql.SQLException;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class EverydayDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final int	DATABASE_VERSION	= 1;

	private static final String	DATABASE_NAME		= "gammon.db";

	public EverydayDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
//		sample code
		try {
			LoggingUtils.log(EverydayDatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, A.class);
			TableUtils.createTable(connectionSource, B.class);
		} catch (SQLException e) {
			LoggingUtils.log(EverydayDatabaseHelper.class.getName(), "Can't create database: " + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		/**
		 * It is only for demonstration purpose. A better approach is to do incremental update
		 */
//		incremental update example here
//		switch (oldVersion) {
//		case 1:
//			// update from version 1, add new table?
//		case 2:
//			// update from version 2, convert old object into new ones?
//		case 3:
//			// update from version 3, drop a table and make a new one?
//		}
		
		// A simple demonstration of update: drop all tables. It will lose all data!
		db.beginTransaction();
		try {
			dropTableIfExist(db, DbConstant.TABLE_A);
			dropTableIfExist(db, DbConstant.TABLE_B);
			onCreate(db, connectionSource);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	private void dropTableIfExist(SQLiteDatabase db, String tableName) {
		String sqlStatement = "DROP TABLE IF EXISTS " + DatabaseUtils.sqlEscapeString(tableName);
		db.execSQL(sqlStatement);
	}

	public SampleModelDao getLoginDao() throws SQLException {
		return new SampleModelDao(this);
	}

}
