package mobi.liaison.loaders.bindings.interfaces;

import android.content.Context;

import java.util.Set;

import mobi.liaison.loaders.database.Column;

/**
 * Created by Emir Hasanbegovic on 28/04/14.
 */
public interface ColumnBinding extends Binding {
    public Set<Column> getColumns();
    public void onBind(final Context context, final Column column, final Object value);
}
