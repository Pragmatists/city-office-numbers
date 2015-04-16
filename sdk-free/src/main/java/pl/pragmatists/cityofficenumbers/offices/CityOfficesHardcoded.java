package pl.pragmatists.cityofficenumbers.offices;

import java.util.Arrays;
import java.util.List;

public class CityOfficesHardcoded implements CityOfficesModel {

    @Override
    public Office[] offices() {
        return new Office[] {
                new Office("USC Andersa"),
                new Office("USC Falęcka"),
                new Office("UD Białołęka"),
                new Office("UD Bielany"),
                new Office("UD Ochota"),
                new Office("UD Wola"),
                new Office("UD Żoliborz")
        };
    }
}
