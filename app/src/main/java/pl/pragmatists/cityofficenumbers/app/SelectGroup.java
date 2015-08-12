package pl.pragmatists.cityofficenumbers.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import pl.pragmatists.cityofficenumbers.events.BusInstance;

public class SelectGroup extends AppCompatActivity {

    public static final String ARG_OFFICE_ID = "office-id";

    private ArrayAdapter<OfficeGroup> officeGroupsAdapter;

    private String officeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        officeId = getIntent().getStringExtra(ARG_OFFICE_ID);
        setContentView(R.layout.activity_select_group);
        ProgressBar progressBar = createProgressBar();
        addToRoot(progressBar);

        officeGroupsAdapter = new OfficesListUi(this);

        getListView().setAdapter(officeGroupsAdapter);


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
    protected void onResume() {
        super.onResume();
        BusInstance.instance().register(officeGroupsAdapter);
        GroupIntentService.startFetchGroupsAction(this, officeId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusInstance.instance().unregister(officeGroupsAdapter);
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
