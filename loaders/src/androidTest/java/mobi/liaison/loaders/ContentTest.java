package mobi.liaison.loaders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * When moving to newer version of Android, SQLiteDatabase is a final class that does not allow us to mock it. Most of these test can no longer run.
 */
@RunWith(AndroidJUnit4.class)
public class ContentTest {

    final MockContent mMockContent = new MockContent();
    final Context mContext = null;
    final SQLiteDatabase mSqLiteDatabase = null;

    @Test
    public void onUpgrade_dropsTableAndCreatesTable() {
//        mMockContent.onUpgrade(mContext, mSqLiteDatabase, 1);
//        verify(mSqLiteDatabase).execSQL("DROP TABLE EMIR");
//        verify(mSqLiteDatabase).execSQL("CREATE TABLE EMIR");
    }

    @Test
    public void onNoUpgrade_doesNotdropAndCreateTable() {
//        mMockContent.onUpgrade(mContext, mSqLiteDatabase, 0);
//        verify(mSqLiteDatabase, never()).execSQL(any(String.class));
    }

    @Test
    public void insertCallsInsertWithRightNameAndReturnsCorrectUri() {
//        when(mSqLiteDatabase.insert(any(String.class), any(String.class), any(ContentValues.class))).thenReturn(Long.valueOf(1));
//        final Uri uri = Uri.parse("http://www.authority.com/PATH1/PATH2");
//        final Uri insertUri = mMockContent.insert(mContext, mSqLiteDatabase, new Path("PATH1/PATH2"), uri, null);
//        verify(mSqLiteDatabase).insert(eq("NAME"), any(String.class), any(ContentValues.class));
//        assertThat(insertUri, equalTo(Uri.parse("http://www.authority.com/PATH1/PATH2/1")));
    }

    @Test
    public void insertCallsInsertWithRightNameAndReturnsCorrectUri_withDifferentIncomingUri() {
//        when(mSqLiteDatabase.insert(any(String.class), any(String.class), any(ContentValues.class))).thenReturn(Long.valueOf(1));
//        final Uri uri = Uri.parse("http://www.authority.com");
//        final Uri insertUri = mMockContent.insert(mContext, mSqLiteDatabase, new Path("PATH1/PATH2"), uri, null);
//        verify(mSqLiteDatabase).insert(eq("NAME"), any(String.class), any(ContentValues.class));
//        assertThat(insertUri, equalTo(Uri.parse("http://www.authority.com/PATH1/PATH2/1")));
    }

    public static class MockContent extends Content {

        @Override
        public int getVersion(final Context context) {
            return 0;
        }

        @Override
        public String getName(final Context context) {
            return "NAME";
        }

        @Override
        public String getCreate(final Context context) {
            return "CREATE TABLE EMIR";
        }

        @Override
        public String getDrop(final Context context) {
            return "DROP TABLE EMIR";
        }

        @Override
        public List<Path> getPaths(final Context context) {
            return Lists.newArrayList(new Path("PATH1/PATH2"));
        }

    }

}
