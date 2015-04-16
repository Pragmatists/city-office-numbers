package pl.pragmatists.cityofficenumbers.offices;

import java.util.Arrays;
import java.util.List;

public class CityOfficesHardcoded implements CityOfficesModel {
    @Override
    public List<Office> offices() {
        return Arrays.asList(
                new Office("USC Andersa"),
                new Office("USC Falęcka"),
                new Office("UD Białołęka"),
                new Office("UD Bielany"),
                new Office("UD Ochota"),
                new Office("UD Wola"),
                new Office("UD Żoliborz")
        );
    }

    public Office[] officesAsArray() {
        return (Office[]) offices().toArray();
    }
}
