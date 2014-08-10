package me.barunsaha.software_engineering_lite;

import java.io.IOException;

import me.barunsaha.software_engineering_lite.adapter.TabsPagerAdapter;
import me.barunsaha.software_engineering_lite.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

public class TabbedActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private int mExperimentId;
	
	public static boolean isEmulator;
	private static final String S_EMULATOR = "generic";

	static {
		Log.i(MainActivity.TAG, Build.BRAND);
		if (Build.BRAND.length() >= S_EMULATOR.length() && 
				Build.BRAND.substring(0, S_EMULATOR.length())
				.equalsIgnoreCase(S_EMULATOR)) {
			isEmulator = true;
		} else {
			isEmulator = false;
		}
	}
	
	private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar mActionBar;
    private DataBaseHelper mDbHelper;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
 
        mExperimentId = getIntent().getIntExtra(MainActivity.S_EXPERIMENT_ID, 1);
        
        setTitle("" + mExperimentId + ". " + 
        		getResources().getStringArray(
				R.array.experiments_short)[mExperimentId-1]);
        
        // Initilization
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), mExperimentId);
 
        assert mViewPager != null : "viewPager is null";
        Log.d("SE", "" + mViewPager + " " +  mAdapter);
        
        mViewPager.setAdapter(mAdapter);
        
        mActionBar = getActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        // Tab titles
        final String[] tabs = getResources().getStringArray(
    			R.array.tabs);
        //{ "Theory", "Self Evaluation", "Case Study", "References" };
        for (String tab_name : tabs) {
            mActionBar.addTab(mActionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
 
        /**
         * on swiping the viewpager make respective tab selected
         * */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page make respected tab selected
                //mActionBar.setSelectedNavigationItem(position);
                //TextView tview = (TextView) findViewById(R.id.tv_theory);
                //if (tview != null) {
                	//tview.setText("2Hello!");
                //}
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // Create the database if it doesn't already exist
        try {
			mDbHelper = new DataBaseHelper(getApplicationContext());
			mDbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), 
					"An exception occured: " + e, Toast.LENGTH_LONG)
					.show();
		}
    }
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        mViewPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    /*protected void loadContent() {        
		Experiment experiment = mDbHelper.getExperiment(mExperimentId);
		String title = "<h1>" + experiment.getTitle() + "</h1>";
		String style = "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../css/isad_app.css\" />";
		String contents = "<html><head>" + style + 
				//"</head> <body> <h1>H1</h1> <h2>H2</h2> </body></html>";
				"</head> <body>" +  title + experiment.getContent() + "</body></html>";
		
		////WebView webView = (WebView) findViewById(R.id.webview);
		//summary = "<html><head></head><body> Image <img src=\"use_case_1.png\"> </body></html>";
		//webView.loadData(summary, "text/html", null);
		
		String imagesPath;
		
		Log.i(MainActivity.TAG, "emulator: " + isEmulator);
		
		if (isEmulator) {
			imagesPath = "file:///android_asset/images/theory/" + mExperimentId + "/";
		} else {
			imagesPath = "file:///" + 
					getExternalFilesDir(null).getAbsolutePath() + 
					"/images/theory/" + mExperimentId + "/";
		}
		
		Log.i(MainActivity.TAG, imagesPath);
		//Toast.makeText(getApplicationContext(), imagesPath, 
			//	Toast.LENGTH_LONG)
				//.show();
		
		//webView.loadDataWithBaseURL(imagesPath, 
			//	contents, "text/html", "utf-8", null);
    }*/
}
