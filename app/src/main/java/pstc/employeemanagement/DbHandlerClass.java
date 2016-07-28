package pstc.employeemanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Chandrakant on 28-07-2016.
 */
public class DbHandlerClass extends SQLiteOpenHelper {


    public DbHandlerClass(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+Constants.TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constants.COULUMN_NAME + " TEXT NOT NULL, " +
                Constants.COULUMN_DOJ + " TEXT NOT NULL, " +
                Constants.COULUMN_STAR + " INTEGER NOT NULL, " +
                Constants.COULUMN_PAY + " INTEGER NOT NULL) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
        db.execSQL(sql);
        Log.v("tag1",oldVersion+"->"+newVersion);

        onCreate(db);
    }
}
