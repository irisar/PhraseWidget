package com.lovelydeveloper.phrasewidget.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.List;
import java.util.LinkedList;

public class CategoryController extends DataBaseHelper{
	
    public CategoryController(Context context) {
		super(context);
	}

	//Categories table name
	private static final String TABLE_NAME = "categories";
	
	// Categories Table Columns names
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String COLOR = "color";
	private static final String CHECKED = "checked";
	
	private static final String[] COLUMNS = {ID,NAME,COLOR,CHECKED};
    
    
	public void addCategory(CategoryBean phrases){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(ID, phrases.getId()); 
		values.put(NAME, phrases.getName());
		values.put(COLOR, phrases.getColor());
		values.put(CHECKED, phrases.getChecked()); 
		
		db.insert(TABLE_NAME, null, values); 
		
		db.close(); 
	}
    
	
	public CategoryBean getCategory(int id){
		SQLiteDatabase db = getReadableDatabase();
		CategoryBean phrase = new CategoryBean();
		
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
		 
		if (cursor != null) {
			cursor.moveToFirst();
			phrase.setId(cursor.getInt(0));
			phrase.setName(cursor.getString(1));
			phrase.setColor(cursor.getString(2));
			phrase.setChecked(cursor.getInt(0));
		}
		db.close(); 
		return phrase;
	}
	
	public List<CategoryBean> getAllCategories() {
		List<CategoryBean> phrases = new LinkedList<CategoryBean>();
		
		String query = "SELECT " + ID + "," + NAME + "," + COLOR + "," + CHECKED  + " FROM " + TABLE_NAME;
		
		CategoryBean phrase = null;
		try {
			SQLiteDatabase db = getWritableDatabase();
			Cursor cursor = db.rawQuery(query, null);
			if (cursor.moveToFirst()) {
				do {
					phrase = new CategoryBean();
					phrase.setId(cursor.getInt(0));
					phrase.setName(cursor.getString(1));
					phrase.setColor(cursor.getString(2));
					phrase.setChecked(cursor.getInt(3));
	
					phrases.add(phrase);
				} while (cursor.moveToNext());
			}
			db.close();
		} catch (Exception e) {
			Log.d("CategoryWidget", "Error: " + e);
		}
		return phrases;
	}
}
