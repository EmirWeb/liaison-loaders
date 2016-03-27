package mobi.liaison.loaders.bindings.adapters;

import android.app.Fragment;
import android.widget.AdapterView;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import mobi.liaison.loaders.database.Column;

/**
 * Created by Emir Hasanbegovic on 28/04/14.
 */
public abstract class FragmentAdapterBinding extends AdapterBinding {

    public FragmentAdapterBinding(final Fragment fragment, int resourceId) {
        this(fragment, resourceId, null, new ArrayList<AdapterItemBinding>());
    }

    public FragmentAdapterBinding(final Fragment fragment, int resourceId, AdapterItemBinding adapterItemBinding) {
        this(fragment, resourceId, null, Lists.newArrayList(adapterItemBinding));
    }

    public FragmentAdapterBinding(final Fragment fragment, int resourceId, List<AdapterItemBinding> adapterItemBindings) {
        this(fragment, resourceId, null, adapterItemBindings);
    }

    public FragmentAdapterBinding(final Fragment fragment, int resourceId, final Column column) {
        this(fragment, resourceId, column, new ArrayList<AdapterItemBinding>());
    }

    public FragmentAdapterBinding(final Fragment fragment, int resourceId, final Column column, AdapterItemBinding adapterItemBinding) {
        this(fragment, resourceId, column, Lists.newArrayList(adapterItemBinding));
    }

    public FragmentAdapterBinding(final Fragment fragment, int resourceId, final Column column, List<AdapterItemBinding> adapterItemBindings) {
        super(fragment.getActivity().getApplicationContext(), (AdapterView) fragment.getView().findViewById(resourceId), column, adapterItemBindings);
    }
}
