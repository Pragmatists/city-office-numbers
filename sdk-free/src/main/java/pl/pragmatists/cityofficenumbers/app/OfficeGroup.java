package pl.pragmatists.cityofficenumbers.app;

public class OfficeGroup {
    public String status;
    public String czasObslugi;
    public String lp;
    public String idGrupy;
    public Integer liczbaCzynnychStan;
    public String nazwaGrupy;
    public String literaGrupy;
    public Integer liczbaKlwKolejce;
    public Integer aktualnyNumer;

    @Override
    public String toString() {
        return nazwaGrupy;
    }
}
