package zekisanmobile.petsitter.Owner;

import java.util.ArrayList;

import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.vo.Sitter;

public interface OwnerHomeView {

    void updateSitterList(ArrayList<Sitter> sitters);

    PetSitterApp getPetSitterApp();
}
