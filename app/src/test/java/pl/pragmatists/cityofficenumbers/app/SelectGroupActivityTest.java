package pl.pragmatists.cityofficenumbers.app;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import android.app.Application;
import android.content.Intent;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsNetworkError;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsServerError;
import pl.pragmatists.cityofficenumbers.offices.TestApplication;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class SelectGroupActivityTest {

    private Application testApplication;

    @Test
    public void shouldFetchGroupsOnCreate() {
        OfficeGroupsFetcher officeGroupsFetcher = mock(OfficeGroupsFetcher.class);
        testApplication = new TestApplication(null, officeGroupsFetcher);
        Intent intent = new Intent().putExtra(SelectGroup.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        SelectGroup activity = createSelectGroupActivity(intent);

        Intent nextStartedService = Shadows.shadowOf(activity).getNextStartedService();
        Intent expectedIntent = new Intent(activity, GroupIntentService.class)
                .putExtra(GroupIntentService.OFFICE_ID_KEY, "5d2e698a-9c31-456b-8452-7ce33e7deb94");
        assertThat(nextStartedService).isEqualTo(expectedIntent);
    }

    @Test
    public void showsAToastOnServerError() {
        createDefaultSelectGroupActivity();

        BusInstance.instance().post(new OfficeGroupsServerError("details"));

        assertThat(ShadowToast.getTextOfLatestToast()).contains("details");
    }

    @Test
    public void showsAToastOnNetworkError() {
        createDefaultSelectGroupActivity();

        BusInstance.instance().post(new OfficeGroupsNetworkError());

        assertThat(ShadowToast.getTextOfLatestToast()).contains("Nieudane połączenie z serwerem.");
    }

    private SelectGroup createDefaultSelectGroupActivity() {
        OfficeGroupsFetcher officeGroupsFetcher = mock(OfficeGroupsFetcher.class);
        testApplication = new TestApplication(null, officeGroupsFetcher);
        Intent intent = new Intent().putExtra(SelectGroup.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        return createSelectGroupActivity(intent);
    }

    private SelectGroup createSelectGroupActivity(Intent intent) {
        return Robolectric.buildActivity(SelectGroup.class)
                .withIntent(intent).withApplication(testApplication)
                .create().visible().resume().get();
    }

}