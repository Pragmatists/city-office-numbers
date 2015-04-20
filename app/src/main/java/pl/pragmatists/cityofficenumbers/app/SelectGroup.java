package pl.pragmatists.cityofficenumbers.app;

import java.util.Collection;

import org.springframework.web.client.RestTemplate;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;

public class SelectGroup extends ActionBarActivity {

    private static final String ARG_OFFICE_ID = "office-id";

    private ArrayAdapter<OfficeGroup> officeGroupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String officeId = getIntent().getStringExtra(ARG_OFFICE_ID);
        setContentView(R.layout.activity_select_group);
        ProgressBar progressBar = createProgressBar();
        addToRoot(progressBar);

        officeGroupsAdapter = new ArrayAdapter<>(this, R.layout.office_group_item);
        getListView().setAdapter(officeGroupsAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        LoaderManager.LoaderCallbacks<Collection<OfficeGroup>> callback = new LoaderManager.LoaderCallbacks<Collection<OfficeGroup>>() {
            @Override
            public Loader<Collection<OfficeGroup>> onCreateLoader(int id, Bundle args) {
                return new GroupsLoader(SelectGroup.this, officeId, new RestTemplate());
            }

            @Override
            public void onLoadFinished(Loader<Collection<OfficeGroup>> loader, Collection<OfficeGroup> data) {
                officeGroupsAdapter.addAll(data);
            }

            @Override
            public void onLoaderReset(Loader<Collection<OfficeGroup>> loader) {
                officeGroupsAdapter.clear();
            }
        };
        getLoaderManager().initLoader(0, null, callback).startLoading();
    }

    private void addToRoot(ProgressBar progressBar) {
        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);
    }

    private ProgressBar createProgressBar() {
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);
        return progressBar;
    }

    private ListView getListView() {
        return (ListView) findViewById(R.id.office_groups);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void startForOfficeId(Context context, String id) {
        Intent intent = new Intent(context, SelectGroup.class).putExtra(ARG_OFFICE_ID, id);
        context.startActivity(intent);
    }
}
