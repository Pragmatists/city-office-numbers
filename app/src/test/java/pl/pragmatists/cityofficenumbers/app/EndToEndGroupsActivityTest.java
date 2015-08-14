package pl.pragmatists.cityofficenumbers.app;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import android.app.Application;
import android.content.Intent;
import android.widget.ListView;
import pl.pragmatists.cityofficenumbers.events.BusInstance;
import pl.pragmatists.cityofficenumbers.officegroups.OfficeGroupsFetcher;
import pl.pragmatists.cityofficenumbers.offices.TestApplication;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = "src/main/AndroidManifest.xml")
public class EndToEndGroupsActivityTest {

    private Application testApplication;
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    private OfficeGroupsFetcher createOfficeGroupsFetcher() {
        return new OfficeGroupsFetcher(new RestClientWithOkHttp(getHost()), BusInstance.instance());
    }

    private Host getHost() {
        return new Host("http", server.getHostName(), server.getPort());
    }

    @Test
    public void shouldFetchGroupsOnCreate() throws IOException {
        server.enqueue(new MockResponse().setBody("" +
                "{\n" +
                "    \"result\": {\n" +
                "        \"date\": \"2015-04-19\",\n" +
                "        \"grupy\": [\n" +
                "            {\n" +
                "                \"status\": null,\n" +
                "                \"czasObslugi\": \"0\",\n" +
                "                \"lp\": null,\n" +
                "                \"idGrupy\": \"1\",\n" +
                "                \"liczbaCzynnychStan\": 0,\n" +
                "                \"nazwaGrupy\": \"URODZENIA\",\n" +
                "                \"literaGrupy\": \"U\",\n" +
                "                \"liczbaKlwKolejce\": 0,\n" +
                "                \"aktualnyNumer\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"time\": \"16:36\"\n" +
                "    }\n" +
                "} \n"));
        server.start();
        OfficeGroupsFetcher officeGroupsFetcher = createOfficeGroupsFetcher();

        testApplication = new TestApplication(null, officeGroupsFetcher);
        Intent intent = new Intent().putExtra(SelectGroup.ARG_OFFICE_ID, "5d2e698a-9c31-456b-8452-7ce33e7deb94");

        Robolectric.getBackgroundThreadScheduler().pause();
        SelectGroup activity = createSelectGroupActivity(intent);

//        assertThat(Robolectric.getBackgroundThreadScheduler().size()).isEqualTo(1);
        Robolectric.getBackgroundThreadScheduler().runOneTask();
        ShadowApplication.runBackgroundTasks();

        ListView listView = (ListView) activity.findViewById(R.id.office_groups);
        assertThat(listView.getCount()).isEqualTo(1);
    }

    private SelectGroup createSelectGroupActivity(Intent intent) {
        return Robolectric.buildActivity(SelectGroup.class)
                .withIntent(intent).withApplication(testApplication)
                .create().visible().resume().get();
    }
}