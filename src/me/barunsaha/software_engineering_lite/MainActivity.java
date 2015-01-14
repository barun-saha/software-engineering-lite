package me.barunsaha.software_engineering_lite;

import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.format.Time;


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
			
			// A bad hack to wait until the database is copied so that a
			// greetings message, if any, can be displayed
			//while (! aTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
				// Do nothing
			//}
			try {
				new DataBaseHelper(this).createDataBase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*else {
			SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
			if (db != null) {
				db.close();
			}
		}
		*/
		
		// Show greetings, if any available
		//Log.i(MainActivity.TAG, "To show greetings ...");
		showGreetings();
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
	
	private void showGreetings() {
		// Get current date
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int day = today.monthDay;
		int month = today.month + 1;
		int year = today.year;
		
		// Get event, if any, for today
		// Create the database if it doesn't already exist
		DataBaseHelper dbHelper = null;
        try {
			dbHelper = new DataBaseHelper(getApplicationContext());
			dbHelper.createDataBase();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), 
					"An exception occured: " + e, Toast.LENGTH_LONG)
					.show();
		}
        
        String[] event = dbHelper.getEvent(day, month, year);
        String message = "None";
        String id;
        
        if (event[0] != null) {
        	message = event[1];
        	id = event[0];
        
        	// Increment the year by 1 and update the database 
    		// so that it is shown again in the next year
        	year += 1;
        	dbHelper.updateEvent(id, year);
        	
        	// Display the greetings		
    		new AlertDialog.Builder(this)
    	    	.setMessage(message)
    	    	.setTitle(R.string.greetings)
    	    	.setCancelable(true)
    	    	.setNeutralButton(android.R.string.ok,
    	    		new DialogInterface.OnClickListener() {
    	    			public void onClick(DialogInterface dialog, 
    	    					int whichButton) {}
    	         	})
    	        .show();
        }
	}
}