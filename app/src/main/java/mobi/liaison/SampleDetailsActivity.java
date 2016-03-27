package mobi.liaison;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import mobi.liaison.bindings.SampleDetailsBinding;
import mobi.liaison.loaders.ActivityBindingManager;

public class SampleDetailsActivity extends Activity {

    private static final String ID = "id";
    private ActivityBindingManager mActivityBindingManager;

    public static void startActivity(final Activity activity, final long id) {
        final Intent intent = new Intent(activity, SampleDetailsActivity.class);
        intent.putExtra(ID, id);
        activity.startActivity(intent);
    }

    private long getId() {
        final Intent intent = getIntent();
        return intent.getLongExtra(ID, -1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_details);

        mActivityBindingManager = new ActivityBindingManager(this);

        /**
         * Binds information in the database to the UI, passes the id in to make sure it shows the right information.
         */
        final SampleDetailsBinding sampleDetailsBinding = new SampleDetailsBinding(this, getId());
        mActivityBindingManager.addBindDefinition(sampleDetailsBinding);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityBindingManager.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityBindingManager.onStop(this);
    }
}
