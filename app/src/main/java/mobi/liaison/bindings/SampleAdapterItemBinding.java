package mobi.liaison.bindings;

import mobi.liaison.R;
import mobi.liaison.tables.SampleTableContent;
import mobi.liaison.loaders.bindings.TextBinder;
import mobi.liaison.loaders.bindings.adapters.AdapterItemBinding;

public class SampleAdapterItemBinding extends AdapterItemBinding {

    public SampleAdapterItemBinding(int layoutResourceId) {
        super(layoutResourceId);

        /**
         * Binds the sample_column_1 to the UI in the activity;
         */
        final TextBinder list_item_sample_column_1 = new TextBinder(R.id.list_item_sample_column_1, SampleTableContent.SAMPLE_COLUMN_1);
        addBinding(list_item_sample_column_1);

        /**
         * Binds the sample_column_2 to the UI in the activity;
         */
        final TextBinder list_item_sample_column_2 = new TextBinder(R.id.list_item_sample_column_2, SampleTableContent.SAMPLE_COLUMN_2);
        addBinding(list_item_sample_column_2);
    }
}
