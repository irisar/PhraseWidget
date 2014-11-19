package com.lovelydeveloper.phrasewidget.db;

public class PhraseBean {
	private int id;
	private String text;
	private int category;
	private int size;
	private String font;
	private String dayOfTheYear;
	private int dayOfTheWeek;

	
	public PhraseBean(){}

	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getFont() {
		return font;
	}
	public void setFont(String font) {
		this.font = font;
	}

	public String getDayOfTheYear() {
		return dayOfTheYear;
	}

	public void setDayOfTheYear(String dayOfTheYear) {
		this.dayOfTheYear = dayOfTheYear;
	}

	public int getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(int dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	
}
