package mobi.liaison.bindings;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import mobi.liaison.R;
import mobi.liaison.SampleProvider;
import mobi.liaison.loaders.bindings.ActivityItemBinding;
import mobi.liaison.loaders.bindings.TextBinder;
import mobi.liaison.tables.SampleTableContent;

/**
 * Queries SampleContentTable with the following:
 * SELECT * FROM SampleContentTable where _id = ?
 * Updates when content://sample.authority.com/SamplePath is notified
 */
public class SampleDetailsBinding extends ActivityItemBinding {

    private final long mId;

    public SampleDetailsBinding(final Activity activity, final long id) {
        super(activity);
        mId = id;

        /**
         * Binds the sample_column_1 to the UI in the activity;
         */
        final TextBinder sampleColumn1Binder = new TextBinder(R.id.activity_details_sample_sample_column_1, SampleTableContent.Columns.SAMPLE_COLUMN_1);
        addBinding(sampleColumn1Binder);

        /**
         * Binds the sample_column_2 to the UI in the activity;
         */
        final TextBinder sampleColumn2Binder = new TextBinder(R.id.activity_details_sample_sample_column_2, SampleTableContent.Columns.SAMPLE_COLUMN_2);
        addBinding(sampleColumn2Binder);
    }

    @Override
    public String getSelection(Context context) {
        return SampleTableContent.Columns._ID + "=?";
    }

    @Override
    public String[] getSelectionArguments(Context context) {
        return new String[]{Long.toString(mId)};
    }

    @Override
    public Uri getUri(Context context) {
        return SampleProvider.getUri(context, SampleTableContent.Paths.SAMPLE_PATH);
    }
}
