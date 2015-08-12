package pl.pragmatists.cityofficenumbers.app;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import android.app.Application;
import android.content.Intent;
import pl.pragmatists.cityofficenumbers.offices.OfficeGroupsFetcher;
import pl.pragmatists.cityofficenumbers.offices.TestApplication;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class GroupIntentServiceTest {

    private Application testApplication;

    @Test
    public void shouldFetchGroups() {
        OfficeGroupsFetcher officeGroupsFetcher = mock(OfficeGroupsFetcher.class);
        testApplication = new TestApplication(null, officeGroupsFetcher);
        Intent intent = new Intent();
        intent.putExtra(SelectGroup.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");
        SelectGroup activity = createGroupsIntentService(intent).get();

        Intent nextStartedService = Shadows.shadowOf(activity).getNextStartedService();
        Intent expectedIntent = new Intent(activity, GroupIntentService.class)
                .putExtra(GroupIntentService.OFFICE_ID_KEY, "5d2e698a-9c31-456b-8452-7ce33e7deb94");
        assertThat(nextStartedService).isEqualTo(expectedIntent);

    }

    private ActivityController<SelectGroup> createGroupsIntentService(Intent intent) {
        return Robolectric.buildActivity(SelectGroup.class)
                .withIntent(intent).withApplication(testApplication)
                .create().visible().resume();
    }

}