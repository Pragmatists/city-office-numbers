package pl.pragmatists.cityofficenumbers.app;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import pl.pragmatists.cityofficenumbers.offices.CityOfficesModel;
import pl.pragmatists.cityofficenumbers.offices.Office;

public class SelectOffice extends AppCompatActivity {

    @Inject
    CityOfficesModel cityOfficesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMyApplication().component().inject(this);
        setContentView(R.layout.activity_select_office);
        ListView officesListView = (ListView) findViewById(R.id.offices);
        final Office[] offices = cityOfficesModel.offices();
        officesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectGroup.startForOfficeId(SelectOffice.this, offices[position].getId());
            }
        });
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.office_item, offices);
        officesListView.setAdapter(adapter);
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

}
