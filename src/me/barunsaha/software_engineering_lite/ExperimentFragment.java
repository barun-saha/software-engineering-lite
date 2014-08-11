package me.barunsaha.software_engineering_lite;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.webkit.WebView;

public abstract class ExperimentFragment extends Fragment {

	protected int mExperimentId;
	protected String mContent;
	
	// These paths are relative to imagesPath to be defined by individual
	// fragments. The directory tree is currently has same levels so that
	// ../../../ point to the same root from everywhere.
	protected static String styleSheet = 
			//"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/isad_app.css\" />";
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/isad_app.css\" />";
	protected static String jqueryStyleSheet = 
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../lib/jquery_mobile/jquery.mobile-1.4.3.min.css\" />";
	protected static String jqueryJs =
			"<script type=\"text/javascript\" src=\"../../../lib/jquery_mobile/jquery-1.11.1.min.js\"></script>";
	protected static String jqueryMobileJs =
			"<script type=\"text/javascript\" src=\"lib/jquery_mobile/jquery.mobile-1.4.3.min.js\"></script>";
	protected static String customJs =
			"<script type=\"text/javascript\" src=\"../../../js/custom.js\"></script>";
			//Not working "<script type=\"text/javascript\" src=\"js/custom.js\"></script>";

	protected static String imagesPath = "";
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onStart() {
		super.onStart();

		WebView wview = getWebView();
		//wview.getSettings().setJavaScriptEnabled(true);
		
		generateImagesPath();
		
        if (wview != null) {
        	//tview.setText("Hello! " + mExperimentId);
        	loadContent(wview);
        	display(wview);
        }
	}
	
	protected abstract WebView getWebView();
	
	protected abstract void generateImagesPath();
	
	protected abstract void loadContent(WebView wView);
	
	protected void display(WebView wView) {
		//String imagesPath;
		
		//Log.i(MainActivity.TAG, "emulator: " + TabbedActivity.isEmulator);		
		//Log.i(MainActivity.TAG, "loadData imagesPath: " + imagesPath);
		
		//Toast.makeText(getApplicationContext(), imagesPath, 
			//	Toast.LENGTH_LONG)
				//.show();
		
		wView.loadDataWithBaseURL(imagesPath, 
				mContent, "text/html", "utf-8", null);
	}

}