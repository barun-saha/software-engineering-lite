package me.barunsaha.software_engineering_lite;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	protected static String DB_PATH = null;
			//"/data/data/me.barunsaha.software_engineering_lite/databases";

	protected static String DB_NAME = "isad.sqlite";
	private SQLiteDatabase mDataBase;
	private final Context mContext;

	/**
	 * Constructor takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.mContext = context;
		
		if (DB_PATH == null) {
			//DB_PATH = context.getFilesDir().getPath();
			DB_PATH = context.getDatabasePath(DB_NAME).toString();
			//Log.i(MainActivity.TAG, "DB_PATH " + DB_PATH);
		}
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		boolean dbExists = checkDataBase();

		if (dbExists) {
			// do nothing - database already exist
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
			
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;

		try {
			//String myPath = DB_PATH + "/" + DB_NAME;
			//checkDB = SQLiteDatabase.openDatabase(myPath, null,
			checkDB = SQLiteDatabase.openDatabase(DB_PATH, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
			//Log.i(MainActivity.TAG, "Database exists!");
		}

		return checkDB != null ? true : false;
	}

	public void openDataBase() throws SQLException {
		// Open the database
		//String myPath = DB_PATH + "/" + DB_NAME;
		//mDataBase = SQLiteDatabase.openDatabase(myPath, null,
		mDataBase = SQLiteDatabase.openDatabase(DB_PATH, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if (mDataBase != null)
			mDataBase.close();

		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

    // Getting an experiment
	public Experiment getExperiment(int id) {
		//Log.i(MainActivity.TAG, DB_PATH);
		
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    //Log.i(MainActivity.TAG, "" + db);
	    
	    Cursor cursor = db.query("isad_theory", new String[] { "_id", "title", "content" }, "_id=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Experiment experiment = new Experiment(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2));
	    
	    return experiment;
	}
	
	// Get single theory
	public TheoryContent getTheory(int id) {
		//Log.i(MainActivity.TAG, DB_PATH);
		
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    //Log.i(MainActivity.TAG, "" + db);
	    
	    Cursor cursor = db.query("isad_theory", new String[] { "_id", "title", "content" }, "_id=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    TheoryContent theory = new TheoryContent(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2));
	    
	    return theory;
	}
	
	// Get a case study
	public CaseStudyContent getCaseStudy(int id) {
		
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    //Log.i(MainActivity.TAG, "" + db);
	    
	    Cursor cursor = db.query("isad_casestudy", 
	    		new String[] { "_id", "title", "problem", "analysis" }, 
	    		"theory_id=?",
	            new String[] { String.valueOf(id) }, 
	            null, null, null, null);
	    Log.i(MainActivity.TAG, cursor.toString() + " " + id);
	    
	    if (cursor != null) {
	        boolean status = cursor.moveToFirst();
	        Log.i(MainActivity.TAG, "Cursor status: " + status);
	    }
	 
	    CaseStudyContent caseStudy = new CaseStudyContent(id,
	            cursor.getString(1), cursor.getString(2), cursor.getString(3));
	    
	    return caseStudy;
	}
	
	// Get a self-eval Q&A (all) for an experiment
	public ArrayList<SelfEvaluationContent> getSelfEvaluations(int id) {
		
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    //Log.i(MainActivity.TAG, "" + db);
	    
	    Cursor cursor = db.query("isad_selfevaluation", 
	    		new String[] {"question_num", "question", "option1", 
	    			"option2", "option3", "option4", "answer"}, 
	    		"theory_id=?",
	            new String[] { String.valueOf(id) }, 
	            null, null, 
	            "question_num ASC", null);
	    Log.i(MainActivity.TAG, cursor.toString() + " " + id);
	    
//		    if (cursor != null) {
//		        boolean status = cursor.moveToFirst();
//		        Log.i(MainActivity.TAG, "Cursor status: " + status);
//		    }
	 
	    ArrayList<SelfEvaluationContent> all = new 
	    		ArrayList<SelfEvaluationContent>(); 
	    
	    while (cursor.moveToNext()) {
	    	SelfEvaluationContent selfEvaluation = 
	    			new SelfEvaluationContent(id,
		    		cursor.getInt(0), cursor.getString(1), 
		    		cursor.getString(2), cursor.getString(3), 
		    		cursor.getString(4), cursor.getString(5), 
		    		cursor.getInt(6));
		    
		    all.add(selfEvaluation);
	    }
	    
	    return all;
	}
	
	// Get all references for an experiment
	public ArrayList<ReferenceContent> getReferences(int id) {
		
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    //Log.i(MainActivity.TAG, "" + db);
	    
	    ArrayList<ReferenceContent> all = new 
	    		ArrayList<ReferenceContent>();
	    
	    // Web references
	    Cursor cursor = db.query("isad_reference", 
	    		new String[] {"url", "url_desc"}, 
	    		"theory_id=? and book_id is null",
	            new String[] {String.valueOf(id)}, 
	            null, null, 
	            "_id ASC", null);
	    //Log.i(MainActivity.TAG, cursor.toString() + " " + id);	    
	    
	    while (cursor.moveToNext()) {
	    	ReferenceContent reference = 
	    			new ReferenceContent(id,
		    		cursor.getString(0), 
		    		cursor.getString(1), null, null, null, null);
		    
		    all.add(reference);
	    }
	    
	    // Books
	    // SQL join: http://martin.cubeactive.com/android-creating-a-join-with-sqlite/
	    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	    qb.setTables("isad_reference join isad_book on isad_reference.book_id = isad_book._id");
	    cursor = qb.query(db, 
	    		new String[] {"title", "author", "publisher", "edition"}, 
	    		"theory_id=?", 
	    		new String[] {String.valueOf(id)}, 
	    		null, null, 
	    		"isad_book._id desc");
	    
	    while (cursor.moveToNext()) {
	    	ReferenceContent reference = 
	    			new ReferenceContent(id, null, null,
		    		cursor.getString(0), cursor.getString(1), 
		    		cursor.getString(2), cursor.getString(3));
		    
		    all.add(reference);
	    }
	    Log.i(MainActivity.TAG, all.toString());
	    
	    return all;
	}
	
	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transferring byte stream.
	 * */
	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = mContext.getAssets().open(DB_NAME);
		// Path to the just created empty db
		//String outFileName = DB_PATH + "/" + DB_NAME;
		String outFileName = mContext.getDatabasePath(DB_NAME).toString();

		Log.i(MainActivity.TAG, "" + myInput + " " + outFileName);
	    
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the input file to the output file
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

}