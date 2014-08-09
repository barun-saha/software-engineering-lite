package me.barunsaha.software_engineering_lite;

public abstract class Content {
	
	protected String mTitle;
	
	public Content(String title) {
		mTitle = title;
	}
	
	public String getTitle() {
		return mTitle;
	}
}
