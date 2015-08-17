package pl.pragmatists.cityofficenumbers.app;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroup;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsServerError;
import pl.pragmatists.cityofficenumbers.offices.TestApplication;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "src/main/AndroidManifest.xml")
public class SelectGroupActivityTest {

    private Application testApplication;

    private EventBus eventBus;

    @Before
    public void setUp() throws Exception {
        eventBus = BusInstance.instance();
    }

    @Test
    public void shouldFetchGroupsOnCreate() {
        OfficeGroupsFetcher officeGroupsFetcher = mock(OfficeGroupsFetcher.class);
        testApplication = new TestApplication(officeGroupsFetcher);
        Intent intent = new Intent().putExtra(SelectGroup.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        SelectGroup activity = createSelectGroupActivity(intent);

        Intent nextStartedService = Shadows.shadowOf(activity).getNextStartedService();
        Intent expectedIntent = new Intent(activity, GroupIntentService.class)
                .putExtra(GroupIntentService.OFFICE_ID_KEY, "5d2e698a-9c31-456b-8452-7ce33e7deb94");
        assertThat(nextStartedService).isEqualTo(expectedIntent);
    }

    @Test
    public void showsLoadedGroups() {
        SelectGroup activity = createDefaultSelectGroupActivity();

        eventBus.post(new OfficeGroupsFetched(Collections.singletonList(new OfficeGroup())));

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

    private SelectGroup createDefaultSelectGroupActivity() {
        OfficeGroupsFetcher officeGroupsFetcher = mock(OfficeGroupsFetcher.class);
        testApplication = new TestApplication(officeGroupsFetcher);
        Intent intent = new Intent().putExtra(SelectGroup.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        return createSelectGroupActivity(intent);
    }

    private SelectGroup createSelectGroupActivity(Intent intent) {
        return Robolectric.buildActivity(SelectGroup.class)
                .withIntent(intent).withApplication(testApplication)
                .create().visible().resume().get();
    }

}