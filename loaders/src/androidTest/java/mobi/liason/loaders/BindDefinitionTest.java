package mobi.liason.loaders;

import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by emir on 2016-03-19.
 */
@RunWith(AndroidJUnit4.class)
public class BindDefinitionTest {

    private Context mContext;
    private LoaderManager mLoaderManager;

    @Test
    public void differentIdsForDifferentChildrenOfItemBinding() {
        final BindDefinition1 bindDefinition1 = new BindDefinition1(mContext);
        final BindDefinition2 bindDefinition2 = new BindDefinition2(mContext);
        final int id1 = bindDefinition1.getId(mContext);
        final int id2 = bindDefinition2.getId(mContext);
        assertThat(id1, not(equalTo((id2))));
    }

    public class BindDefinition1 extends BindDefinition {

        public BindDefinition1(Context context) {
            super(context);
        }

        @Override
        public void onBind(Context context, Cursor cursor) {

        }

        @Override
        public Uri getUri(Context context) {
            return null;
        }

    }

    public class BindDefinition2 extends BindDefinition {

        public BindDefinition2(Context context) {
            super(context);
        }

        @Override
        public void onBind(Context context, Cursor cursor) {

        }

        @Override
        public Uri getUri(Context context) {
            return null;
        }
    }
}