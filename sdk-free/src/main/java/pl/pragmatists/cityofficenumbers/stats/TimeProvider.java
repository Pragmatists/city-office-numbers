package pl.pragmatists.cityofficenumbers.stats;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeProvider {

    public Date getNow() {
        return new Date();
    }

    public Date todayHigh() {
        Calendar date = new GregorianCalendar();
        date.setTime(getNow());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.add(Calendar.DAY_OF_MONTH, 1);
        return date.getTime();
    }

    public Date todayLow() {
        Calendar date = new GregorianCalendar();
        date.setTime(getNow());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }
}
