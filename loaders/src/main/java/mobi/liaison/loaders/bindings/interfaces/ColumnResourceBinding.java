package mobi.liaison.loaders.bindings.interfaces;

import android.content.Context;
import android.view.View;

import mobi.liaison.loaders.database.Column;

/**
 * Created by Emir Hasanbegovic on 28/04/14.
 */
public interface ColumnResourceBinding extends ColumnBinding, ResourceBinding, Binding {
    public void onBind(final Context context, final View view, final int resourceId, final Column column, final Object value);
}
