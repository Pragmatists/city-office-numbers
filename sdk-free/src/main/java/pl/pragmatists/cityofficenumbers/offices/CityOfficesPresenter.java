package pl.pragmatists.cityofficenumbers.offices;

public class CityOfficesPresenter {
    private final CityOfficesModel model;

    private final CityOfficesView view;

    public CityOfficesPresenter(CityOfficesModel model, CityOfficesView view) {
        this.model = model;
        this.view = view;
    }

    public void render() {
        if (model.offices().isEmpty()) {
            view.showNoOfficesAvailableMessage();
        }
        view.showOffices(model.offices());
    }
}
