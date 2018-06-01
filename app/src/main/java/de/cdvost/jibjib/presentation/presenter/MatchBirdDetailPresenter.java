package de.cdvost.jibjib.presentation.presenter;


import android.content.Context;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.room.IStoreBirdInteractor;
import de.cdvost.jibjib.domain.interactors.room.impl.StoreBirdInteractor;
import de.cdvost.jibjib.domain.interactors.web.IGetBirdDetailsInteractor;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;


public class MatchBirdDetailPresenter extends AbstractPresenter implements IMatchBirdDetailPresenter, IGetBirdDetailsInteractor.Callback, IStoreBirdInteractor.Callback {

    private IMatchBirdDetailPresenter.View view;

    public MatchBirdDetailPresenter(Executor executor, MainThread mainThread, IMatchBirdDetailPresenter.View view) {
        super(executor, mainThread);
        this.view = view;
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

    @Override
    public void getMatchBird(int id) {

    }

    @Override
    public void saveBird(Bird bird, Context context) {
        new StoreBirdInteractor(executor, mainThread, this, bird, context).execute();
    }

    @Override
    public void onBirdReceived(Bird bird) {

    }

    @Override
    public void onExecutionFailed(Object fail) {

    }

    @Override
    public void onStoreComplete() {
        view.showBirdSavedHint();
    }
}
