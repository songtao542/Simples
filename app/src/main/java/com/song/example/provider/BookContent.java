package com.song.example.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by le on 3/9/17.
 */

public class BookContent {
    public static final String AUTHORITY = "com.exam.book.provider";


    public static final String PARAMETER_LIMIT = "limit";

    public interface CategoryColumns extends BaseColumns {
        String DISPLAY_NAME = "displayName";
        String CODE = "code";
    }

    public interface BookColumns extends BaseColumns {
        String CATEGORY_KEY = "categoryKey";
        String DISPLAY_NAME = "displayName";
    }

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public BookContent() {

    }


    public static class Category extends BookContent {
        public static final String TABLE_NAME = "Category";
        public static final Uri CONTENT_URI = Uri.parse(BookContent.CONTENT_URI + "/category");
    }

    public static class Book extends BookContent {
        public static final String TABLE_NAME = "Book";
        public static final Uri CONTENT_URI = Uri.parse(BookContent.CONTENT_URI + "/book");
    }

}
