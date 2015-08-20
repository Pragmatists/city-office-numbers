package pl.pragmatists.cityofficenumbers.app.selectoffice;

import javax.inject.Inject;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import pl.pragmatists.cityofficenumbers.app.CityOfficeNumbersApplication;
import pl.pragmatists.cityofficenumbers.app.OfficesIntentService;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.offices.Office;

public class SelectOfficeActivity extends AppCompatActivity {

    public static final String ARG_USER_ID = "user-id";

    @Inject
    CityOfficesModel cityOfficesModel;

    private OfficesListAdapter officesListAdapter;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMyApplication().component().inject(this);
        setContentView(R.layout.activity_select_office);
        final Office[] offices = cityOfficesModel.offices();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectGroupActivity.startForOfficeId(SelectOfficeActivity.this, offices[position].getId());
            }
        });
        officesListAdapter = new OfficesListAdapter(this);
        getListView().setAdapter(officesListAdapter);
        initProgressBar();
        userId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
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
        officesListAdapter.clear();
        OfficesIntentService.startFetchOfficesAction(this, userId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusInstance.instance().unregister(officesListAdapter);
    }


    private CityOfficeNumbersApplication getMyApplication() {
        return (CityOfficeNumbersApplication) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_office, menu);
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

    public void toggleFavorite(View v) {
        Office office = (Office) v.getTag();
        ImageButton imageButton = (ImageButton) v.findViewById(R.id.star_image_button);
        imageButton.setImageResource(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
    }
}
