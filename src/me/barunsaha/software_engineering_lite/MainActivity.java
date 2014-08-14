package me.barunsaha.software_engineering_lite;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;


public class MainActivity extends ActionBarActivity {

	private ArrayList<Experiment> mExperiments = new ArrayList<Experiment>();
	private ExperimentAdapter mAdapter;
	private ShareActionProvider mShareActionProvider;
	private ListView mList;
	
	private static final String FIRST_RUN = "firstRun";
	private final static String SHARE_MSG = "Have you used Software Engineering Lite yet? Download and give it a try today! https://play.google.com/store/apps/details?id=";

	// Package-wise TAG for Log
	protected static final String TAG = "SE-Lite";
	
	public final static String S_EXPERIMENT_TITLE = "experimentTitle";
	public final static String S_EXPERIMENT_ID = "experimentID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the set of experiments
		String[] experimentTitles = getResources().getStringArray(
				R.array.experiments);		
		for (int i = 0; i < experimentTitles.length; i++) {
			this.mExperiments.add(new Experiment(i + 1, experimentTitles[i]));
		}

		// Instantiate the Adapter class
		mAdapter = new ExperimentAdapter(this, R.layout.list_item, mExperiments);
		
		// Create the list view, set the adapter and listener
		mList = (ListView) findViewById(R.id.list_experiments);
		mList.setAdapter(mAdapter);
		
		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> arg0, View view,
                    int position, long id) {
				
				Experiment item = (Experiment) mList.getItemAtPosition(position);
				
				// Launching new Activity on selecting single List Item
				Intent intent = new Intent(getApplicationContext(),
						TabbedActivity.class);

				intent = new Intent(getApplicationContext(), TabbedActivity.class);
				// Send data to the new activity
				intent.putExtra(S_EXPERIMENT_TITLE, item.getTitle());
				intent.putExtra(S_EXPERIMENT_ID, item.getId());
				// Log.i("SE", eid);

				startActivity(intent);
            }
		}); // End setOnItemClickListener()

		// First run activities
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.action_bar_share_menu, menu);
	    MenuItem item = menu.findItem(R.id.menu_item_share);
	    
	    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
	    MenuItemCompat.setActionProvider(item, mShareActionProvider);		
		prepareShareAction();
		
		menu.add(0, 0, 0, "About");
		
	    return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			AboutDialog about = new AboutDialog(this);
			about.setTitle("About");
			about.show();
			break;

		}
		return super.onOptionsItemSelected(item);

	}
	
	private void prepareShareAction(){
	    // Create the share Intent
	    Intent myIntent = new Intent(Intent.ACTION_SEND);
	    myIntent.putExtra(Intent.EXTRA_TEXT, SHARE_MSG + getPackageName());
	    myIntent.setType("text/plain");
	    
	    // Set the share Intent
	    if (mShareActionProvider != null) {
	    	mShareActionProvider.setShareIntent(myIntent);
	    }
	}
}