package zekisanmobile.petsitter.view.owner;

import javax.inject.Inject;

import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.vo.Owner;

public class RatePresenterImpl implements RatePresenter{

    //region Members
    Owner owner;

    RateView view;

    @Inject
    OwnerModel ownerModel;
    //endregion

    //region Constructors
    public RatePresenterImpl(RateView view) {
        this.view = view;
        view.getPetSitterApp().getAppComponent().inject(this);
        this.owner = ownerModel.getLoggedOwnerUser();
    }
    //endregion

    //region Inherited Methods
    @Override
    public long getLoggedUserId() {
        return owner.getId();
    }

    @Override
    public String getLoggedUserPhoto() {
        return owner.getUser().getPhoto();
    }
    //endregion
}