package me.barunsaha.software_engineering_lite;

public class TheoryContent extends Content {

	protected String mContent;
		
	public TheoryContent(int experimentId, String title, String content) {
		super(title);
		mContent = content;
	}
	
	public String getContent() {
		return mContent;
	}
}