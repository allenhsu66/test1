package hk.ed.kgs.inspectionfa.data;

import hk.ed.androidframework.db.DbConstant;

import java.util.Collection;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Sample class for future reference
 * @author Lokey
 *
 */
@DatabaseTable(tableName = DbConstant.TABLE_A)
public class A {
	@Expose
	@DatabaseField(id = true)
	public Integer	id;
	
	@Expose
	@DatabaseField
	public String	content;
	
	@Expose
	@ForeignCollectionField
	public Collection<B> listObjects;

}
