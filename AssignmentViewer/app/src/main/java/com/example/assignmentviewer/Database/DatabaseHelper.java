package com.example.assignmentviewer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.assignmentviewer.Models.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private Context context;
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context){
        super(context, Config.DATABASE_NAME, null,Config.DATABASE_VERSION );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Course Table QL
        String CREATE_TABLE_COURSE = "CREATE TABLE "+ Config.COURSE_TABLE_NAME +
                " (" +
                Config.COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Config.COLUMN_COURSE_TITLE + " TEXT NOT NULL,"+
                Config.COLUMN_COURSE_CODE + " TEXT NOT NULL)";

        //Create Assignment Table QL
        String CREATE_TABLE_ASS = "CREATE TABLE "+ Config.ASS_TABLE_NAME +
                " (" +
                Config.COLUMN_ASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Config.COLUMN_COURSE_ID + " INTEGER NOT NULL, "+
                Config.COLUMN_ASS_TITLE + " TEXT NOT NULL,"+
                Config.COLUMN_ASS_GRADE + " REAL NOT NULL)";


        Log.d(TAG, CREATE_TABLE_COURSE);
        Log.d(TAG, CREATE_TABLE_ASS);

        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_ASS);

        Log.d(TAG, "db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertCourse(Course course){

        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Config.COLUMN_COURSE_TITLE, course.getTitle());
        contentValues.put(Config.COLUMN_COURSE_CODE, course.getCode());

        try {
            id = db.insertOrThrow(Config.COURSE_TABLE_NAME, null, contentValues);
        }
        catch (SQLException e){
            Log.d(TAG, "Exception: " + e);
            Toast.makeText(context, "Operation Failed! " +e, Toast.LENGTH_LONG).show();
        }
        finally {
            db.close();
        }
        return id;
    }

    public List<Course> getAllCourses(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        try{
            cursor = db.query(Config.COURSE_TABLE_NAME, null, null, null, null, null, null);

            if(cursor != null){
                if(cursor.moveToFirst()){
                    List<Course> courses = new ArrayList<>();

                    do{

                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_COURSE_ID));
                        String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_TITLE));
                        String code = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_CODE));

                        courses.add(new Course(id, title, code));

                    }while(cursor.moveToNext());

                    return courses;
                }
            }
        }
        catch (SQLException e){
            Log.d(TAG, "Exception: " + e);
            Toast.makeText(context, "Operation Failed! " +e, Toast.LENGTH_LONG).show();
        }
        finally {
            if(cursor != null)
                    cursor.close();

            db.close();

        }

        return Collections.emptyList();

    }

    public Course getCourseByCode(String codeCourse) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Config.COURSE_TABLE_NAME, null, Config.COLUMN_COURSE_CODE + " = ?", new String[]{codeCourse}, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_COURSE_ID));
                    String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_TITLE));
                    String code = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_CODE));

                    return new Course(id, title, code);
                }
            }

        } catch (SQLException e) {
            Log.d(TAG, "Exception: " + e);
            Toast.makeText(context, "Operation Failed! " + e, Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null)
                cursor.close();

            db.close();

        }
        return null;
    }

}
