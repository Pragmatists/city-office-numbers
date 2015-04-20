package pl.pragmatists.cityofficenumbers.app;

import java.util.Collection;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class GroupsLoader extends AsyncTaskLoader<Collection<OfficeGroup>> {
    private final String officeId;

    private final RestTemplate restTemplate;

    public GroupsLoader(Context context, String officeId, RestTemplate restTemplate) {
        super(context);
        this.officeId = officeId;
        this.restTemplate = restTemplate;
    }

    @Override
    public Collection<OfficeGroup> loadInBackground() {
        final String url = "https://api.um.warszawa.pl/api/action/wsstore_get/?id=" + officeId;
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        OfficeGroupsResult officeGroupsResult = restTemplate.getForObject(url, OfficeGroupsResult.class);
        return officeGroupsResult.officeGroups();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
