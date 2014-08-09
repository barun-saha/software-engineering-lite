package me.barunsaha.software_engineering_lite;

import java.util.ArrayList;

import me.barunsaha.software_engineering_lite.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExperimentAdapter extends ArrayAdapter<Experiment> {

	private ArrayList<Experiment> experiments;
	
	/* here we must override the constructor for ArrayAdapter
	* the only variable we care about now is ArrayList<Item> objects,
	* because it is the list of objects we want to display.
	*/
	public ExperimentAdapter(Context context, int textViewResourceId, 
			ArrayList<Experiment> experiments) {
		super(context, textViewResourceId, experiments);
		this.experiments = experiments;
	}
	
	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
	    if (v == null) {
	        LayoutInflater vi;
	        vi = LayoutInflater.from(getContext());
	        v = vi.inflate(R.layout.list_item, null);
	    }
	    
	    Experiment e = this.experiments.get(position);
	    
	    if (e != null) {
	    	TextView id = (TextView) v.findViewById(R.id.experiment_id);
	    	TextView title = (TextView) v.findViewById(R.id.experiment_title);
	    	
	    	if (id != null) {
	    		//id.setTypeface(Typeface.MONOSPACE);
	    		id.setText("" + String.format("%2s", e.getId()) + ".");
	    	}
	    	
	    	if (title != null) {
	    		title.setText(e.getTitle());
	    	}
	    }

	    return v;
	}

}