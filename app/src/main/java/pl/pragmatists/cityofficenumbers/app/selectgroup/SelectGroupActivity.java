package pl.pragmatists.cityofficenumbers.app.selectgroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import pl.pragmatists.cityofficenumbers.app.GroupIntentService;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.enternumber.EnterNumberActivity;
import pl.pragmatists.cityofficenumbers.app.selectoffice.CityOfficeNumbersActivity;
import pl.pragmatists.cityofficenumbers.app.selectoffice.SelectOfficeActivity;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;

import javax.inject.Inject;

public class SelectGroupActivity extends CityOfficeNumbersActivity {

    public static final String ARG_OFFICE_ID = "office-id";

    private ArrayAdapter<OfficeGroup> officeGroupsAdapter;

    private String officeId;

    @Inject
    ErrorUi errorUi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMyApplication().component().inject(this);
        officeId = getIntent().getStringExtra(ARG_OFFICE_ID);
        setContentView(R.layout.activity_select_group);
        initProgressBar();
        officeGroupsAdapter = new GroupsListUi(this);

        getListView().setAdapter(officeGroupsAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnterNumberActivity
                        .startForOfficeAndGroup(SelectGroupActivity.this, officeId, officeGroupsAdapter.getItem(position).groupId());
            }
        });

    }

    private void addToRoot(ProgressBar progressBar) {
        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);
    }

    private ProgressBar initProgressBar() {
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);
        addToRoot(progressBar);
        return progressBar;
    }

    private ListView getListView() {
        return (ListView) findViewById(R.id.office_groups);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusInstance.instance().register(officeGroupsAdapter);
        BusInstance.instance().register(errorUi);
        GroupIntentService.startFetchGroupsAction(this, officeId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusInstance.instance().unregister(errorUi);
        BusInstance.instance().unregister(officeGroupsAdapter);
    }

    public static void startForOfficeId(Context context, String id) {
        Intent intent = new Intent(context, SelectGroupActivity.class).putExtra(ARG_OFFICE_ID, id);
        context.startActivity(intent);
    }
}
