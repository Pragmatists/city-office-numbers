package pl.pragmatists.cityofficenumbers.app.selectoffice;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.common.CityOfficeNumbersActivity;
import pl.pragmatists.cityofficenumbers.app.selectgroup.ErrorUi;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.events.BusInstance;

import javax.inject.Inject;

public class SelectOfficeActivity extends CityOfficeNumbersActivity {

    public static final String ARG_USER_ID = "user-id";

    private OfficesListAdapter officesListAdapter;

    @Inject
    UserId userId;

    @Inject
    ErrorUi errorUi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMyApplication().component().inject(this);
        setContentView(R.layout.activity_select_office);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectGroupActivity.startForOfficeId(SelectOfficeActivity.this, ((Office)view.getTag()).getId());
            }
        });
        officesListAdapter = new OfficesListAdapter(this);
        getListView().setAdapter(officesListAdapter);
        initProgressBar();
        getMyApplication().component().inject(this);

    }

    private ListView getListView() {
        return (ListView) findViewById(R.id.offices);
    }

    private ProgressBar initProgressBar() {
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);
        addToRoot(progressBar);
        return progressBar;
    }

    private void addToRoot(ProgressBar progressBar) {
        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusInstance.instance().register(officesListAdapter);
        BusInstance.instance().register(errorUi);
        OfficesIntentService.startFetchOfficesAction(this, userId.get());
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusInstance.instance().unregister(officesListAdapter);
        BusInstance.instance().unregister(errorUi);
    }

    public void toggleFavorite(View v) {
        Office office = (Office) v.getTag();
        // TODO: call a intent service to change favorite state
    }

    public void toggleFavoriteOnlyVisible(View view) {
        Switch onlyFavoriteToggle = (Switch) view;
        officesListAdapter.getFilter().filter(onlyFavoriteToggle.isChecked() ? "Y" : "", null);
    }
}
