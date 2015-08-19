package pl.pragmatists.cityofficenumbers.officegroups.json;

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
}