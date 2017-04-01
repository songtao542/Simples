package com.song.example.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by le on 3/9/17.
 */

public class DBHelper {
    public static final int DATABASE_VERSION = 1;

    private static final String TRIGGER_CATEGORY_DELETE =
            "create trigger category_delete before delete on " + BookContent.Category.TABLE_NAME +
                    " begin delete from " + BookContent.Book.TABLE_NAME +
                    " where " + BookContent.BookColumns.CATEGORY_KEY + "=old." + BaseColumns._ID +
                    "; end";

    static void createCategoryTable(SQLiteDatabase db) {
        String s = " (" + BookContent.CategoryColumns._ID + " integer primary key autoincrement, "
                + BookContent.CategoryColumns.DISPLAY_NAME + " text, "
                + BookContent.CategoryColumns.CODE + " text"
                + ");";
        db.execSQL("create table " + BookContent.Category.TABLE_NAME + s);
        // Deleting an account deletes associated Mailboxes and HostAuth's
        db.execSQL(TRIGGER_CATEGORY_DELETE);
    }

    static void createBookTable(SQLiteDatabase db) {
        String s = " (" + BookContent.BookColumns._ID + " integer primary key autoincrement, "
                + BookContent.BookColumns.DISPLAY_NAME + " text, "
                + BookContent.BookColumns.CATEGORY_KEY + " integer"
                + ");";
        db.execSQL("create table " + BookContent.Book.TABLE_NAME + s);
    }


    protected static class DatabaseHelper extends SQLiteOpenHelper {
        final Context mContext;

        DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
            mContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            createBookTable(db);
            createCategoryTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
