package me.barunsaha.software_engineering_lite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

public class CaseStudyFragment extends ExperimentFragment {

	public static CaseStudyFragment getInstance(int experimentId) {
		CaseStudyFragment f = new CaseStudyFragment();
		f.mExperimentId = experimentId;
		
		return f;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_case_study, container, false);
         
        return rootView;
    }

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected WebView getWebView() {
		WebView wview = (WebView) getView().findViewById(R.id.wv_casestudy);
		wview.getSettings().setJavaScriptEnabled(true);
		//wview.setWebChromeClient(new WebChromeClient());
		return wview;
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
        
        CaseStudyContent caseStudy = dbHelper.getCaseStudy(mExperimentId);
        String title = "<h1>" + caseStudy.getTitle() + "</h1>";
		mContent = "<html><head>" + 
				styleSheet + jqueryStyleSheet + 
				jqueryJs + jqueryMobileJs + 
				customJs +
				//"<script type=\"text/javascript\">console.log(\"Hello World inline script\");alert(\"Hello World inline script\");</script>" +
				"</head><body>"+  title + 
				"<h2>Problem</h2>" +
				"<a href=\"#\" id=\"problem-hide\">Hide</a>" + 
				"<div id=\"case-study-problem\">" + caseStudy.getCase() + "</div>" + 
				"<h2>Analysis</h2>" + caseStudy.getAnalysis() +
				"</body></html>";
    }

	@Override
	protected void generateImagesPath() {
		/*
		 * Commenting for release
		if (TabbedActivity.isEmulator) {
			imagesPath = "file:///android_asset/images/case_study/" + mExperimentId + "/";
			//imagesPath = "file:///android_asset/";
		} else {
		*/
			//Log.i(MainActivity.TAG, "extPath: " + getActivity().getExternalFilesDir(null));
			//Log.i(MainActivity.TAG, "absPath: " + getActivity().getExternalFilesDir(null).getAbsolutePath().toString());
			imagesPath = "file:///" + 
					getActivity().getExternalFilesDir(null).getAbsolutePath() +
					"/images/case_study/" + mExperimentId + "/";
		/*
		 * Commenting for release
		}
		*/	
	}
}