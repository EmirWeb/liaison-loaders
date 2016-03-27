package mobi.liaison;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mobi.liaison.loaders.Content;
import mobi.liaison.loaders.DatabaseHelper;
import mobi.liaison.tables.SampleTableContent;

/**
 * Creates a Database with the name of SampleDatabaseName
 *
 * Has a table called SampleTableContent, go to SampleTableContent.java for more details
 *
 */
public class SampleDatabaseHelper extends DatabaseHelper {

    private static final String DATA_BASE_NAME = "SampleDatabaseName";
    private static final int VERSION = 1;

    public SampleDatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, VERSION);
    }

    @Override
    public List<Content> getContent(final Context context) {
        final List<Content> contentList = new ArrayList<Content>();

        contentList.add(new SampleTableContent());

        return contentList;
    }
}