package me.barunsaha.software_engineering_lite;

public class CaseStudyContent extends Content {

	protected String mCase;
	protected String mAnalysis;
	
	public CaseStudyContent(int experimentId, String title, String problem,
			String analysis) {
		super(title);
		mCase = problem;
		mAnalysis = analysis;
	}
	
	public String getCase() {
		return mCase;
	}
	
	public String getAnalysis() {
		return mAnalysis;
	}
}