package de.cdvost.jibjib.presentation.presenter;

import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;

public interface IMatchViewPresenter extends IPresenter {
    interface View extends BaseView {
        public void showMatchResults(Object results);
    }

    public void matchSound(Object audio);
}
