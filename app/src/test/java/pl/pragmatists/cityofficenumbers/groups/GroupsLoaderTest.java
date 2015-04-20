package pl.pragmatists.cityofficenumbers.groups;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import pl.pragmatists.cityofficenumbers.app.BuildConfig;
import pl.pragmatists.cityofficenumbers.app.GroupsLoader;
import pl.pragmatists.cityofficenumbers.app.OfficeGroup;
import pl.pragmatists.cityofficenumbers.app.SelectGroup;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class GroupsLoaderTest {

    @Test
    public void loadsGroups() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(requestTo("https://api.um.warszawa.pl/api/action/wsstore_get/?id=9c3d5770-57d8-4365-994c-69c5ac4186ee"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("" +
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
                        "} \n", MediaType.APPLICATION_JSON));

        SelectGroup selectGroup = Robolectric.buildActivity(SelectGroup.class).get();
        GroupsLoader groupsLoader = new GroupsLoader(selectGroup, "9c3d5770-57d8-4365-994c-69c5ac4186ee", restTemplate);
        Collection<OfficeGroup> officeGroups = groupsLoader.loadInBackground();
        Assertions.assertThat(officeGroups).hasSize(1);
    }
}
