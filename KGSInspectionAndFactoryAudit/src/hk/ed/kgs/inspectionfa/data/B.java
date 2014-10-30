package hk.ed.kgs.inspectionfa.data;

import hk.ed.androidframework.db.DbConstant;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Sample class for future reference
 * @author Lokey
 *
 */
@DatabaseTable(tableName = DbConstant.TABLE_B)
public class B {
	@Expose
	@DatabaseField(id = true)
	public Integer	id;
	
	@DatabaseField(foreign = true)
	public A sample;
	
	@Expose
	@DatabaseField
	public String	content;

}
