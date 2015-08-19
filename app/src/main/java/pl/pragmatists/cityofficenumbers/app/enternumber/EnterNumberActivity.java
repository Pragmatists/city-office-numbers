package pl.pragmatists.cityofficenumbers.app.enternumber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import pl.pragmatists.cityofficenumbers.app.GroupIntentService;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.enternumber.EnterNumberPresenter;
import pl.pragmatists.cityofficenumbers.enternumber.EnterNumberView;
import pl.pragmatists.cityofficenumbers.events.BusInstance;

public class EnterNumberActivity extends AppCompatActivity implements EnterNumberView {

    public static final String GROUP_ID = "group-id";

    private String officeId;

    private EnterNumberPresenter enterNumberPresenter;

    private TextView currentNumberTextView;

    private TextView queueSizeTextView;

    private EditText ticketNumberTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);
        officeId = getIntent().getStringExtra(SelectGroupActivity.ARG_OFFICE_ID);
        int groupId = getIntent().getIntExtra(GROUP_ID, 0);
        currentNumberTextView = (TextView) findViewById(R.id.current_number);
        queueSizeTextView = (TextView) findViewById(R.id.queue_size);
        ticketNumberTextField = (EditText) findViewById(R.id.ticket_number_text_field);
        enterNumberPresenter = new EnterNumberPresenter(groupId, this);
        ticketNumberTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enterNumberPresenter.numberEntered(s.toString());
            }
        });
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
        BusInstance.instance().register(enterNumberPresenter);
        GroupIntentService.startFetchGroupsAction(this, officeId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusInstance.instance().unregister(enterNumberPresenter);
    }

    @Override
    public void setCurrentNumber(String currentNumber) {
        currentNumberTextView.setText(currentNumber);
    }

    @Override
    public void setQueueSize(String queueSize) {
        queueSizeTextView.setText(queueSize);
    }

    @Override
    public void setUsersNumber(String usersNumber) {
        ((TextView)findViewById(R.id.chosen_number)).setText(usersNumber);
    }

    @Override
    public void setQueueBeforeSize(String queueBefore) {
        ((TextView)findViewById(R.id.queue_before)).setText(queueBefore);
    }

    @Override
    public void setExpectedTime(String expectedTime) {
        ((TextView)findViewById(R.id.minutes_left)).setText(expectedTime);

    }
}
