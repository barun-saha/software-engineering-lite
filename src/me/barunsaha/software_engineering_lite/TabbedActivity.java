package me.barunsaha.software_engineering_lite;

import me.barunsaha.software_engineering_lite.adapter.TabsPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class TabbedActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private int mExperimentId;
	
	public static boolean isEmulator;
	private static final String S_EMULATOR = "generic";

	static {
		//Log.i(MainActivity.TAG, Build.BRAND);
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
    //private DataBaseHelper mDbHelper;
 
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
        //Log.d("SE", "" + mViewPager + " " +  mAdapter);
        
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
                // On changing the page make respected tab selected
                mActionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
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
}
