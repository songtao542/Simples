package com.song.example.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";

    public static final String DATABASE_NAME = "BookProvider.db";
    private static final Object sDatabaseLock = new Object();

    private SQLiteDatabase mDatabase;

    private static final int CATEGORY_BASE = 0;
    private static final int CATEGORY = CATEGORY_BASE;
    private static final int CATEGORY_ID = CATEGORY_BASE + 1;

    private static final int BOOK_BASE = 0x1000;
    private static final int BOOK = BOOK_BASE;
    private static final int BOOK_ID = BOOK_BASE + 1;


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        // All category
        sURIMatcher.addURI(BookContent.AUTHORITY, "category", CATEGORY);
        // A specific category
        sURIMatcher.addURI(BookContent.AUTHORITY, "category/#", CATEGORY_ID);

        // All book
        sURIMatcher.addURI(BookContent.AUTHORITY, "book", BOOK);
        // A specific book
        sURIMatcher.addURI(BookContent.AUTHORITY, "book/#", BOOK_ID);
    }

    public BookProvider() {
    }

    @Override
    public String getType(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match) {
            case CATEGORY:
                return "vnd.android.cursor.dir/book-category";
            case CATEGORY_ID:
                return "vnd.android.cursor.item/book-category";
            case BOOK:
                return "vnd.android.cursor.dir/book-category";
            case BOOK_ID:
                return "vnd.android.cursor.item/book-category";
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        switch (match) {
            case CATEGORY:
                return db.delete(BookContent.Category.TABLE_NAME, selection, selectionArgs);
            case CATEGORY_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                return db.delete(BookContent.Category.TABLE_NAME, BookContent.CategoryColumns._ID + "=" + longId + " AND (" + selection + ")", selectionArgs);
            case BOOK:
                return db.delete(BookContent.Book.TABLE_NAME, selection, selectionArgs);
            case BOOK_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                return db.delete(BookContent.Book.TABLE_NAME, BookContent.BookColumns._ID + "=" + longId + " AND (" + selection + ")", selectionArgs);
            default:
                return 0;
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert uri=" + uri);
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        Uri resultUri = null;
        switch (match) {
            case CATEGORY:
                longId = db.insert(BookContent.Category.TABLE_NAME, null, values);
                resultUri = ContentUris.withAppendedId(BookContent.Category.CONTENT_URI, longId);
                break;
            case CATEGORY_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                Log.d(TAG, "insert CATEGORY_ID id=" + longId);
                values.put(BookContent.CategoryColumns._ID, longId);
                insert(BookContent.Category.CONTENT_URI, values);
                resultUri = ContentUris.withAppendedId(BookContent.Category.CONTENT_URI, longId);
                break;
            case BOOK:
                longId = db.insert(BookContent.Book.TABLE_NAME, null, values);
                resultUri = ContentUris.withAppendedId(BookContent.Book.CONTENT_URI, longId);
                break;
            case BOOK_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                Log.d(TAG, "insert BOOK_ID id=" + longId);
                values.put(BookContent.BookColumns._ID, longId);
                insert(BookContent.Book.CONTENT_URI, values);
                resultUri = ContentUris.withAppendedId(BookContent.Book.CONTENT_URI, longId);
            default:
                break;
        }
        return resultUri;
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        String limit = uri.getQueryParameter(BookContent.PARAMETER_LIMIT);
        switch (match) {
            case CATEGORY:
                return db.query(BookContent.Category.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder, limit);
            case CATEGORY_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                return db.query(BookContent.Category.TABLE_NAME, projection, BookContent.CategoryColumns._ID + "=" + longId + " AND (" + selection + ")",
                        selectionArgs, null, null, sortOrder, limit);
            case BOOK:
                return db.query(BookContent.Book.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder, limit);
            case BOOK_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                return db.query(BookContent.Book.TABLE_NAME, projection, BookContent.BookColumns._ID + "=" + longId + " AND (" + selection + ")",
                        selectionArgs, null, null, sortOrder, limit);
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        switch (match) {
            case CATEGORY:
                return db.update(BookContent.Category.TABLE_NAME, values, selection, selectionArgs);
            case CATEGORY_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                values.put(BookContent.CategoryColumns._ID, longId);
                return db.update(BookContent.Category.TABLE_NAME, values, selection, selectionArgs);
            case BOOK:
                return db.update(BookContent.Book.TABLE_NAME, values, selection, selectionArgs);
            case BOOK_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                values.put(BookContent.BookColumns._ID, longId);
                return db.update(BookContent.Book.TABLE_NAME, values, selection, selectionArgs);
            default:
                return 0;
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        SQLiteDatabase db = getDatabase(getContext());
        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    public SQLiteDatabase getDatabase(Context context) {
        synchronized (sDatabaseLock) {
            // Always return the cached database, if we've got one
            if (mDatabase != null) {
                return mDatabase;
            }
            DBHelper.DatabaseHelper helper = new DBHelper.DatabaseHelper(context, DATABASE_NAME);
            mDatabase = helper.getWritableDatabase();
            return mDatabase;
        }
    }
}
