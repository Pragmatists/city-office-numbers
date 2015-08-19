package pl.pragmatists.cityofficenumbers.app.enternumber;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;

public class EnterNumberActivity extends AppCompatActivity {

    private static final String GROUP_ID = "group-id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);
        String officeId = getIntent().getStringExtra(SelectGroupActivity.ARG_OFFICE_ID);
        int groupId = getIntent().getIntExtra(GROUP_ID, 0);

    }

    public static void startForOfficeAndGroup(Context context, String officeId, int groupId) {
        Intent intent = new Intent(context, EnterNumberActivity.class)
                .putExtra(SelectGroupActivity.ARG_OFFICE_ID, officeId)
                .putExtra(GROUP_ID, groupId);
        context.startActivity(intent);
    }
}
