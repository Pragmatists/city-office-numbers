package pl.pragmatists.cityofficenumbers.app;

import static org.assertj.core.api.Assertions.*;
import static org.robolectric.Shadows.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import android.app.Application;
import android.content.Intent;
import android.widget.ListView;
import pl.pragmatists.cityofficenumbers.app.enternumber.EnterNumberActivity;
import pl.pragmatists.cityofficenumbers.app.selectgroup.SelectGroupActivity;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsServerError;
import pl.pragmatists.cityofficenumbers.testing.TestApplicationBuilder;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "src/main/AndroidManifest.xml")
public class SelectGroupActivityTest {

    private Application testApplication;

    private EventBus eventBus;

    @Before
    public void setUp() throws Exception {
        eventBus = BusInstance.instance();
        testApplication = new TestApplicationBuilder().build();
    }

    @Test
    public void shouldFetchGroupsOnCreate() {
        Intent intent = new Intent().putExtra(SelectGroupActivity.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        SelectGroupActivity activity = createSelectGroupActivity(intent);

        Intent nextStartedService = Shadows.shadowOf(activity).getNextStartedService();
        Intent expectedIntent = new Intent(activity, GroupIntentService.class)
                .putExtra(SelectGroupActivity.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");
        assertThat(nextStartedService).isEqualTo(expectedIntent);
    }

    @Test
    public void showsLoadedGroups() {
        SelectGroupActivity activity = createDefaultSelectGroupActivity();

        eventBus.post(new OfficeGroupsFetched(Collections.singletonList(defaultOfficeGroupJson())));

        ListView listView = (ListView) activity.findViewById(R.id.office_groups);
        assertThat(listView.getCount()).isEqualTo(1);
    }

    @Test
    public void showsAToastOnServerError() {
        createDefaultSelectGroupActivity();

        eventBus.post(new OfficeGroupsServerError("details"));

        assertThat(ShadowToast.getTextOfLatestToast()).contains("details");
    }

    @Test
    public void showsAToastOnNetworkError() {
        createDefaultSelectGroupActivity();

        eventBus.post(new OfficeGroupsNetworkError());

        assertThat(ShadowToast.getTextOfLatestToast()).contains("Nieudane połączenie z serwerem.");
    }

    @Test
    public void goesToGroupActivityOnSelect() {
        SelectGroupActivity activity = createSelectGroupActivity("9c3d5770-57d8-4365-994c-69c5ac4186ee");
        ListView lvOffices = (ListView) activity.findViewById(R.id.office_groups);
        eventBus.post(new OfficeGroupsFetched(Collections.singletonList(defaultOfficeGroupJson().withId(1))));

        shadowOf(lvOffices).performItemClick(0);

        Intent expectedIntent = new Intent(activity, EnterNumberActivity.class)
                .putExtra("office-id", "9c3d5770-57d8-4365-994c-69c5ac4186ee")
                .putExtra("group-id", 1);
        Intent nextStartedActivity = shadowOf(activity).getNextStartedActivity();
        assertThat(nextStartedActivity).isEqualTo(expectedIntent);
    }

    private OfficeGroupJson defaultOfficeGroupJson() {
        OfficeGroupJson officeGroupJson = new OfficeGroupJson();
        officeGroupJson.aktualnyNumer = 0;
        officeGroupJson.liczbaKlwKolejce = 0;
        return officeGroupJson;
    }

    private SelectGroupActivity createDefaultSelectGroupActivity() {
        return createSelectGroupActivity("5d2e698a-9c31-456b-8452-7ce33e7deb94");
    }

    private SelectGroupActivity createSelectGroupActivity(String officeId) {
        Intent intent = new Intent().putExtra(SelectGroupActivity.ARG_OFFICE_ID, officeId);
        return createSelectGroupActivity(intent);
    }

    private SelectGroupActivity createSelectGroupActivity(Intent intent) {
        return Robolectric.buildActivity(SelectGroupActivity.class)
                .withIntent(intent).withApplication(testApplication)
                .create().visible().resume().get();
    }

}