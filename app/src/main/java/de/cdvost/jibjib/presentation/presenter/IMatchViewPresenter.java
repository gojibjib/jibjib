package de.cdvost.jibjib.presentation.presenter;

import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;

public interface IMatchViewPresenter extends IPresenter {
    interface View extends BaseView {
        public void showMatchResults(List<MatchResult> results);
    }

    public void matchSound(Object audio);
}
