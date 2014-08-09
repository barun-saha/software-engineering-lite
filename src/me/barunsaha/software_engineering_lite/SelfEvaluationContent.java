package me.barunsaha.software_engineering_lite;

public class SelfEvaluationContent extends Content {

	protected int mQNumber;
	protected String mQuestion;
	protected String mOption1;
	protected String mOption2;
	protected String mOption3;
	protected String mOption4;
	protected int mAnswer;
	
	public SelfEvaluationContent(int experimentId, int qNumber, String question, 
			String option1, String option2, String option3, String option4, 
			int answer) {
		super("");
		
		mQNumber = qNumber;
		mQuestion = question;
		mOption1 = option1;
		mOption2 = option2;
		mOption3 = option3;
		mOption4 = option4;
		mAnswer = answer;
	}
	
	public String getQuestion() {
		return mQuestion;
	}
	
	public int getAnswer() {
		return mAnswer;
	}

	public int getQNumber() {
		return mQNumber;
	}
	
	public String getOption1() {
		return mOption1;
	}

	public String getOption2() {
		return mOption2;
	}

	public String getOption3() {
		return mOption3;
	}

	public String getOption4() {
		return mOption4;
	}
}