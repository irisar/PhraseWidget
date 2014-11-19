package com.lovelydeveloper.phrasewidget.db;

public class CategoryBean {
	private int id;
    private String name;
    private String color;
    private int checked;
 
    public CategoryBean(){}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getChecked() {
		return checked;
	}


	public void setChecked(int checked) {
		this.checked = checked;
	}
	
	
 
}
