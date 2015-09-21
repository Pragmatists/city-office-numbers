package pl.pragmatists.cityofficenumbers.enternumber;

import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.Test;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.officegroups.json.OfficeGroupJson;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;

public class EnterNumberPresenterTest {

    private EnterNumberView view = mock(EnterNumberView.class);

    private EventBus bus = mock(EventBus.class);

    @Test
    public void show_entered_number_with_a_prefix() {
        OfficeGroup officeGroup = new OfficeGroup().groupLetter("A");
        EnterNumberPresenter presenter = new EnterNumberPresenter(officeGroup, view, bus);

        presenter.numberEntered("123");

        verify(view).setUsersNumber("A123");
    }

    @Test
    public void show_calculated_queue_size() {
        OfficeGroup officeGroup = new OfficeGroup().currentNumber(30);
        EnterNumberPresenter presenter = new EnterNumberPresenter(officeGroup, view, bus);

        presenter.numberEntered("35");

        verify(view).setQueueBeforeSize("5");
    }

    @Test
    public void show_calculated_waiting_time() {
        OfficeGroup officeGroup = new OfficeGroup().currentNumber(30).serviceTime(3);
        EnterNumberPresenter presenter = new EnterNumberPresenter(officeGroup, view, bus);

        presenter.numberEntered("35");

        verify(view).setExpectedTime("15");
    }

    @Test
    public void ignores_empty_number_entered() {
        EnterNumberPresenter presenter = new EnterNumberPresenter(null, view, bus);

        presenter.numberEntered("");

        verifyNoMoreInteractions(view);
    }

    @Test
    public void updates_calculated_values_on_new_data() {
        EnterNumberPresenter presenter = new EnterNumberPresenter(5, view, bus);
        when(view.getUserNumber()).thenReturn("35");
        OfficeGroupJson officeGroup = new OfficeGroupJson();
        officeGroup.aktualnyNumer = 30;
        officeGroup.liczbaKlwKolejce = 12;
        officeGroup.idGrupy = 5;

        presenter.onEventMainThread(new OfficeGroupsFetched(Collections.singletonList(officeGroup)));

        verify(view).setQueueBeforeSize("5");
    }

    @Test
    public void requests_new_stats_calculation_after_receiving_new_data() {
        EnterNumberPresenter presenter = new EnterNumberPresenter(5, view, bus);
        someNumberIsEntered();

        presenter.onEventMainThread(new OfficeGroupsFetched(Collections.singletonList(aGroupWithId(5))));

        verify(bus).post(new RequestStatsUpdate(5));
    }

    private void someNumberIsEntered() {
        when(view.getUserNumber()).thenReturn("35");
    }

    private OfficeGroupJson aGroupWithId(int id) {
        OfficeGroupJson officeGroup = new OfficeGroupJson();
        officeGroup.aktualnyNumer = 30;
        officeGroup.liczbaKlwKolejce = 12;
        officeGroup.idGrupy = id;
        return officeGroup;
    }
}