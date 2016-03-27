package mobi.liaison;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import mobi.liaison.loaders.DatabaseHelper;
import mobi.liaison.loaders.Path;
import mobi.liaison.loaders.Provider;
import mobi.liaison.loaders.UriUtilities;

/**
 * Sets up a Content Provider under content://sample.authority.com
 */
public class SampleProvider extends Provider {
    @Override
    public String getAuthority(final Context context) {
        return getProviderAuthority(context);
    }

    public static String getProviderAuthority(final Context context) {
        final Resources resources = context.getResources();
        final String authority = resources.getString(R.string.authority);
        return authority;
    }

    @Override
    protected DatabaseHelper onCreateDatabaseHelper(Context context) {
        return new SampleDatabaseHelper(context);
    }

    /**
     * An exmaple of centralizing creating all uris that this content provider will handle
     * @param context
     * @param path Path definition
     * @param objects Parameters that build the path
     * @return the uri
     */
    public static Uri getUri(final Context context, final Path path, final Object... objects) {
        final String authority = getProviderAuthority(context);
        return UriUtilities.getUri(ContentResolver.SCHEME_CONTENT, authority, path, objects);
    }
}
