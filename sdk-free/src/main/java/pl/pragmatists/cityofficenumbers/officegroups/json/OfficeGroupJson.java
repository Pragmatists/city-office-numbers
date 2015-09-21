package pl.pragmatists.cityofficenumbers.officegroups.json;

import pl.pragmatists.cityofficenumbers.groups.OfficeGroup;

public class OfficeGroupJson {
    public String status;
    public String czasObslugi;
    public String lp;
    public int idGrupy;
    public Integer liczbaCzynnychStan;
    public String nazwaGrupy;
    public String literaGrupy;
    public Integer liczbaKlwKolejce;
    public Integer aktualnyNumer;

    @Override
    public String toString() {
        return nazwaGrupy;
    }

    public OfficeGroupJson withId(int groupId) {
        idGrupy = groupId;
        return this;
    }

    public OfficeGroup toOfficeGroup() {
        return new OfficeGroup()
                        .name(nazwaGrupy)
                        .groupId(idGrupy)
                        .currentNumber(aktualnyNumer)
                        .groupLetter(literaGrupy)
                        .queueSize(liczbaKlwKolejce)
                        .serviceTime(czasObslugi == null ? 0 : Integer.parseInt(czasObslugi));
    }
}
