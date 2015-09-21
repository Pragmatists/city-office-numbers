package pl.pragmatists.cityofficenumbers.app.enternumber;

import static org.assertj.android.api.Assertions.*;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupBuilder.*;

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
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder;

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
        OfficeGroups officeGroups = OfficeGroupsBuilder.withOneGroup(anOfficeGroup()
                .withId(2)
                .withCurrentNumber(143)
                .withLetter("U")
                .withQueueSize(6).build()).build();

        eventBus.post(new OfficeGroupsFetched(officeGroups));

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