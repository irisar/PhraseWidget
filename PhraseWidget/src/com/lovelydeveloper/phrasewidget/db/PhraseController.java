package com.lovelydeveloper.phrasewidget.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class PhraseController extends DataBaseHelper{
	
    public PhraseController(Context context) {
		super(context);
	}

	//Phrases table name
	private static final String TABLE_NAME = "phrases";
	
	// Phrases Table Columns names
	private static final String ID = "id";
	private static final String TEXT = "phrase";
	private static final String CATEGORY = "category";
	private static final String SIZE = "size";
	private static final String FONT = "font";
	private static final String DAYOFTHEYEAR = "dayoftheyear";
	private static final String DAYOFTHEWEEK = "dayoftheweek";

	
	private static final String[] COLUMNS = {ID,TEXT,CATEGORY,SIZE,FONT,DAYOFTHEYEAR,DAYOFTHEWEEK};
    
    
	public void addPhrase(PhraseBean phrases){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(ID, phrases.getId()); 
		values.put(TEXT, phrases.getText());
		values.put(CATEGORY, phrases.getCategory());
		values.put(SIZE, phrases.getSize());
		values.put(FONT, phrases.getFont());
		values.put(DAYOFTHEYEAR, phrases.getDayOfTheYear());
		values.put(DAYOFTHEWEEK, phrases.getDayOfTheWeek());
		
		db.insert(TABLE_NAME, null, values); 
		
		db.close(); 
	}
    
	
	public PhraseBean getPhrase(int id){
		SQLiteDatabase db = getReadableDatabase();
		PhraseBean phrase = new PhraseBean();
		
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
		 
		if (cursor != null) {
			cursor.moveToFirst();
			phrase.setId(cursor.getInt(0));
			phrase.setText(cursor.getString(1));
			phrase.setCategory(cursor.getInt(2));
			phrase.setSize(cursor.getInt(3));
			phrase.setFont(cursor.getString(4));
			phrase.setDayOfTheYear(cursor.getString(5));
			phrase.setDayOfTheWeek(cursor.getInt(6));
		}
		db.close(); 
		return phrase;
	}
	
	public List<PhraseBean> getAllPhrases() {
		List<PhraseBean> phrases = new LinkedList<PhraseBean>();

		String DATE_FORMAT_NOW = "dd/MM/yyyy";
		Date day = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String today = sdf.format(day);
		
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		
		String query = "SELECT " + ID + "," + TEXT + "," + CATEGORY + "," + SIZE + "," + FONT + "," + DAYOFTHEYEAR + "," + DAYOFTHEWEEK 
						+ " FROM " + TABLE_NAME + " WHERE (" + DAYOFTHEYEAR + " is NULL OR " + DAYOFTHEYEAR + " = '" + today.toString() + "' )"
						+ " AND (" + DAYOFTHEWEEK + " is NULL OR " + DAYOFTHEWEEK + " = " +  dayOfWeek +")"
						+ " AND " + CATEGORY +" in (select id from categories where checked = 1)";
		
		PhraseBean phrase = null;
		try {
			SQLiteDatabase db = getWritableDatabase();
			Cursor cursor = db.rawQuery(query, null);
			if (cursor.moveToFirst()) {
				do {
					phrase = new PhraseBean();
					phrase.setId(cursor.getInt(0));
					phrase.setText(cursor.getString(1));
					phrase.setCategory(cursor.getInt(2));
					phrase.setSize(cursor.getInt(3));
					phrase.setFont(cursor.getString(4));
					phrase.setDayOfTheYear(cursor.getString(5));
					phrase.setDayOfTheWeek(cursor.getInt(6));
	
					phrases.add(phrase);
				} while (cursor.moveToNext());
			}
			db.close();
		} catch (Exception e) {
			Log.d("PhraseWidget", "Error: " + e);
		}
		return phrases;
	}
	
	public ArrayList<PhraseBean> getPhrasesFromCategory(int category) {
		ArrayList<PhraseBean> phrases = new ArrayList<PhraseBean>();

		String DATE_FORMAT_NOW = "dd/MM/yyyy";
		Calendar day = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String today = sdf.format(day);
		
		String query = "SELECT " + ID + "," + TEXT + "," + CATEGORY + "," + SIZE + "," + FONT + "," + DAYOFTHEYEAR + "," + DAYOFTHEWEEK 
						+ " FROM " + TABLE_NAME 
						+ " WHERE " + CATEGORY + " = " + category 
									+ "AND (" + DAYOFTHEYEAR + " is NULL OR " + DAYOFTHEYEAR + " = '" + today.toString() + "' )";

		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		PhraseBean phrase = null;
		if (cursor.moveToFirst()) {
			do {
				phrase = new PhraseBean();
				phrase.setId(Integer.parseInt(cursor.getString(0)));
				phrase.setId(cursor.getInt(0));
				phrase.setText(cursor.getString(1));
				phrase.setCategory(cursor.getInt(2));
				phrase.setSize(cursor.getInt(3));
				phrase.setFont(cursor.getString(4));
				phrase.setDayOfTheYear(cursor.getString(5));
				phrase.setDayOfTheWeek(cursor.getInt(6));

				phrases.add(phrase);
			} while (cursor.moveToNext());
			
			//Randomize
			long seed = System.nanoTime();
			Collections.shuffle(phrases, new Random(seed));
		}
		db.close(); 
		return phrases;
	}
	
	public PhraseBean getRandomPhrase() {
		List<PhraseBean> phrases = getAllPhrases();
		Random randomizer = new Random();
		PhraseBean phrase = phrases.get(randomizer.nextInt(phrases.size()));
		
		Log.d("PhraseWidget", "Phrase id:" + phrase.getId() + " Text: " + phrase.getText() + " Font: "
								+ phrase.getFont() + " Size: " + phrase.getSize() + " Category: " + phrase.getCategory());
		
		return phrase;
	}
}
