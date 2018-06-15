package de.cdvost.jibjib.presentation.presenter;

import android.content.Context;

import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IMatchBirdDetailPresenter extends IPresenter {
    interface View extends BaseView {
        public void showMatchBirdDetail(Bird bird);

        public void showBirdSavedHint();
    }

    public void saveBird(Bird bird, Context context);
}
