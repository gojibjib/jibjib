package de.cdvost.jibjib.presentation.presenter;

import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;

public interface ISoundPrepPresenter extends IPresenter {

    interface View extends BaseView {

        public void updateVisualization();
    }

    public void cutSound(float start, float end);

    public void editDone();
}
