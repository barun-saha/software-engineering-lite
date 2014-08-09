package me.barunsaha.software_engineering_lite.adapter;

import me.barunsaha.software_engineering_lite.CaseStudyFragment;
import me.barunsaha.software_engineering_lite.ReferenceFragment;
import me.barunsaha.software_engineering_lite.SelfEvaluationFragment;
import me.barunsaha.software_engineering_lite.TheoryFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	private int mExperimentId;
	private static final int nTabs = 4;
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    
    public TabsPagerAdapter(FragmentManager fm, int expId) {
        super(fm);
        mExperimentId = expId;
    }
 
    @Override
    public Fragment getItem(int index) {

        switch (index) {
        case 0:
            // Top Rated fragment activity
            //return new TheoryFragment();
        	return TheoryFragment.getInstance(mExperimentId);
        case 1:
            // Games fragment activity
            return SelfEvaluationFragment.getInstance(mExperimentId);
        case 2:
            // Movies fragment activity
            return CaseStudyFragment.getInstance(mExperimentId);
        case 3:
        	return ReferenceFragment.getInstance(mExperimentId);
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return nTabs;
    }
 
}