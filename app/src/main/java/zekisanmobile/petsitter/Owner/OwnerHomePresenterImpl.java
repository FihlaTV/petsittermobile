package zekisanmobile.petsitter.Owner;

import javax.inject.Inject;

import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.vo.Owner;

public class OwnerHomePresenterImpl implements OwnerHomePresenter{

    private Owner owner;
    private OwnerHomeView view;

    @Inject
    OwnerModel ownerModel;

    public OwnerHomePresenterImpl(OwnerHomeView view){
        view.getPetSitterApp().getAppComponent().inject(this);
        this.view = view;
        this.owner = ownerModel.getLoggedOwnerUser();
    }

    @Override
    public long getOwnerApiId() {
        return owner.getApiId();
    }

    @Override
    public String getLoggedUserName() {
        return owner.getName();
    }

    @Override
    public String getLoggedUserEmail() {
        return owner.getUser().getEmail();
    }

    @Override
    public String getLoggedUserPhoto() {
        return owner.getUser().getPhoto();
    }
}
