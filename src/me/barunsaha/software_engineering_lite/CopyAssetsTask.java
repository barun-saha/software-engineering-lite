package me.barunsaha.software_engineering_lite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

public class CopyAssetsTask extends AsyncTask<String, Integer, Void> {
	
	private Context mContext;
	private ProgressDialog mProgressDialog;
	
	public CopyAssetsTask(Context context) {
		super();
		mContext = context;
	}
	
	@Override
	protected Void doInBackground(String... directories) {
	    int nDirectories = directories.length;
	    
	    for (int i = 1; i <= nDirectories; i++) {
	    	copyAssets(directories[i-1]);
	    	// +1 because of the database creation later
            publishProgress((int) ((i / (float) (nDirectories + 1)) * 100));
            // Escape early if cancel() is called
            if (isCancelled()) break;
	    }
	    
	    try {
	    	if (isCancelled()) return null;

			new DataBaseHelper(mContext).createDataBase();
			publishProgress(100);
			//Log.i(MainActivity.TAG, "Db copied from task");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		return null;
	    
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Copying resource files ...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
	}
	

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	    mProgressDialog.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if (mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	// Source: http://www.twodee.org/blog/?p=4518
	/**
	 * Copy the asset at the specified path to this app's data directory. If the
	 * asset is a directory, its contents are also copied.
	 * 
	 * @param path
	 *            Path to asset, relative to app's assets directory.
	 */
	private void copyAssets(String path) {
		AssetManager manager = mContext.getAssets();

		// If we have a directory, we make it and recurse. If a file, we copy
		// its
		// contents.
		try {
			String[] contents = manager.list(path);

			// The documentation suggests that list throws an IOException, but
			// doesn't
			// say under what conditions. It'd be nice if it did so when the
			// path was
			// to a file. That doesn't appear to be the case. If the returned
			// array is
			// null or has 0 length, we assume the path is to a file. This means
			// empty
			// directories will get turned into files.
			if (contents == null || contents.length == 0)
				throw new IOException();

			// Make the directory.
			File dir = new File(mContext.getExternalFilesDir(null), path);
			Log.i(MainActivity.TAG, "Creating directories " + dir.getAbsolutePath());
			dir.mkdirs();

			// Recurse on the contents.
			for (String entry : contents) {
				copyAssets(path + "/" + entry);
			}
		} catch (IOException e) {
			copyFileAsset(path);
		}
	}

	/**
	 * Copy the asset file specified by path to app's data directory. Assumes
	 * parent directories have already been created.
	 * 
	 * @param path
	 *            Path to asset, relative to app's assets directory.
	 */
	private void copyFileAsset(String path) {
		File file = new File(mContext.getExternalFilesDir(null), path);

		try {
			InputStream in = mContext.getAssets().open(path);
			OutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int read = in.read(buffer);

			while (read != -1) {
				out.write(buffer, 0, read);
				read = in.read(buffer);
			}

			out.close();
			in.close();
		} catch (IOException e) {
			Log.i(MainActivity.TAG, e.toString());
		}
	}
	/*
	*//**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transferring byte stream.
	 * *//*
	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = mContext.getAssets().open(DataBaseHelper.DB_NAME);
		// Path to the just created empty db
		//String outFileName = DB_PATH + "/" + DB_NAME;
		String outFileName = mContext.getDatabasePath(DataBaseHelper.DB_NAME).toString();

		Log.i(MainActivity.TAG, "" + myInput + " " + outFileName);
		
	    // check if databases folder exists, if not create one and its subfolders
		File databaseFile = new File(outFileName);
	    if (!databaseFile.exists()){
	        databaseFile.mkdir();
	    }
	    
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}*/

}
