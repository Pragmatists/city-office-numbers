package pl.pragmatists.cityofficenumbers.app.selectoffice;

public class Office {
    public String name;

    public String id;

    public boolean favorite;

    public Office() {

    }

    public Office(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Office id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public Office favorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public void toggleFavorite() {
        favorite = !favorite;
    }
}
