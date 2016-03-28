package mobi.liaison.tables;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import mobi.liaison.SampleData;
import mobi.liaison.SampleProvider;
import mobi.liaison.loaders.Path;
import mobi.liaison.loaders.database.Column;
import mobi.liaison.loaders.database.TableContent;
import mobi.liaison.loaders.database.annotations.TableColumn;
import mobi.liaison.loaders.database.annotations.TableColumns;
import mobi.liaison.loaders.database.annotations.TablePath;
import mobi.liaison.loaders.database.annotations.TablePaths;
import mobi.liaison.loaders.database.annotations.Unique;

/**
 * This creates a table in the database called SampleContentTable.
 * <p>
 * It has one column called _id and it contains long.
 * <p>
 * It has one column called SampleColumn1 and it contains Strings.
 * <p>
 * It has one column called SampleColumn2 and it contains Strings.
 * <p>
 * This handles all content providers functions destined for the
 * URI content://sample.authority.com/SamplePath .
 * <p>
 * This includes Query, Insert, BulkInsert, Delete, Update.
 */
public class SampleTableContent extends TableContent {

    public static final String SAMPLE_NAME = "SampleTableContent";

    @Override
    public String getName(Context context) {
        return SAMPLE_NAME;
    }

    @TablePaths
    public static class Paths {
        @TablePath
        public static final Path SAMPLE_PATH = new Path("SamplePath");
    }

    @TableColumns
    public static class Columns {
        @Unique
        @TableColumn
        public static final Column _ID = new Column(SAMPLE_NAME, BaseColumns._ID, Column.Type.integer);
        @TableColumn
        public static final Column SAMPLE_COLUMN_1 = new Column(SAMPLE_NAME, "SampleColumn1", Column.Type.text);
        @TableColumn
        public static final Column SAMPLE_COLUMN_2 = new Column(SAMPLE_NAME, "SampleColumn2", Column.Type.text);
    }

    /**
     * Deletes all information in database and inserts new data.
     *
     * ContentProviderOperation is used to batch all the commands into a single transaction.
     *
     * @param context
     * @param sampleColumns
     * @return true if successful, false otherwise
     */
    public static boolean insert(final Context context, final List<SampleData> sampleColumns) {
        final Uri uri = SampleProvider.getUri(context, Paths.SAMPLE_PATH);

        final ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();

        final ContentProviderOperation deleteContentProviderOperation = ContentProviderOperation.newDelete(uri).build();
        contentProviderOperations.add(deleteContentProviderOperation);

        for (final SampleData sampleData : sampleColumns) {
            final ContentValues contentValues = getContentValues(sampleData);
            final ContentProviderOperation contentProviderOperation = ContentProviderOperation.newInsert(uri).withValues(contentValues).build();
            contentProviderOperations.add(contentProviderOperation);

        }

        final ContentResolver contentResolver = context.getContentResolver();
        final String providerAuthority = SampleProvider.getProviderAuthority(context);
        try {
            contentResolver.applyBatch(providerAuthority, contentProviderOperations);
            contentResolver.notifyChange(uri, null);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ContentValues getContentValues(final SampleData sampleData) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Columns._ID.getName(), sampleData.mId);
        contentValues.put(Columns.SAMPLE_COLUMN_1.getName(), sampleData.mSampleColumn1);
        contentValues.put(Columns.SAMPLE_COLUMN_2.getName(), sampleData.mSampleColumn2);
        return contentValues;
    }
}
