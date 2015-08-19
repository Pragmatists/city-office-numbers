package pl.pragmatists.cityofficenumbers.enternumber;

import static org.mockito.Mockito.*;

import org.junit.Test;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;

public class EnterNumberPresenterTest {

    private EnterNumberView view = mock(EnterNumberView.class);

    @Test
    public void show_entered_number_with_a_prefix() {
        OfficeGroup officeGroup = new OfficeGroup().groupLetter("A");
        EnterNumberPresenter presenter = new EnterNumberPresenter(officeGroup, view);

        presenter.numberEntered("123");

        verify(view).setUsersNumber("A123");
    }

    @Test
    public void show_calculated_queue_size() {
        OfficeGroup officeGroup = new OfficeGroup().currentNumber(30);
        EnterNumberPresenter presenter = new EnterNumberPresenter(officeGroup, view);

        presenter.numberEntered("35");

        verify(view).setQueueBeforeSize("5");
    }

    @Test
    public void show_calculated_waiting_time() {
        OfficeGroup officeGroup = new OfficeGroup().currentNumber(30).serviceTime(3);
        EnterNumberPresenter presenter = new EnterNumberPresenter(officeGroup, view);

        presenter.numberEntered("35");

        verify(view).setExpectedTime("15");
    }

    @Test
    public void ignores_empty_number_entered() {
        EnterNumberPresenter presenter = new EnterNumberPresenter(null, view);

        presenter.numberEntered("");

        verifyNoMoreInteractions(view);
    }
}