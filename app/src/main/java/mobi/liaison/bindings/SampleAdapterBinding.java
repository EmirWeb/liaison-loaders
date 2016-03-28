package mobi.liaison.bindings;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import mobi.liaison.R;
import mobi.liaison.SampleProvider;
import mobi.liaison.tables.SampleTableContent;
import mobi.liaison.loaders.bindings.adapters.ActivityAdapterBinding;


/**
 * Queries SampleContentTable with the following:
 * SELECT * FROM SampleContentTable
 * Updates when content://sample.authority.com/SamplePath is notified
 */
public class SampleAdapterBinding extends ActivityAdapterBinding {

    public SampleAdapterBinding(Activity activity, int resourceId) {
        super(activity, resourceId);

        /**
         * Defines a single type see Adapter's getItemType(int position) for more details
         */
        final SampleAdapterItemBinding sampleItemBinding = new SampleAdapterItemBinding(R.layout.list_item_sample);
        addItemBinding(sampleItemBinding);
    }

    @Override
    public Uri getUri(Context context) {
        return SampleProvider.getUri(context, SampleTableContent.Paths.SAMPLE_PATH);
    }
}
