package mobi.liaison.loaders.bindings.adapters;

import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.InstrumentationInfo;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.TextView;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;

import java.util.HashSet;
import java.util.Set;

import mobi.liaison.loaders.bindings.TextBinder;
import mobi.liaison.loaders.bindings.interfaces.Binding;
import mobi.liaison.loaders.bindings.interfaces.ColumnBinding;
import mobi.liaison.loaders.bindings.interfaces.ColumnResourceBinding;
import mobi.liaison.loaders.bindings.interfaces.DataBinding;
import mobi.liaison.loaders.bindings.interfaces.ResourceBinding;
import mobi.liaison.loaders.database.Column;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class AdapterItemBindingTest {

    @Test
    public void getLayoutResourceIdReturnsSameLayoutResourceIdPassedInConstructor(){
        AdapterItemBinding adapterItemBinding = new AdapterItemBinding(1);
        int layoutResourceId = adapterItemBinding.getLayoutResourceId();
        assertThat(layoutResourceId, equalTo(1));

        adapterItemBinding = new AdapterItemBinding(2, new TextBinder(0,new Column("TABLE_NAME", "COLUMN_NAME", Column.Type.text)));
        layoutResourceId = adapterItemBinding.getLayoutResourceId();
        assertThat(layoutResourceId, equalTo(2));


        final Set<Binding> bindings = new HashSet<Binding>();
        bindings.add(new TextBinder(0, new Column("TABLE_NAME", "COLUMN_NAME_0", Column.Type.text)));
        bindings.add(new TextBinder(1, new Column("TABLE_NAME", "COLUMN_NAME_1", Column.Type.text)));
        adapterItemBinding = new AdapterItemBinding(2, bindings);
        layoutResourceId = adapterItemBinding.getLayoutResourceId();
        assertThat(layoutResourceId, equalTo(2));
    }

    @Test
    public void getResourceIdsReturnsResourceIdsPassedInConstructorViaBindings(){
        AdapterItemBinding adapterItemBinding = new AdapterItemBinding(2, new TextBinder(0,new Column("TABLE_NAME", "COLUMN_NAME", Column.Type.text)));
        Set<Integer> resourceIds = adapterItemBinding.getResourceIds();
        assertThat(resourceIds, hasItem(0));


        final Set<Binding> bindings = new HashSet<Binding>();
        bindings.add(new TextBinder(0, new Column("TABLE_NAME", "COLUMN_NAME_0", Column.Type.text)));
        bindings.add(new TextBinder(1, new Column("TABLE_NAME", "COLUMN_NAME_1", Column.Type.text)));
        adapterItemBinding = new AdapterItemBinding(2, bindings);
        resourceIds = adapterItemBinding.getResourceIds();
        assertThat(resourceIds, hasItems(0, 1));
    }

    @Test
    public void getResourceIdsReturnsResourceIdsPassedInMethodViaBindings(){
        AdapterItemBinding adapterItemBinding = new AdapterItemBinding(2);
        adapterItemBinding.addBinding(new TextBinder(0,new Column("TABLE_NAME", "COLUMN_NAME", Column.Type.text)));

        Set<Integer> resourceIds = adapterItemBinding.getResourceIds();
        assertThat(resourceIds, hasItems(0));


        adapterItemBinding = new AdapterItemBinding(2);
        adapterItemBinding.addBinding(new TextBinder(0, new Column("TABLE_NAME", "COLUMN_NAME_0", Column.Type.text)));
        adapterItemBinding.addBinding(new TextBinder(1, new Column("TABLE_NAME", "COLUMN_NAME_1", Column.Type.text)));

        resourceIds = adapterItemBinding.getResourceIds();
        assertThat(resourceIds, hasItems(0, 1));
    }

    @Test
    public void bindCallsAllCallbacksForColumnResourceBinding(){
        final Context context = InstrumentationRegistry.getContext();
        final AdapterItemBinding adapterItemBinding = new AdapterItemBinding(2);
        final ColumnResourceBinding mockColumnResourceBinding = mock(ColumnResourceBinding.class);

        when(mockColumnResourceBinding.getResourceIds()).thenReturn(Sets.newHashSet(0, 1));
        final Column viewModelColumn1 = new Column("TABLE_NAME", "COLUMN_1", Column.Type.text);
        final Column viewModelColumn2 = new Column("TABLE_NAME", "COLUMN_2", Column.Type.text);
        when(mockColumnResourceBinding.getColumns()).thenReturn(Sets.newHashSet(viewModelColumn1, viewModelColumn2));

        adapterItemBinding.addBinding(mockColumnResourceBinding);

        final Cursor mockCursor = mock(Cursor.class);

        final View mockView = mock(View.class);
        when(mockView.getTag(anyInt())).thenReturn(new TextView(context));

        adapterItemBinding.bind(context, mockView, mockCursor);

        final InOrder inOrder = inOrder(mockColumnResourceBinding);
        inOrder.verify(mockColumnResourceBinding).onBindStart(any(Context.class));
        inOrder.verify(mockColumnResourceBinding).onBindEnd(any(Context.class));

        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(0));
        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(1));

        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(0), eq(viewModelColumn1), anyObject());
        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(0), eq(viewModelColumn2), anyObject());
        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(1), eq(viewModelColumn1), anyObject());
        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(1), eq(viewModelColumn2), anyObject());

        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), eq(viewModelColumn1), anyObject());
        verify(mockColumnResourceBinding, times(1)).onBind(any(Context.class), eq(viewModelColumn2), anyObject());

    }

    @Test
    public void bindCallsAllCallbacksForColumnBinding(){
        final Context context = InstrumentationRegistry.getContext();
        final AdapterItemBinding adapterItemBinding = new AdapterItemBinding(2);
        final ColumnBinding mockColumnBinding = mock(ColumnBinding.class);

        final Column viewModelColumn1 = new Column("TABLE_NAME", "COLUMN_1", Column.Type.text);
        final Column viewModelColumn2 = new Column("TABLE_NAME", "COLUMN_2", Column.Type.text);
        when(mockColumnBinding.getColumns()).thenReturn(Sets.newHashSet(viewModelColumn1, viewModelColumn2));

        adapterItemBinding.addBinding(mockColumnBinding);

        final Cursor mockCursor = mock(Cursor.class);

        final View mockView = mock(View.class);
        when(mockView.getTag(anyInt())).thenReturn(new TextView(context));

        adapterItemBinding.bind(context, mockView, mockCursor);

        final InOrder inOrder = inOrder(mockColumnBinding);
        inOrder.verify(mockColumnBinding).onBindStart(any(Context.class));
        inOrder.verify(mockColumnBinding).onBindEnd(any(Context.class));

        verify(mockColumnBinding, times(1)).onBind(any(Context.class), eq(viewModelColumn1), anyObject());
        verify(mockColumnBinding, times(1)).onBind(any(Context.class), eq(viewModelColumn2), anyObject());

    }

    @Test
    public void bindCallsAllCallbacksForResourceBinding(){
        final Context context = InstrumentationRegistry.getContext();
        final AdapterItemBinding adapterItemBinding = new AdapterItemBinding(2);
        final ResourceBinding resourceBinding = mock(ResourceBinding.class);

        when(resourceBinding.getResourceIds()).thenReturn(Sets.newHashSet(0, 1));

        adapterItemBinding.addBinding(resourceBinding);

        final Cursor mockCursor = mock(Cursor.class);

        final View mockView = mock(View.class);
        when(mockView.getTag(anyInt())).thenReturn(new TextView(context));

        adapterItemBinding.bind(context, mockView, mockCursor);

        final InOrder inOrder = inOrder(resourceBinding);
        inOrder.verify(resourceBinding).onBindStart(any(Context.class));
        inOrder.verify(resourceBinding).onBindEnd(any(Context.class));

        verify(resourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(0));
        verify(resourceBinding, times(1)).onBind(any(Context.class), any(View.class), eq(1));

    }

    @Test
    public void bindCallsAllCallbacksForDataBinding(){
        final Context context = InstrumentationRegistry.getContext();
        final AdapterItemBinding adapterItemBinding = new AdapterItemBinding(2);
        final DataBinding mockDataBinding = mock(DataBinding.class);


        adapterItemBinding.addBinding(mockDataBinding);

        final Cursor mockCursor = mock(Cursor.class);

        final View mockView = mock(View.class);
        when(mockView.getTag(anyInt())).thenReturn(new TextView(context));

        adapterItemBinding.bind(context, mockView, mockCursor);

        final InOrder inOrder = inOrder(mockDataBinding);
        inOrder.verify(mockDataBinding).onBindStart(any(Context.class));
        inOrder.verify(mockDataBinding).onBindEnd(any(Context.class));

        verify(mockDataBinding, times(1)).onBind(any(Context.class), any(Cursor.class));

    }


}