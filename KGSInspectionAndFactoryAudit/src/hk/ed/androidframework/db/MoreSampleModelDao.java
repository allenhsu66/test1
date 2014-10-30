package hk.ed.androidframework.db;

import hk.ed.kgs.inspectionfa.data.B;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

public class MoreSampleModelDao {
	// sample code
	private OrmLiteSqliteOpenHelper helper;

	private Dao<B, Integer> dao;

	public MoreSampleModelDao(OrmLiteSqliteOpenHelper helper) throws SQLException {
		this.helper = helper;
		dao = helper.getDao(B.class);
	}

	public Dao<B, Integer> getRawDao() throws SQLException {
		return dao;
	}

	public void updateSampleModel(B login) throws SQLException {
		dao.createOrUpdate(login);
	}
	
	public List<B> loadAllMoreSampleModels() throws SQLException {
		return dao.queryForAll();
	}

	public B loadSampleModel(int id) throws SQLException {
		B matchObj = new B();
		matchObj.id = id;
		List<B> results2 = dao.queryForMatching(matchObj);
		if (results2.size() > 0) {
			return results2.get(0);
		}
		return null;
	}
}
