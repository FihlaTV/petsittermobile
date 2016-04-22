package zekisanmobile.petsitter.api;

import java.util.List;

public class SearchSittersBody {

    List<String> animals;

    public List<String> getAnimals() {
        return animals;
    }

    public void setAnimals(List<String> animals) {
        this.animals = animals;
    }
}
