package hk.ed.androidframework.db;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;


public class LoginDao {
//	sample code
//	private OrmLiteSqliteOpenHelper		helper;
//
//	private Dao<Authentication, String>	dao;
//
	public LoginDao(OrmLiteSqliteOpenHelper helper) throws SQLException {
//		this.helper = helper;
//		dao = helper.getDao(Authentication.class);
	}
//
//	public Dao<Authentication, String> getRawDao() throws SQLException {
//		return dao;
//	}
//
//	public void updateLogin(Authentication login) throws SQLException {
//		dao.createOrUpdate(login);
//	}
//
//	public Authentication loadLogin(String username, String password) throws SQLException {
//
//		// HashMap<String, Object> map = new HashMap<String, Object>();
//		// map.put("MobileAuthenticationUID", username);
//		// map.put("MobileAuthenticationPassword", password);
//		// List<Authentication> results = dao.queryForFieldValues(map);
//
//		Authentication matchObj = new Authentication();
//		matchObj.MobileAuthenticationUID = username;
//		matchObj.MobileAuthenticationPassword = password;
//		List<Authentication> results = dao.queryForMatching(matchObj);
//		if (results.size() > 0) {
//			return results.get(0);
//		}
//		return null;
//	}
}
