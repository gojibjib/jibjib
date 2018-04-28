package de.cdvost.jibjib.presentation.presenter;

import java.util.List;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.Interactor;
import de.cdvost.jibjib.domain.interactors.room.IStoreBirdInteractor;
import de.cdvost.jibjib.domain.interactors.web.IMatchSoundInteractor;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.domain.interactors.web.impl.MatchSoundInteractorImpl;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;

public class MatchViewPresenter extends AbstractPresenter
        implements IMatchViewPresenter, IMatchSoundInteractor.Callback,
        IStoreBirdInteractor.Callback {

    private IMatchViewPresenter.View view;

    public MatchViewPresenter(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void matchSound(Object audio) {
        //starts the long-running method
        //results will be returned in the callback method (onMatchingFinished())
        view.showProgress();
        new MatchSoundInteractorImpl(executor, mainThread, audio, this).execute();
    }

    @Override
    public void onMatchingFinished(List<MatchResult> matchResults) {
        view.hideProgress();
        view.showMatchResults(matchResults); ;
    }

    @Override
    public void onStoreComplete(boolean success) {

    }

    @Override
    public void onExecutionFailed(Object fail) {
        view.hideProgress();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
