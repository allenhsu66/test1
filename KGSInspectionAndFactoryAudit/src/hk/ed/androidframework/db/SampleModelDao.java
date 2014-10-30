package hk.ed.androidframework.db;

import hk.ed.kgs.inspectionfa.data.A;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

public class SampleModelDao {
	// sample code
	private OrmLiteSqliteOpenHelper helper;

	private Dao<A, Integer> dao;

	public SampleModelDao(OrmLiteSqliteOpenHelper helper) throws SQLException {
		this.helper = helper;
		dao = helper.getDao(A.class);
	}

	public Dao<A, Integer> getRawDao() throws SQLException {
		return dao;
	}

	public void updateSampleModel(A login) throws SQLException {
		dao.createOrUpdate(login);
	}

	public A loadSampleModel(int id) throws SQLException {
		A matchObj = new A();
		matchObj.id = id;
		List<A> results2 = dao.queryForMatching(matchObj);
		if (results2.size() > 0) {
			return results2.get(0);
		}
		return null;
	}
}
