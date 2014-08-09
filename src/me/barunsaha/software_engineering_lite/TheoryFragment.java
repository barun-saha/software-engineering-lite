package me.barunsaha.software_engineering_lite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

public class TheoryFragment extends ExperimentFragment {
	
	public static TheoryFragment getInstance(int experimentId) {
		TheoryFragment f = new TheoryFragment();
		f.mExperimentId = experimentId;
		return f;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theory, container, false);
         
        return rootView;
    }
	
	protected WebView getWebView() {
		return (WebView) getView().findViewById(R.id.wv_theory);
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
        
		//Experiment experiment = dbHelper.getExperiment(mExperimentId);
        TheoryContent theory = dbHelper.getTheory(mExperimentId);
		//String title = "<h1>" + experiment.getTitle() + "</h1>";
        String title = "<h1>" + theory.getTitle() + "</h1>";
		//String style = "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/isad_app.css\" />";
		mContent = "<html><head>" + styleSheet + 
				"</head> <body>"+  title + theory.getContent() + "</body></html>";
    }

	@Override
	protected void generateImagesPath() {
		if (TabbedActivity.isEmulator) {
			imagesPath = "file:///android_asset/images/theory/" + mExperimentId + "/";
			//imagesPath = "file:///android_asset/";
		} else {
			//Log.i(MainActivity.TAG, "extPath: " + getActivity().getExternalFilesDir(null));
			//Log.i(MainActivity.TAG, "absPath: " + getActivity().getExternalFilesDir(null).getAbsolutePath().toString());
			imagesPath = "file:///" + 
					getActivity().getExternalFilesDir(null).getAbsolutePath() +
					"/images/theory/" + mExperimentId + "/";
		}	
		
	}
}
