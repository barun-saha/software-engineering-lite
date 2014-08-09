package me.barunsaha.software_engineering_lite;

public class ReferenceContent extends Content {
	// Reference to a website
	protected String mUrl;
	protected String mUrlDescription;
	// Reference to a book
	protected String mTitle;
	protected String mAuthor;
	protected String mPublisher;
	protected String mEdition;
	
	public ReferenceContent(int experimentId, String url, String description,
			String title, String author, String publisher, String edition) {
		super("");
		
		mUrl = url;
		mUrlDescription = description;
		mTitle = title;
		mAuthor = author;
		mPublisher = publisher;
		mEdition = edition;
	}

	public String getUrl() {
		return mUrl;
	}

	public String getUrlDescription() {
		return mUrlDescription;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public String getPublisher() {
		return mPublisher;
	}

	public String getEdition() {
		return mEdition;
	}
	
	public String toString() {
		return mUrl + " " + mUrlDescription + " " + mTitle + " " + mAuthor;
	}
}