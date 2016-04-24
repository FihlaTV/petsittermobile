package zekisanmobile.petsitter.api.body;

import java.util.List;

public class SearchSittersBody {

    //region Members
    List<String> animals;
    //endregion

    //region Accessors
    public List<String> getAnimals() {
        return animals;
    }

    public void setAnimals(List<String> animals) {
        this.animals = animals;
    }
    //endregion
}
