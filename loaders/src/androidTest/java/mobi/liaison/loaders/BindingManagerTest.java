package mobi.liaison.loaders;

import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class BindingManagerTest {

    private LoaderManager mLoaderManager;
    private BindingManager mBindingManager;

    @Before
    public void setup() {
        mLoaderManager = mock(LoaderManager.class);
        mBindingManager = new BindingManager(null, mLoaderManager);
    }

    @Test
    public void onStartCallsInitLoaderTwice() {
        mBindingManager.addBindDefinition(new MockBindDefinition(null));
        mBindingManager.addBindDefinition(new MockBindDefinition(null) {
            @Override
            public int getId(Context context) {
                return 2;
            }
        });
        mBindingManager.onStart(null);
        verify(mLoaderManager, atLeast(2)).initLoader(anyInt(), any(Bundle.class), any(LoaderManager.LoaderCallbacks.class));
    }

    @Test
    public void addingDefinitionAfterStartedCallsInitLoaderTwice() {
        mBindingManager.onStart(null);
        mBindingManager.addBindDefinition(new MockBindDefinition(null));
        mBindingManager.addBindDefinition(new MockBindDefinition(null));
        verify(mLoaderManager, atLeast(2)).initLoader(anyInt(), any(Bundle.class), any(LoaderManager.LoaderCallbacks.class));
    }

    @Test
    public void addingDefinitionBeforeStartedDoesNotCallInit() {
        mBindingManager.addBindDefinition(new MockBindDefinition(null));
        mBindingManager.addBindDefinition(new MockBindDefinition(null));
        verify(mLoaderManager, never()).initLoader(anyInt(), any(Bundle.class), any(LoaderManager.LoaderCallbacks.class));
    }

    @Test
    public void onStartDoesNotCallInitLoader() {
        mBindingManager.onStart(null);
        verify(mLoaderManager, never()).initLoader(anyInt(), any(Bundle.class), any(LoaderManager.LoaderCallbacks.class));
    }

    public static class MockBindDefinition extends BindDefinition {

        public MockBindDefinition(Context context) {
            super(context);
        }

        @Override
        public void onBind(Context context, Cursor cursor) {

        }

        @Override
        public Uri getUri(Context context) {
            return Uri.parse("http://parchment.mobi");
        }

        @Override
        public int getId(Context context) {
            return 1;
        }
    }

}