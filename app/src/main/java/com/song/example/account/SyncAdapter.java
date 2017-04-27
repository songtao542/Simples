package com.song.example.account;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.song.example.LogTag;
import com.song.example.provider.BookContent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by le on 4/27/17.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LogTag.TAG, "do sync work start !!!");
        Log.d(LogTag.TAG, "account ==" + account);
        Log.d(LogTag.TAG, "extras ==" + extras);
        Log.d(LogTag.TAG, "authority==" + authority);
        Log.d(LogTag.TAG, "provider ==" + provider);
        insert(provider);
        selectAll(provider);
        Log.d(LogTag.TAG, "do sync work end !!!");
    }

    private void insert(ContentProviderClient provider) {
        String cateName = "Category1";
        String cateCode = "123";
        String bookName = "Book Name" + new Random().nextInt();

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ContentValues category = new ContentValues();
        category.put(BookContent.CategoryColumns.DISPLAY_NAME, cateName);
        category.put(BookContent.CategoryColumns.CODE, cateCode);

        ops.add(ContentProviderOperation.newInsert(BookContent.Category.CONTENT_URI)
                .withValues(category)
                .build());

        ContentValues book = new ContentValues();
        book.put(BookContent.BookColumns.DISPLAY_NAME, bookName);
//      book.put(BookColumns._ID,10);


        ContentValues backReferences = new ContentValues();
        backReferences.put(BookContent.BookColumns.CATEGORY_KEY, 0);

//      ops.add(ContentProviderOperation.newInsert(ContentUris.withAppendedId(Book.CONTENT_URI, 10))
        ops.add(ContentProviderOperation.newInsert(BookContent.Book.CONTENT_URI)
                .withValues(book)
//              .withValueBackReference(BookColumns.CATEGORY_KEY, 0)
                .withValueBackReferences(backReferences)
                .build());

        try {
            provider.applyBatch(ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectAll(ContentProviderClient provider) {
        Cursor c = null;
        try {
            c = provider.query(BookContent.Book.CONTENT_URI, null, null, null, null);
            if (c != null) {
                Log.d(LogTag.TAG, "books in database !!!!!!");
                while (c.moveToNext()) {
                    long id = c.getLong(c.getColumnIndex(BookContent.BookColumns._ID));
                    String bookName = c.getString(c.getColumnIndex(BookContent.BookColumns.DISPLAY_NAME));
                    long categoryKey = c.getLong(c.getColumnIndex(BookContent.BookColumns.CATEGORY_KEY));
                    Log.d(LogTag.TAG, "book:" + "id:" + id + "  bookName:" + bookName + "  categoryKey:" + categoryKey);
                }
                Log.d(LogTag.TAG, "books in database !!!!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        Cursor c1 = null;
        try {
            c1 = provider.query(BookContent.Category.CONTENT_URI, null, null, null, null);
            if (c1 != null) {

                Log.d(LogTag.TAG, "books category in database !!!!!!");
                while (c1.moveToNext()) {
                    long id = c1.getLong(c1.getColumnIndex(BookContent.CategoryColumns._ID));
                    String categoryName = c1.getString(c1.getColumnIndex(BookContent.CategoryColumns.DISPLAY_NAME));
                    String categoryCode = c1.getString(c1.getColumnIndex(BookContent.CategoryColumns.CODE));
                    Log.d(LogTag.TAG, "category:" + "cid:" + id + "   categoryName:" + categoryName + "   categoryCode:" + categoryCode);
                }
                Log.d(LogTag.TAG, "books category in database !!!!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c1 != null) {
                c1.close();
            }
        }
    }

}
