package pl.pragmatists.cityofficenumbers.app;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Intent;
import pl.pragmatists.cityofficenumbers.events.BusInstance;

@RunWith(RobolectricTestRunner.class)
public class GroupIntentServiceTest {

    private OfficeGroupsFetched officeGroupsFetched;

    @Before
    public void setUp() throws Exception {
        BusInstance.instance().register(this);
    }
    @After
    public void tearDown() throws Exception {
        BusInstance.instance().unregister(this);
    }

    @Test
    public void shouldFetchGroups() {
        GroupIntentService service = new GroupIntentService();
        service.onCreate();
        Intent intent = new Intent();
        intent.putExtra(GroupIntentService.OFFICE_ID_KEY, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        service.onHandleIntent(intent);

        assertThat(officeGroupsFetched.groups()).isNotEmpty();

    }

    public void onEvent(OfficeGroupsFetched officeGroupsFetched) {

        this.officeGroupsFetched = officeGroupsFetched;
    }

}