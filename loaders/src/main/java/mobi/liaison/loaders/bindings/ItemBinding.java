package mobi.liaison.loaders.bindings;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

import mobi.liaison.loaders.BindDefinition;
import mobi.liaison.loaders.database.Column;
import mobi.liaison.loaders.bindings.interfaces.Binding;
import mobi.liaison.loaders.bindings.interfaces.ColumnBinding;
import mobi.liaison.loaders.bindings.interfaces.ColumnResourceBinding;
import mobi.liaison.loaders.bindings.interfaces.DataBinding;
import mobi.liaison.loaders.bindings.interfaces.ResourceBinding;

/**
 * Created by Emir Hasanbegovic on 28/04/14.
 */
public abstract class ItemBinding extends BindDefinition {

    private View mRootView;
    private final Set<Binding> mBindings;

    public ItemBinding(final Context context, final View rootView, final Binding binding) {
        this(context, rootView, Sets.newHashSet(binding));
    }

    public ItemBinding(final Context context, final View rootView) {
        this(context, rootView, new HashSet<Binding>());
    }

    public ItemBinding(final Context context) {
        this(context, null, new HashSet<Binding>());
    }

    public ItemBinding(final Context context, final View rootView, final Set<Binding> bindings) {
        super(context);
        mRootView = rootView;
        mBindings = bindings;
        for (final Binding binding : bindings) {
            extractResourceIds(binding);
        }
    }

    public void setRootView(final View rootView) {
        mRootView = rootView;
    }

    public void addBinding(final Binding binding) {
        mBindings.add(binding);
        extractResourceIds(binding);
    }

    private void extractResourceIds(final Binding binding) {
        if (binding instanceof ResourceBinding) {
            final ResourceBinding resourceBinding = (ResourceBinding) binding;
            final Set<Integer> resourceIds = resourceBinding.getResourceIds();
            for (final int resourceId : resourceIds) {
                final View view = mRootView.findViewById(resourceId);
                mRootView.setTag(resourceId, view);
            }
        }
    }

    @Override
    public void onBind(final Context context, Cursor cursor) {
        bind(context, mBindings, mRootView, cursor);
    }

    public static void bind(final Context context, final Set<Binding> bindings, final View view, final Cursor cursor) {
        for (final Binding binding : bindings) {

            binding.onBindStart(context);

            if (binding instanceof ResourceBinding) {
                final ResourceBinding resourceBinding = (ResourceBinding) binding;
                for (final Integer resourceId : resourceBinding.getResourceIds()) {
                    final View bindingView = (View) view.getTag(resourceId);
                    resourceBinding.onBind(context, bindingView, resourceId);

                    if (binding instanceof ColumnResourceBinding) {
                        final ColumnResourceBinding columnResourceBinding = (ColumnResourceBinding) binding;
                        for (final Column column : columnResourceBinding.getColumns()) {
                            final Object value = column.getValue(cursor);
                            columnResourceBinding.onBind(context, bindingView, resourceId, column, value);
                        }
                    }
                }
            }

            if (binding instanceof ColumnBinding) {
                final ColumnBinding columnBinding = (ColumnBinding) binding;
                for (final Column column : columnBinding.getColumns()) {
                    final Object value = column.getValue(cursor);
                    columnBinding.onBind(context, column, value);
                }
            }

            if (binding instanceof DataBinding) {
                final DataBinding dataBinding = (DataBinding) binding;
                dataBinding.onBind(context, cursor);
            }

            binding.onBindEnd(context);
        }
    }

}
