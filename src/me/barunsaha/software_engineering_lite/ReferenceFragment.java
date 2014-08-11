package me.barunsaha.software_engineering_lite;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

public class ReferenceFragment extends ExperimentFragment {

	public static ReferenceFragment getInstance(int experimentId) {
		ReferenceFragment f = new ReferenceFragment();
		f.mExperimentId = experimentId;
		
		return f;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_references, container, false);
         
        return rootView;
    }

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected WebView getWebView() {
		WebView wview = (WebView) getView().findViewById(R.id.wv_references);
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
        
        ArrayList<ReferenceContent> allReferences = 
        		dbHelper.getReferences(mExperimentId);
        String books = "<ol>";
        String urls = "<ol type=\"i\">";
        
        for (ReferenceContent reference : allReferences) {
        	String url = reference.getUrl();
        	//Log.i(MainActivity.TAG, "" + reference);
        	
        	if (url != null) {
        	//if (! url.equalsIgnoreCase("x")) {
        		urls += "<li><a href=\"" + url + "\">" + 
            			reference.getUrlDescription() + "</a></li>";
        	} else {
        	
        		books += "<li>" +
        				reference.getTitle() + ", " + reference.getAuthor() +
            			", " + reference.getPublisher() + ", " +
            			reference.getEdition() + "</li>";
        	}
        }
        urls += "</ol>";
        books += "</ol>";
        
        //String title = "<h1>" + caseStudy.getTitle() + "</h1>";
		mContent = "<html><head>" + 
				styleSheet + jqueryStyleSheet + 
				jqueryJs + jqueryMobileJs + 
				customJs +
				//"<script type=\"text/javascript\">console.log(\"Hello World inline script\");alert(\"Hello World inline script\");</script>" +
				"</head><body>"+ 
				"<h2>Bibliography</h2>" + books +
				"<h2>Webliography</h2>" + urls +
				"</body></html>";
    }

	@Override
	protected void generateImagesPath() {
		/*
		 * Commenting for release
		 * if (TabbedActivity.isEmulator) {
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
		 * }
		 */
	}
}