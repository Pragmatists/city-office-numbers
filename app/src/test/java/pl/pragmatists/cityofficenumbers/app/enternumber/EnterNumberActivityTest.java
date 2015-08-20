package pl.pragmatists.cityofficenumbers.app.enternumber;

import static java.util.Collections.*;
import static org.assertj.android.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.Intent;
import android.widget.TextView;
import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.R;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "src/main/AndroidManifest.xml")
public class EnterNumberActivityTest {

    private EventBus eventBus;

    @Before
    public void setUp() throws Exception {
        eventBus = BusInstance.instance();
    }

    @Test
    public void shows_group_info_on_event() {
        EnterNumberActivity activity = createActivityForGroupId(2);

        OfficeGroupJson json = new OfficeGroupJson();
        json.idGrupy = 2;
        json.aktualnyNumer = 143;
        json.literaGrupy = "U";
        json.liczbaKlwKolejce = 6;

        eventBus.post(new OfficeGroupsFetched(singletonList(json)));

        assertThat((TextView) activity.findViewById(R.id.current_number)).hasTextString("U143");
        assertThat((TextView) activity.findViewById(R.id.queue_size)).hasTextString("6");
    }

    private EnterNumberActivity createActivityForGroupId(int groupId) {
        Intent intent = createIntentWithGroupId(groupId);
        return Robolectric.buildActivity(EnterNumberActivity.class).withIntent(intent).create().visible().resume()
                .get();
    }

    private Intent createIntentWithGroupId(int groupId) {
        return new Intent()
                    .putExtra(SelectGroupActivity.ARG_OFFICE_ID, "1234")
                    .putExtra(EnterNumberActivity.GROUP_ID, groupId);
    }
}