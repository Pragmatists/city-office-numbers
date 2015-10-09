package pl.pragmatists.cityofficenumbers.stats;

import java.util.Date;

public class FakeTimeProvider extends TimeProvider {
    private Date today;

    public void todayIs(Date date) {
        this.today = date;
    }

    @Override
    public Date getNow() {
        return today;
    }
}
