package pstc.employeemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chandrakant on 28-07-2016.
 */
public class TableControllerDB extends DbHandlerClass {
    public TableControllerDB(Context context) {
        super(context);
    }

    public boolean create(Employee e) {

        ContentValues values = new ContentValues();

        values.put(Constants.COULUMN_NAME, e.name);
        values.put(Constants.COULUMN_DOJ, e.doj);
        values.put(Constants.COULUMN_STAR, e.getPerformance().performance_rating);
        values.put(Constants.COULUMN_PAY, e.getPerformance().pay_package);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean success = db.insert(Constants.TABLE_NAME, null, values) > 0;
        db.close();

        return success;
    }

    public boolean update(Employee e) {

        ContentValues values = new ContentValues();

        values.put(Constants.COULUMN_NAME, e.name);
        values.put(Constants.COULUMN_DOJ, e.doj);
        values.put(Constants.COULUMN_STAR, e.getPerformance().performance_rating);
        values.put(Constants.COULUMN_PAY, e.getPerformance().pay_package);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(e.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(Constants.TABLE_NAME, values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }


    public boolean delete(int id) {
        boolean success = false;

        SQLiteDatabase db = this.getWritableDatabase();
        success = db.delete(Constants.TABLE_NAME, "id ='" + id + "'", null) > 0;
        db.close();

        return success;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM "+Constants.TABLE_NAME;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public Employee readOne(String id){
        String sql = "SELECT * FROM "+ Constants.TABLE_NAME + " where id = "+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {

                int _id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COULUMN_ID)));
                String empName = cursor.getString(cursor.getColumnIndex(Constants.COULUMN_NAME));
                String empDOJ = cursor.getString(cursor.getColumnIndex(Constants.COULUMN_DOJ));
                int empStar = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COULUMN_STAR)));
                int empPay = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COULUMN_PAY)));

                Employee e = new Employee(empName,empDOJ,empPay);
                e.getPerformance().performance_rating = empStar;
                e.id = _id;

                return e;

            }
        cursor.close();
        db.close();

        return null;
    }


    public List<Employee> read(int start,int length) {
        Log.v("tag1",Integer.toString(start)+", "+Integer.toString(length));

        String sql = "SELECT * FROM "+ Constants.TABLE_NAME + " limit "+start+", "+length;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Employee> queryList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                int _id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COULUMN_ID)));
                String empName = cursor.getString(cursor.getColumnIndex(Constants.COULUMN_NAME));
                String empDOJ = cursor.getString(cursor.getColumnIndex(Constants.COULUMN_DOJ));
                int empStar = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COULUMN_STAR)));
                int empPay = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COULUMN_PAY)));

                Employee e = new Employee(empName,empDOJ,empPay);
                e.getPerformance().performance_rating = empStar;
                e.id = _id;

                queryList.add(e);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return queryList;
    }
}
