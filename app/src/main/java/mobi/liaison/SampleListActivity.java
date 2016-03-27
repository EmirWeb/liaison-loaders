package mobi.liaison;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import mobi.liaison.bindings.SampleAdapterBinding;
import mobi.liaison.loaders.ActivityBindingManager;
import mobi.liaison.tables.SampleTableContent;

public class SampleListActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(3);
    private ActivityBindingManager mActivtyBindingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_list);

        mActivtyBindingManager = new ActivityBindingManager(this);

        final SampleAdapterBinding sampleAdapterBinding = new SampleAdapterBinding(this, R.id.activity_sample_list_list_view);
        mActivtyBindingManager.addBindDefinition(sampleAdapterBinding);

        final ListView listView = (ListView) findViewById(R.id.activity_sample_list_list_view);
        listView.setOnItemClickListener(this);

        runFakeNetworkCalls();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivtyBindingManager.onStart(this);
    }

    @Override
    protected void onStop() {
        mActivtyBindingManager.onStop(this);
        super.onStop();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SampleDetailsActivity.startActivity(this, id);
    }

    /**
     * FAKE NETWORK CALL
     */
    private void runFakeNetworkCalls() {
        SCHEDULED_EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<SampleData> sampleDatas = new ArrayList<>();
                sampleDatas.add(new SampleData(0, "SampleColumn1 - 1", "SampleColumn2 - 1"));
                sampleDatas.add(new SampleData(1, "SampleColumn1 - 2", "SampleColumn2 - 2"));
                sampleDatas.add(new SampleData(2, "SampleColumn1 - 3", "SampleColumn2 - 3"));
                sampleDatas.add(new SampleData(3, "SampleColumn1 - 4", "SampleColumn2 - 4"));
                sampleDatas.add(new SampleData(4, "SampleColumn1 - 5", "SampleColumn2 - 5"));
                sampleDatas.add(new SampleData(5, "SampleColumn1 - 6", "SampleColumn2 - 6"));
                SampleTableContent.insert(getApplicationContext(), sampleDatas);
            }
        });
    }
}
