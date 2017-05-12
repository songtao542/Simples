import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.song.example.provider.BookContent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by le on 5/11/17.
 */
@RunWith(AndroidJUnit4.class)
public class UriMatcherTest {
    @Test
    public void uriMatcher() throws Exception {

        UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        {
            // All category
            sURIMatcher.addURI(BookContent.AUTHORITY, "category", 1);
            // A specific category
            sURIMatcher.addURI(BookContent.AUTHORITY, "category/#", 2);

            // All book
            sURIMatcher.addURI(BookContent.AUTHORITY, "book", 3);
            // A specific book
            sURIMatcher.addURI(BookContent.AUTHORITY, "book/#", 4);
//            sURIMatcher.addURI(BookContent.AUTHORITY, "book/*", 5);//和51,6冲突
            sURIMatcher.addURI(BookContent.AUTHORITY, "book/author/*", 51);
            sURIMatcher.addURI(BookContent.AUTHORITY, "book/email/*", 6);

            // All user
            sURIMatcher.addURI("org.gitclub.provider", "user", 7);
            // A specific user
            sURIMatcher.addURI("org.gitclub.provider", "user/#", 8);
            // A specific user
//            sURIMatcher.addURI("org.gitclub.provider", "user/*", 9); //和10冲突
            sURIMatcher.addURI("org.gitclub.provider", "user/email/*", 10);
        }

        Uri u = BookContent.Book.CONTENT_URI.buildUpon().appendEncodedPath("author").appendEncodedPath("song").build();

        int m = sURIMatcher.match(u);

        assertEquals(5, m);


        Uri u1 = BookContent.Book.CONTENT_URI.buildUpon().appendEncodedPath("email").appendEncodedPath("songtao542@gmail.com").build();

        int m1 = sURIMatcher.match(u1);

        assertEquals(6, m1);

        Uri u2  =Uri.parse("content://org.gitclub.provider/user/email/songtao542@gmail.com");

        int m2 = sURIMatcher.match(u2);

        assertEquals(10, m2);
    }
}
