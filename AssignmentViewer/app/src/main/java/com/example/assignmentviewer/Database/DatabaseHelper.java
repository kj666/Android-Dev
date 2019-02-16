package com.example.assignmentviewer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.assignmentviewer.Models.Assignment;
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

    //Insert a course
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

    //Get All Courses
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

                        //Get average using Query
                        Double avg = getAverageByCourse(id);
                        boolean isEmpty = assignmentExists(id);

                        courses.add(new Course(id, title, code, avg, isEmpty));

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

    //Get course by ID
    public Course getCourseByID(int CourseID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Config.COURSE_TABLE_NAME, null, Config.COLUMN_COURSE_ID + " = ?", new String[]{CourseID+""}, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_COURSE_ID));
                    String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_TITLE));
                    String code = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_CODE));
                    Double avg = getAverageByCourse(id);
                    boolean isEmpty = assignmentExists(id);

                    return new Course(id, title, code, avg, isEmpty);
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

    //Get All Assignment By their course ID
    public List<Assignment> getAllAssignmentsByCourse(int CourseID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try{
            cursor = db.query(Config.ASS_TABLE_NAME, null, Config.COLUMN_COURSE_ID+" = ?", new String[]{CourseID+""}, null, null, null);

            if(cursor != null){
                if(cursor.moveToFirst()){
                    List<Assignment> assignments = new ArrayList<>();

                    do{
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ASS_ID));
                        int courseID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_COURSE_ID));
                        String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ASS_TITLE));
                        double grade = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_ASS_GRADE));

                        assignments.add(new Assignment(id, courseID, title, grade));

                    }while(cursor.moveToNext());

                    return assignments;
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

    //Insert an Assignment
    public long insertAssignment(Assignment assignment){

        long id = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Config.COLUMN_ASS_TITLE, assignment.getTitle());
        contentValues.put(Config.COLUMN_ASS_GRADE, assignment.getGrade());
        contentValues.put(Config.COLUMN_COURSE_ID, assignment.getCourseID());

        try {
            id = db.insertOrThrow(Config.ASS_TABLE_NAME, null, contentValues);
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

    //Get average of All Assignment
    public double getAverageAllAssignment(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            //Get average using SQL
            String query = "SELECT AVG("+Config.COLUMN_ASS_GRADE+") FROM "+ Config.ASS_TABLE_NAME;
            cursor = db.rawQuery(query,null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    return cursor.getDouble(0);
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
        return 0;
    }

    //Get Average By Course using Query
    public Double getAverageByCourse(int CourseID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            //Get average using SQL
            String query = "SELECT AVG("+Config.COLUMN_ASS_GRADE+") FROM "+ Config.ASS_TABLE_NAME+" WHERE "+Config.COLUMN_COURSE_ID+ " = " + CourseID;
            cursor = db.rawQuery(query,null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    Log.d(TAG, "Exception: " + cursor.getDouble(0));
                    return cursor.getDouble(0);
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

        return 0.0;
    }

    //Delete a Course
    public void deleteCourse(int CourseID){
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            db.delete(Config.ASS_TABLE_NAME, Config.COLUMN_COURSE_ID + " = ?", new String[]{CourseID+""});
            db.delete(Config.COURSE_TABLE_NAME, Config.COLUMN_COURSE_ID + " = ?", new String[]{CourseID+""});

        } catch (SQLException e) {
            Log.d(TAG, "Exception: " + e);
            Toast.makeText(context, "Operation Failed! " + e, Toast.LENGTH_LONG).show();
        } finally {
            db.close();

        }
    }

    //Check if Assignment Exists in Course
    public boolean assignmentExists(int CourseID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            //Count Assignment per Course using SQL
            String query = "SELECT COUNT("+Config.COLUMN_ASS_ID+") FROM "+ Config.ASS_TABLE_NAME+" WHERE "+Config.COLUMN_COURSE_ID+ " = " + CourseID;
            cursor = db.rawQuery(query,null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    int count = cursor.getInt(0);
                    if(count != 0)
                        return false;
                    else
                        return true;
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
        return false;
    }

}
