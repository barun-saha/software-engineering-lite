package me.barunsaha.software_engineering_lite;

public class Experiment {

	private int mId;
	private String mTitle;
	//private String theory = "";
	
	public Experiment(int id, String title) {
		super();
		this.mId = id;
		this.mTitle = title;
	}

	public Experiment(int id, String title, String content) {
		super();
		this.mId = id;
		this.mTitle = title;
		//this.theory = content;
	}
	
	public int getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	//public String getTheory() {
		//return theory;
	//}

	@Override
	public String toString() {
		return "Experiment [mId=" + mId + ", mTitle=" + mTitle + "]";
	}
}
