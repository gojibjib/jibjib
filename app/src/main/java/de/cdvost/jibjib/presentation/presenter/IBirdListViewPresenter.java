package de.cdvost.jibjib.presentation.presenter;

import android.content.Context;

import java.util.List;

import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IBirdListViewPresenter extends IPresenter{
    interface View extends BaseView{
        public void showBirdList(List<Bird> birdList);
    }

    public void getBirdList(Context context);
}
