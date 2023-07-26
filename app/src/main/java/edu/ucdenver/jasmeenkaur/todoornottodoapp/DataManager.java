package edu.ucdenver.jasmeenkaur.todoornottodoapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DataManager {
    private SQLiteDatabase db;

    public DataManager(Context context) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    // =================================== insert functions ===================================
    public void insertList(String name){
        // create new empty list using user-entered name
        String query = "insert into list "
                + "(name) values"
                + "('" + name + "')";
        try {
            db.execSQL(query);
        }
        catch (SQLException e) {
            Log.i("info", "Error: could not create new list and insert into table - " + e.getMessage());
        }
    }

    public void insertTask(String name, String dueDate, String dueTime, String priority, String notes, String listID){
        String query = "insert into task "
                + "(name, due_date, due_time, priority, notes, list_id) values"
                + "(\"" + name + "\","
                + "\"" + dueDate + "\","
                + "\"" + dueTime + "\","
                + "\"" + priority + "\","
                //+ "(" + category + ","
                //+ "(" + parentTask + ","
                + "\"" + notes + "\","
                + "\"" + listID + "\")";
        try {
            db.execSQL(query);
        }
        catch (SQLException e) {
            Log.i("info", "Error: could not create new task and insert into table - " + e.getMessage());
        }
    }

    public void insertCategory(String name, String defaultCategory, String font, String textColor,
                               String backgroundColor, String listID){
        String query = "insert into category "
                + "(name, default_category, font, text_color, background_color, list_id) values"
                + "(" + name + ","
                + "(" + defaultCategory + ","
                + "(" + font + ","
                + "(" + textColor + ","
                + "(" + backgroundColor + ","
                + "(" + listID + ")";
        try {
            db.execSQL(query);
        }
        catch (SQLException e) {
            Log.i("info", "Error: could not create new category and insert into table - " + e.getMessage());
        }
    }


    // =================================== delete functions ===================================
    public void deleteList (int id) {
        String query = "delete from list where _id = " + id;
    }
    public void deleteTask (int id) {
        String query = "delete from task where _id = " + id;
    }
    public void deleteCategory (int id) {
        String query = "delete from category where _id = " + id;
    }



    // =================================== select functions ===================================
    public Cursor selectList(String id) {
        Cursor cursor = null;
        String query = "select * from list where _id is " + id;

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve list - " + e.getMessage());
        }

        return cursor;
    }

    public Cursor selectAllLists() {
        Cursor cursor = null;
        String query = "select * from list";

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve all lists - " + e.getMessage());
        }

        return cursor;
    }

    public Cursor selectListTasks(String listId) {
        Cursor cursor = null;
        String query = "select * from task where list_id is \"" + listId + "\"";

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve list's tasks - " + e.getMessage());
        }

        return cursor;
    }

    public Cursor selectListCompleteTasks(String listId, String sortBy) {
        Cursor cursor = null;
        String query = "select * from task where list_id is " + listId + " and completed is 'true' order by " + sortBy;

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve list's completed tasks - " + e.getMessage());
        }

        return cursor;
    }

    public Cursor selectListIncompleteTasks(String listId, String sortBy) {
        Cursor cursor = null;
        String query = "select * from task where list_id is " + listId + " and completed is 'false' order by " + sortBy;

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve list's incomplete tasks - " + e.getMessage());
        }

        return cursor;
    }

    /*
    public Cursor selectSubtasks(String id) {
        Cursor cursor = null;
        String query = "select * from tasks where parent_id is " + id;

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve subtasks - " + e.getMessage());
        }

        return cursor;
    }
    */

    public Cursor selectTask(String id) {
        Cursor cursor = null;
        String query = "select * from task where _id is \"" + id + "\"";

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve task information - " + e.getMessage());
        }

        return cursor;
    }

    public Cursor selectDefaultCategories(){
        Cursor cursor = null;
        String query = "select * from category where default_category = null";

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve default categories - " + e.getMessage());
        }

        return cursor;
    }

    public Cursor selectListCategories(String listId) {
        Cursor cursor = null;
        String query = "select * from category where list_id is " + listId;

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve list's categories - " + e.getMessage());
        }

        return cursor;
    }

    public Cursor selectCategory(String id) {
        Cursor cursor = null;
        String query = "select * from category where _id is " + id;

        try {
            cursor = db.rawQuery(query, null);
        }
        catch (SQLException e) {
            Log.i ("info", "Failed to retrieve category information - " + e.getMessage());
        }

        return cursor;
    }


    // =================================== update functions ===================================
    // update list values  -------------------------------------------------------------------------
    public void updateListName(String id, String name){
        String query = "update list set"
                + "name = " + name
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update list name - " + e.getMessage());
        }
    }
    public void updateListTaskSort(String id, String taskSort){
        String query = "update list set"
                + "task_sort = " + taskSort
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update list task sort - " + e.getMessage());
        }
    }
    public void updateListTaskCompleteHandle(String id, String taskCompleteHandle){
        String query = "update list set"
                + "task_complete_handle = " + taskCompleteHandle
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update list task complete handle - " + e.getMessage());
        }
    }
    public void updateListBackgroundColor(String id, String backgroundColor){
        String query = "update list set"
                + "background_color = " + backgroundColor
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update list background color - " + e.getMessage());
        }
    }
    public void updateList(String id, String name, String taskSort, String taskCompleteHandle,
                           String backgroundColor){
        String query = "update list set"
                + "name = " + name
                + "task_sort = " + taskSort
                + "task_complete_handle = " + taskCompleteHandle
                + "background_color = " + backgroundColor
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update list information - " + e.getMessage());
        }
    }

    // update task values --------------------------------------------------------------------------
    public void updateTaskComplete(String id, String completed){
        String query = "update task set"
                + "completed = " + completed
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update list information - " + e.getMessage());
        }
    }
    public void updateTask(String id, String name, String dueDate, String dueTime, String priority, String notes){
        String query = "update task set"
                + "name = " + name
                + "due_date = " + dueDate
                + "due_time = " + dueTime
                + "priority = " + priority
                //+ "category = " + category
                //+ "parent_task = " + parentTask
                + "notes = " + notes
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update task information - " + e.getMessage());
        }
    }

    // update category values ----------------------------------------------------------------------
    public void updateCategory(String id, String name, String defaultCategory, String font, String textColor,
                               String backgroundColor) {
        String query = "update category set"
                + "name = " + name
                + "default_category = " + defaultCategory
                + "font = " + font
                + "text_color = " + textColor
                + "background_color = " + backgroundColor
                + "where _id = " + id;

        try {
            db.execSQL(query);
        }
        catch (SQLException e){
            Log.i("info", "Error: could not update category information - " + e.getMessage());
        }
    }


    // =================================== MySQLiteOpenHelper Class ===================================
    private class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper (Context context) {
            super(context, "todo_database", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to hold different to-do lists
            String query = "create table list ("
                    + "_id integer primary key autoincrement not null,"
                    + "name text not null,"
                    + "task_sort default 'due_date',"
                    + "task_complete_handle default 'show',"
                    + "background_color integer default ffffffff"
                    + ")";

            try {
                db.execSQL(query);
            }
            catch (SQLException e){
                Log.i("error", "Error: could not create list table - " + e.getMessage());
            }


            // create table to hold different tasks
            query = "create table task ("
                    + "_id integer primary key autoincrement not null,"
                    + "name text not null,"
                    + "due_date,"
                    + "due_time,"
                    + "priority,"
                    //+ "category,"
                    //+ "parent_task,"
                    + "completed default 'false',"
                    + "notes,"
                    + "list_id"
                    + ")";

            try {
                db.execSQL(query);
            }
            catch (SQLException e){
                Log.i("error", "Error: could not create task table - " + e.getMessage());
            }


            // create table to hold different categories
            query = "create table category ("
                    + "_id integer primary key autoincrement not null,"
                    + "name text not null,"
                    + "default_category,"
                    + "font,"
                    + "text_color,"
                    + "background_color,"
                    + "list_id"
                    + ")";

            try {
                db.execSQL(query);
            }
            catch (SQLException e){
                Log.i("error", "Error: could not create category table - " + e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
