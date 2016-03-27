package mobi.liaison.loaders.bindings;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

import mobi.liaison.loaders.bindings.interfaces.ColumnResourceBinding;
import mobi.liaison.loaders.bindings.interfaces.DataBinding;
import mobi.liaison.loaders.database.Column;

/**
 * Created by Emir Hasanbegovic on 12/05/14.
 */
public class Binder implements ColumnResourceBinding, DataBinding {

    private final Set<Integer> mResourceIds = new HashSet<Integer>();
    private final Set<Column> mColumn = new HashSet<Column>();

    public Binder() {
    }

    public Binder(final int resourceId) {
        mResourceIds.add(resourceId);
    }

    public Binder(final Column column) {
        if (column != null) {
            mColumn.add(column);
        }
    }

    public Binder(final int resourceId, final Column column) {
        mResourceIds.add(resourceId);
        if (column != null) {
            mColumn.add(column);
        }
    }

    public Binder(final Set<Integer> resourceIds, final Set<Column> columns) {
        if (resourceIds != null) {
            mResourceIds.addAll(resourceIds);
        }

        if (columns != null) {
            mColumn.addAll(columns);
        }
    }

    @Override
    public Set<Column> getColumns() {
        return mColumn;
    }

    @Override
    public void onBind(Context context, Column column, Object value) {

    }

    @Override
    public Set<Integer> getResourceIds() {
        return mResourceIds;
    }

    @Override
    public void onBind(Context context, View view, int resourceId) {

    }

    @Override
    public void onBindStart(Context context) {

    }

    @Override
    public void onBindEnd(Context context) {

    }

    @Override
    public void onBind(Context context, Cursor cursor) {

    }

    @Override
    public void onBind(Context context, View view, int resourceId, Column column, Object value) {

    }
}
