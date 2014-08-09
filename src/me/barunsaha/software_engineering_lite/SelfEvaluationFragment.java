package me.barunsaha.software_engineering_lite;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

public class SelfEvaluationFragment extends ExperimentFragment {

	public static SelfEvaluationFragment getInstance(int experimentId) {
		SelfEvaluationFragment f = new SelfEvaluationFragment();
		f.mExperimentId = experimentId;
		
		return f;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_self_evaluation, container, false);
         
        return rootView;
    }

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected WebView getWebView() {
		WebView wview = (WebView) getView().findViewById(R.id.wv_selfevaluation);
		wview.getSettings().setJavaScriptEnabled(true);
		//wview.setWebChromeClient(new WebChromeClient());
		return wview;
	}

	@Override
	protected void generateImagesPath() {
		if (TabbedActivity.isEmulator) {
			imagesPath = "file:///android_asset/images/self_evaluation/icons/";
		} else {
			imagesPath = "file:///" + 
					getActivity().getExternalFilesDir(null).getAbsolutePath() +
					"/images/self_evaluation/icons/";
		}
		
	}

	@Override
	protected void loadContent(WebView wView) {
		// Create the database if it doesn't already exist
		DataBaseHelper dbHelper = null;
        try {
			dbHelper = new DataBaseHelper(getActivity().getApplicationContext());
			////dbHelper.createDataBase();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity().getApplicationContext(), 
					"An exception occured: " + e, Toast.LENGTH_LONG)
					.show();
		}
        
        ArrayList<SelfEvaluationContent> allQuestions = 
        		dbHelper.getSelfEvaluations(mExperimentId);
        
        /*
         * Format of a question
         <div id="question_4">
            <h2 id="q_4" style="padding-top: 7px;">4. A prime attributes is indicated by</h2>
            <div id="choices_4">
                <p id="option4_1" class="option"><input type="radio" name="q_4" value="1" /> Underline inside an ellipse
                        <img src="/isad/v_media/images/transparent_2x2.png"
    " class="ansImageInvisible" style="display: inline; padding-left: 20px;" /></p>
                <p id="option4_2" class="option"><input type="radio" name="q_4" value="2" /> Double ellipse
                        <img src="/isad/v_media/images/transparent_2x2.png" class="ansImageInvisible" style="display: inline; padding-left: 20px;" /></p>
                
                <p id="option4_3" class="option"><input type="radio" name="q_4" value="3" /> Dotted ellipse
                        <img src="/isad/v_media/images/transparent_2x2.png" class="ansImageInvisible" style="display: inline; padding-left: 20px;" /></p>
                
                
                <p id="option4_4" class="option"><input type="radio" name="q_4" value="4" /> Dotted rectangle
                        <img src="/isad/v_media/images/transparent_2x2.png" class="ansImageInvisible" style="display: inline; padding-left: 20px;" /></p>
                
                <input type="hidden" id="qa_4" value="1" />
            </div>
        </div>
         */
        
        String html = "";
        
        for (SelfEvaluationContent selfEval : allQuestions) {
        	int qNum = selfEval.getQNumber();
        	
        	html += "<div id=\"question_" + qNum + "\">" +
        			"<h3 id=\"q_" + qNum + "\" style=\"padding-top: 7px;\">" +
        			qNum + ". " + selfEval.getQuestion() + "</h3>" +
        			"<div id=\"choices_" + qNum + "\">";
        	
        	String options = "";
        	String[] choices = {selfEval.getOption1(), selfEval.getOption2(),
        			selfEval.getOption3(), selfEval.getOption4()};
        	
        	for (int i = 1; i <= 4; i++) {
        		if (choices[i-1].length() == 0) {
        			break;
        		}
        		options += "<p id=\"option" + qNum + "_" + i + 
        				"\" class=\"option\">";
            	options += "<input type=\"radio\" name=\"q_" + qNum + "\"" +
            			" value=\"" + i + "\" id=\"q_" + qNum + "_" + i + "\" />";
            	options += "<label for=\"q_" + qNum + "_" + i + "\">" + choices[i-1] + "</label>";
            	options += "<img src=\"transparent_2x2.png\"" +
            			"class=\"ansImageInvisible\" style=\"display: inline; padding-left: 20px;\" />";
            	options += "</p>";
        	}

        	html += options;
        	html += "  <input type=\"hidden\" id=\"qa_" + qNum + "\" value=\"" + 
        				selfEval.getAnswer() + "\" />" +
        			"</div>" +
        			"</div>";
        }
        
        html += "<div style=\"clear: both;\" id=\"results\"></div>";
        html += "<div class=\"centerAlign\" style=\"padding-top: 5px; padding-bottom: 3px; clear: both;\">";
    	html += "<button type=\"button\" id=\"btnSubmit\" class=\"button-icon\">";
    	//html += "<img src=\"next24x24.png\" class=\"button-run\" />";
    	html += "<strong>Submit</strong> </button> </div>";
    	//html += "<div style=\"clear: both;\"></div>";
    	//html += "<button type=\"button\" id=\"btnClear\" class=\"button-icon\">";
    	//html += "<img src=\"edit_clear24x24.png\" class=\"button-clear\" />";
    	//html += "Clear </button> </div>";
    	
    	//Log.i(MainActivity.TAG, html);
        
		mContent = "<html><head>" + 
				styleSheet + jqueryStyleSheet + 
				jqueryJs + jqueryMobileJs + 
				customJs +
				//"<script type=\"text/javascript\">console.log(\"Hello World inline script\");alert(\"Hello World inline script\");</script>" +
				"</head><body>" +
				html +
				"</body></html>";
	}
}
