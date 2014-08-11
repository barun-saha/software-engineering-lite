package me.barunsaha.software_engineering_lite;

public class Experiment {

	private int mId;
	private String mTitle;
	
	public Experiment(int id, String title) {
		super();
		this.mId = id;
		this.mTitle = title;
	}

	public Experiment(int id, String title, String content) {
		super();
		this.mId = id;
		this.mTitle = title;
	}
	
	public int getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}
	
	@Override
	public String toString() {
		return "Experiment [mId=" + mId + ", mTitle=" + mTitle + "]";
	}
}
