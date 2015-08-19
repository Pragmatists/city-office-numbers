package pl.pragmatists.cityofficenumbers.app.enternumber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import pl.pragmatists.cityofficenumbers.app.GroupIntentService;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.events.BusInstance;

public class EnterNumberActivity extends AppCompatActivity implements EnterNumberView {

    private static final String GROUP_ID = "group-id";

    private String officeId;

    private EnterNumberUi enterNumberUi;

    private TextView currenNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);
        officeId = getIntent().getStringExtra(SelectGroupActivity.ARG_OFFICE_ID);
        int groupId = getIntent().getIntExtra(GROUP_ID, 0);
        currenNumberTextView = (TextView) findViewById(R.id.current_number);
        enterNumberUi = new EnterNumberUi(groupId, this);
    }

    public static void startForOfficeAndGroup(Context context, String officeId, int groupId) {
        Intent intent = new Intent(context, EnterNumberActivity.class)
                .putExtra(SelectGroupActivity.ARG_OFFICE_ID, officeId)
                .putExtra(GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusInstance.instance().register(enterNumberUi);
        GroupIntentService.startFetchGroupsAction(this, officeId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusInstance.instance().unregister(enterNumberUi);
    }

    @Override
    public void setCurrentNumber(String currentNumber) {
        currenNumberTextView.setText(currentNumber);
    }
}
