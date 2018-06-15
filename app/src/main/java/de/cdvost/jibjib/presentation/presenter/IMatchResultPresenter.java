package de.cdvost.jibjib.presentation.presenter;

import android.content.Context;

import java.io.File;
import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;

public interface IMatchResultPresenter extends IPresenter {
    interface View extends BaseView {
        public void showMatchResults(List<MatchedBird> results);
    }
}
