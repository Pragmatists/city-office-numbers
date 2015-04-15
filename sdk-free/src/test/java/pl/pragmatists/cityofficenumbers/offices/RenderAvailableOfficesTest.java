package pl.pragmatists.cityofficenumbers.offices;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RenderAvailableOfficesTest {


    @Mock
    private CityOfficesModel model;
    @Mock
    private CityOfficesView view;

    @Test
    public void no_offices_available() {
        CityOfficesPresenter presenter = new CityOfficesPresenter(model, view);
        when(model.offices()).thenReturn(Collections.<Office>emptyList());
        presenter.render();
        verify(view).showNoOfficesAvailableMessage();
    }

    @Test
    public void pass_all_offices_to_view() {
        CityOfficesPresenter presenter = new CityOfficesPresenter(model, view);
        List<Office> offices = Arrays.asList(new Office("USC Andersa"), new Office("UD Wola"));
        when(model.offices()).thenReturn(offices);
        presenter.render();
        verify(view).showOffices(offices);
    }

}