package me.barunsaha.software_engineering_lite;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ShareActionProvider;


public class MainActivity extends ListActivity {
	
	public final static String S_EXPERIMENT_TITLE = "experimentTitle";
	public final static String S_EXPERIMENT_ID = "experimentID";

	// declare class variables
	private ArrayList<Experiment> mExperiments = new ArrayList<Experiment>();
	private ExperimentAdapter mAdapter;

	// Package-wise access
	protected static final String TAG = "SE-Lite";
	//protected static final String S_STORAGE_PATH = "SE-Lite";
	
	private static final String FIRST_RUN = "firstRun";
	private final static String SHARE_MSG = "Have you used Software Engineering Lite yet? Download and give it a try today! https://play.google.com/store/apps/details?id=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String[] experimentTitles = getResources().getStringArray(
				R.array.experiments);

		for (int i = 0; i < experimentTitles.length; i++) {
			this.mExperiments.add(new Experiment(i + 1, experimentTitles[i]));
		}

		// instantiate our ItemAdapter class
		mAdapter = new ExperimentAdapter(this, R.layout.list_item, mExperiments);
		setListAdapter(mAdapter);

		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		if (sharedPref.getBoolean(FIRST_RUN, true)) {
			// Copy assets only the first time
			// Don't pass getApplicationContext() as context here -- affects
			// the AsyncTask
			new CopyAssetsTask(this)
				.execute("css", "images", "lib", "js");
			
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(FIRST_RUN, false);
			editor.commit();
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Experiment item = (Experiment) getListAdapter().getItem(position);
		
		// Launching new Activity on selecting single List Item
		Intent intent = new Intent(getApplicationContext(),
		// DisplayExperimentActivity.class);
				TabbedActivity.class);

		intent = new Intent(getApplicationContext(), TabbedActivity.class);
		// sending data to new activity
		intent.putExtra(S_EXPERIMENT_TITLE, item.getTitle());
		intent.putExtra(S_EXPERIMENT_ID, item.getId());
		// Log.i("SE", eid);

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.action_bar_share_menu, menu);
	    MenuItem item = menu.findItem(R.id.menu_item_share);
		//getMenuInflater().inflate(R.menu.main, menu);
	    //ShareActionProvider myShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
	    ShareActionProvider myShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    Intent myIntent = new Intent(Intent.ACTION_SEND);
	    myIntent.putExtra(Intent.EXTRA_TEXT, SHARE_MSG + getPackageName());
	    myIntent.setType("text/plain");
	    myShareActionProvider.setShareIntent(myIntent);
	    
		menu.add(0, 0, 0, "About");

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			//AboutDialog about = new AboutDialog(this, android.R.style.Theme);
			AboutDialog about = new AboutDialog(this);
			about.setTitle("About");
			//about.getWindow().setFlags(LayoutParams., LayoutParams.MATCH_PARENT);

			about.show();
			break;

		}
		return true;

	}
}