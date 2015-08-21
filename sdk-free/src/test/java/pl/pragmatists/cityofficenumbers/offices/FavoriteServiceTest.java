package pl.pragmatists.cityofficenumbers.offices;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.http.Host;
import pl.pragmatists.http.RestClientWithOkHttp;

public class FavoriteServiceTest {
    private EventBus bus = mock(EventBus.class);

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void makes_a_request_to_toggle_office_favorite_state() throws InterruptedException {
        FavoriteService favoriteService = new FavoriteService(new RestClientWithOkHttp(new Host("http", server.getHostName(), server.getPort())));
        Office office = new Office().id("office-id").favorite(true);
        server.enqueue(new MockResponse());

        favoriteService.toggleFavorite("user-id", office.id, office.favorite);

        RecordedRequest recordedRequest = server.takeRequest(1, TimeUnit.SECONDS);
        assertThat(recordedRequest.getRequestLine()).contains("/users/user-id/offices/office-id/favorite");
        assertThat(recordedRequest.getBody().readUtf8()).contains("\"favorite\":false");
    }
}
