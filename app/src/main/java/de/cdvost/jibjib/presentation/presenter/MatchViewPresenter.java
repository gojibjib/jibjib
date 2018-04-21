package de.cdvost.jibjib.presentation.presenter;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.IMatchSoundInteractor;
import de.cdvost.jibjib.domain.interactors.MatchSoundInteractorImpl;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;

public class MatchViewPresenter extends AbstractPresenter implements IMatchViewPresenter, IMatchSoundInteractor.Callback {

    private IMatchViewPresenter.View view;

    public MatchViewPresenter(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void matchSound(Object audio) {
        //starts the long-running method
        //results will be returned in the callback method (onMatchFinished())
        view.showProgress();
        new MatchSoundInteractorImpl(executor, mainThread, audio, this).execute();
    }

    @Override
    public void onMatchFinished(Object matchResults) {
        view.hideProgress();
        view.showMatchResults(matchResults); ;
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
