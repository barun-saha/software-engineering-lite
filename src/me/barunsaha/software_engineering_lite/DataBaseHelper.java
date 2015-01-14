package me.barunsaha.software_engineering_lite;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	protected static String DB_PATH = null;
	// "/data/data/me.barunsaha.software_engineering_lite/databases";

	private static final String DB_NAME = "isad.sqlite";
	/** To be incremented every time there is any change in the database */
	private static final int DB_VERSION = 5;

	private SQLiteDatabase mDataBase;
	private final Context mContext;

	// This is an indicator if we need to copy the
	// database file.
	// http://blog.kdehairy.com/using-a-preloaded-sqlite-database-with-sqliteopenhelper/
	private boolean mInvalidDatabaseFile = false;
	private boolean mIsUpgraded = false;

	/**
	 * Constructor takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.mContext = context;

		if (DB_PATH == null) {
			// DB_PATH = context.getFilesDir().getPath();
			DB_PATH = context.getDatabasePath(DB_NAME).toString();
			// Log.i(MainActivity.TAG, "DB_PATH " + DB_PATH);
		}
		//Log.i(MainActivity.TAG, "DB_PATH " + DB_PATH);

		SQLiteDatabase db = null;
		try {
			db = getReadableDatabase();
			
			if (db != null) {
				db.close();
			}
			
			//DATABASE_FILE = context.getDatabasePath(DATABASE_NAME);
			if (mInvalidDatabaseFile || mIsUpgraded) {
				try {
					copyDataBase();
				} catch (IOException e) {
					throw new Error("Error copying database");
				}
			}
			/*
			if (mIsUpgraded) {
				doUpgrade();
			}
			*/
		} catch (SQLiteException e) {
			throw new Error("Error in opening database from constructor!");
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}

	}

	/**
	 * Creates an empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		boolean dbExists = checkDataBase();

		if (dbExists) {
			// do nothing - database already exist
		} else {
			// By calling this method an empty database will be created at
			// the default system path of your application.
			// We are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}

		// onUpgrade(mDataBase, getDbVersionFromSystem(), DB_VERSION);
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
			// String myPath = DB_PATH + "/" + DB_NAME;
			// checkDB = SQLiteDatabase.openDatabase(myPath, null,
			checkDB = SQLiteDatabase.openDatabase(DB_PATH, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
			// Log.i(MainActivity.TAG, "Database exists!");
		}

		return checkDB != null ? true : false;
	}

	public void openDataBase() throws SQLException {
		// Open the database
		// String myPath = DB_PATH + "/" + DB_NAME;
		// mDataBase = SQLiteDatabase.openDatabase(myPath, null,
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
		mInvalidDatabaseFile = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Toast.makeText(mContext,
				"Upgrading database from version " + oldVersion + " to " + 
						newVersion,
				Toast.LENGTH_LONG).show();
		//Log.i(MainActivity.TAG, "Calling onUpgrade: " + oldVersion + " "
			//	+ newVersion);

		mInvalidDatabaseFile = true;
		mIsUpgraded = true;
		
		/*
		if (newVersion > oldVersion) {
			// Replace the old database with the new one
			try {
				Log.i(MainActivity.TAG, "Upgrading database ...");
				Toast.makeText(mContext, "Upgrading database ...",
						Toast.LENGTH_LONG).show();
				copyDataBase();
				Toast.makeText(mContext, "... done upgrading database!",
						Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				throw new Error("Error in upgrading the database!");
			}
		}
		*/
	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

	// Getting an experiment
	public Experiment getExperiment(int id) {
		// Log.i(MainActivity.TAG, DB_PATH);

		SQLiteDatabase db = this.getReadableDatabase();

		// Log.i(MainActivity.TAG, "" + db);

		Cursor cursor = db.query("isad_theory", new String[] { "_id", "title",
				"content" }, "_id=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Experiment experiment = new Experiment(Integer.parseInt(cursor
				.getString(0)), cursor.getString(1), cursor.getString(2));

		return experiment;
	}

	// Get single theory
	public TheoryContent getTheory(int id) {
		// Log.i(MainActivity.TAG, DB_PATH);

		SQLiteDatabase db = this.getReadableDatabase();

		// Log.i(MainActivity.TAG, "" + db);

		Cursor cursor = db.query("isad_theory", new String[] { "_id", "title",
				"content" }, "_id=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		TheoryContent theory = new TheoryContent(Integer.parseInt(cursor
				.getString(0)), cursor.getString(1), cursor.getString(2));

		return theory;
	}

	// Get a case study
	public CaseStudyContent getCaseStudy(int id) {

		SQLiteDatabase db = this.getReadableDatabase();

		// Log.i(MainActivity.TAG, "" + db);

		Cursor cursor = db.query("isad_casestudy", new String[] { "_id",
				"title", "problem", "analysis" }, "theory_id=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		// Log.i(MainActivity.TAG, cursor.toString() + " " + id);

		if (cursor != null) {
			boolean status = cursor.moveToFirst();
			// Log.i(MainActivity.TAG, "Cursor status: " + status);
		}

		CaseStudyContent caseStudy = new CaseStudyContent(id,
				cursor.getString(1), cursor.getString(2), cursor.getString(3));

		return caseStudy;
	}

	// Get a self-eval Q&A (all) for an experiment
	public ArrayList<SelfEvaluationContent> getSelfEvaluations(int id) {

		SQLiteDatabase db = this.getReadableDatabase();

		// Log.i(MainActivity.TAG, "" + db);

		Cursor cursor = db.query("isad_selfevaluation", new String[] {
				"question_num", "question", "option1", "option2", "option3",
				"option4", "answer" }, "theory_id=?",
				new String[] { String.valueOf(id) }, null, null,
				"question_num ASC", null);

		// if (cursor != null) {
		// boolean status = cursor.moveToFirst();
		// Log.i(MainActivity.TAG, "Cursor status: " + status);
		// }

		ArrayList<SelfEvaluationContent> all = new ArrayList<SelfEvaluationContent>();

		while (cursor.moveToNext()) {
			SelfEvaluationContent selfEvaluation = new SelfEvaluationContent(
					id, cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5), cursor.getInt(6));

			all.add(selfEvaluation);
		}

		return all;
	}

	// Get all references for an experiment
	public ArrayList<ReferenceContent> getReferences(int id) {

		SQLiteDatabase db = this.getReadableDatabase();

		// Log.i(MainActivity.TAG, "" + db);

		ArrayList<ReferenceContent> all = new ArrayList<ReferenceContent>();

		// Web references
		Cursor cursor = db.query("isad_reference", new String[] { "url",
				"url_desc" }, "theory_id=? and book_id is null",
				new String[] { String.valueOf(id) }, null, null, "_id ASC",
				null);
		// Log.i(MainActivity.TAG, cursor.toString() + " " + id);

		while (cursor.moveToNext()) {
			ReferenceContent reference = new ReferenceContent(id,
					cursor.getString(0), cursor.getString(1), null, null, null,
					null);

			all.add(reference);
		}

		// Books
		// SQL join:
		// http://martin.cubeactive.com/android-creating-a-join-with-sqlite/
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables("isad_reference join isad_book on isad_reference.book_id = isad_book._id");
		cursor = qb.query(db, new String[] { "title", "author", "publisher",
				"edition" }, "theory_id=?",
				new String[] { String.valueOf(id) }, null, null,
				"isad_reference._id ASC");

		while (cursor.moveToNext()) {
			ReferenceContent reference = new ReferenceContent(id, null, null,
					cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3));

			all.add(reference);
		}
		// Log.i(MainActivity.TAG, all.toString());

		return all;
	}

	/**
	 * Get an event, if any, matching today's date
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return {_id, event description}
	 */
	public String[] getEvent(int day, int month, int year) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query("greetings", new String[] { "_id",
				"e_description" }, "e_day=? AND e_month=? AND e_year=?",
				new String[] { String.valueOf(day), String.valueOf(month),
						String.valueOf(year) }, null, null, null, null);

		String[] retVal = new String[2];

		if (cursor != null && cursor.moveToNext()) {
			retVal[0] = cursor.getString(0);
			retVal[1] = cursor.getString(1);
		} else {
			retVal[0] = null;
		}

		return retVal;
	}

	public void updateEvent(String id, int year) {
		ContentValues cValues = new ContentValues();
		cValues.put("e_year", year);

		SQLiteDatabase db = this.getReadableDatabase();
		db.update("greetings", cValues, "_id=?", new String[] { id });
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
		// String outFileName = DB_PATH + "/" + DB_NAME;
		String outFileName = mContext.getDatabasePath(DB_NAME).toString();

		// Log.i(MainActivity.TAG, "" + myInput + " " + outFileName);

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