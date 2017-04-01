package com.song.example.provider;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.song.example.R;
import com.song.example.provider.BookContent.Book;
import com.song.example.provider.BookContent.BookColumns;
import com.song.example.provider.BookContent.Category;
import com.song.example.provider.BookContent.CategoryColumns;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText mCategoryCode;
    EditText mCategoryName;
    EditText mBookCategory;
    EditText mBookName;
    Button mInsertCategory;
    Button mInsertBook;

    TextView mBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCategoryCode = (EditText) findViewById(R.id.categoryCode);
        mCategoryName = (EditText) findViewById(R.id.categoryName);
        mBookCategory = (EditText) findViewById(R.id.bookCategory);
        mBookName = (EditText) findViewById(R.id.bookName);
        mInsertCategory = (Button) findViewById(R.id.insertCategory);
        mInsertBook = (Button) findViewById(R.id.insertBook);
        mBooks = (TextView) findViewById(R.id.books);

        mInsertCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mInsertBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        selectAll();
        update();
    }

    private void insert() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ContentValues category = new ContentValues();
        category.put(CategoryColumns.DISPLAY_NAME, mCategoryName.getText().toString());
        category.put(CategoryColumns.CODE, mCategoryCode.getText().toString());

        ops.add(ContentProviderOperation.newInsert(Category.CONTENT_URI)
                .withValues(category)
                .build());

        ContentValues book = new ContentValues();
        book.put(BookColumns.DISPLAY_NAME, mBookName.getText().toString());
//      book.put(BookColumns._ID,10);


        ContentValues backReferences = new ContentValues();
        backReferences.put(BookColumns.CATEGORY_KEY, 0);

//      ops.add(ContentProviderOperation.newInsert(ContentUris.withAppendedId(Book.CONTENT_URI, 10))
        ops.add(ContentProviderOperation.newInsert(Book.CONTENT_URI)
                .withValues(book)
//              .withValueBackReference(BookColumns.CATEGORY_KEY, 0)
                .withValueBackReferences(backReferences)
                .build());

        try {
            getContentResolver().applyBatch(BookContent.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectAll();
    }

    private void update() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ops.add(ContentProviderOperation.newUpdate(Category.CONTENT_URI)
                .withSelection(CategoryColumns.DISPLAY_NAME + "=?", new String[]{"电话号"})
                .withValue(CategoryColumns.CODE, 222)
                .build());

        ops.add(ContentProviderOperation.newUpdate(Category.CONTENT_URI)
                .withSelection(CategoryColumns.DISPLAY_NAME + "=?", new String[]{"范冰冰"})
                .withValue(CategoryColumns.CODE, 333)
                .build());

        ops.add(ContentProviderOperation.newUpdate(Book.CONTENT_URI)
                .withSelection(BookColumns.CATEGORY_KEY + "=?", new String[]{""})
                .withSelectionBackReference(0, 0)
                .withValue(BookColumns.DISPLAY_NAME, "222Name")
                .build());

        ops.add(ContentProviderOperation.newUpdate(Book.CONTENT_URI)
                .withSelection(BookColumns.CATEGORY_KEY + "=?", new String[]{""})
                .withSelectionBackReference(0, 1)
                .withValue(BookColumns.DISPLAY_NAME, "333Name")
                .build());

        try {
            getContentResolver().applyBatch(BookContent.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectAll();
    }

    private void selectAll() {
        Cursor c = getContentResolver().query(Book.CONTENT_URI, null, null, null, null);
        if (c != null) {
            try {
                while (c.moveToNext()) {
                    long id = c.getLong(c.getColumnIndex(BookColumns._ID));
                    String bookName = c.getString(c.getColumnIndex(BookColumns.DISPLAY_NAME));
                    long categoryKey = c.getLong(c.getColumnIndex(BookColumns.CATEGORY_KEY));
                    mBooks.append("id:" + id);
                    mBooks.append("bookName:" + bookName);
                    mBooks.append("categoryKey:" + categoryKey + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c.close();
            }
        }

        Cursor c1 = getContentResolver().query(Category.CONTENT_URI, null, null, null, null);
        if (c1 != null) {
            try {
                while (c1.moveToNext()) {
                    long id = c1.getLong(c1.getColumnIndex(CategoryColumns._ID));
                    String categoryName = c1.getString(c1.getColumnIndex(CategoryColumns.DISPLAY_NAME));
                    String categoryCode = c1.getString(c1.getColumnIndex(CategoryColumns.CODE));
                    mBooks.append("cid:" + id);
                    mBooks.append("categoryName:" + categoryName);
                    mBooks.append("categoryCode:" + categoryCode + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                c1.close();
            }
        }


    }
}
