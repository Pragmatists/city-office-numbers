package pl.pragmatists.cityofficenumbers.enternumber;

import static org.mockito.Mockito.*;
import static pl.pragmatists.cityofficenumbers.builders.OfficeGroupBuilder.anOfficeGroup;

import org.junit.Test;

import pl.pragmatists.cityofficenumbers.events.EventBus;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;
import pl.pragmatists.cityofficenumbers.groups.OfficeGroups;
import pl.pragmatists.cityofficenumbers.officegroups.messages.OfficeGroupsFetched;
import pl.pragmatists.cityofficenumbers.builders.OfficeGroupsBuilder;
import pl.pragmatists.cityofficenumbers.stats.events.StatsUpdate;

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
        OfficeGroups officeGroups = OfficeGroupsBuilder.withOneGroup(anOfficeGroup().withId(5).withCurrentNumber(30).build()).build();

        presenter.onEventMainThread(new OfficeGroupsFetched(officeGroups));

        verify(view).setQueueBeforeSize("5");
    }

    @Test
    public void requests_new_stats_calculation_after_receiving_new_data() {
        EnterNumberPresenter presenter = new EnterNumberPresenter(5, view, bus);
        someNumberIsEntered();
        OfficeGroups officeGroups = OfficeGroupsBuilder.withOneGroup(anOfficeGroup().withId(5).build()).build();

        presenter.onEventMainThread(new OfficeGroupsFetched(officeGroups));

        verify(bus).post(new RequestStatsUpdate(5, null));
    }

    @Test
    public void updates_average_queue_size_on_stats_update() {
        EnterNumberPresenter presenter = new EnterNumberPresenter(5, view, bus);

        presenter.onEventMainThread(new StatsUpdate().averageQueueSize(36));

        verify(view).setAverageQueueSize("36");
    }

    private void someNumberIsEntered() {
        when(view.getUserNumber()).thenReturn("35");
    }

}