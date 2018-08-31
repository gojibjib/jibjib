package de.cdvost.jibjib.presentation.presenter;

import android.content.Context;

import java.util.List;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.room.IBirdListInteractor;
import de.cdvost.jibjib.domain.interactors.room.impl.BirdListInteractor;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class BirdListViewPresenter extends AbstractPresenter implements IBirdListViewPresenter, IBirdListInteractor.Callback {

    private IBirdListViewPresenter.View view;

    public BirdListViewPresenter(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void birdListRetrieved(List<Bird> birdList) {
        view.hideProgress();
        view.showBirdList(birdList);
    }

    @Override
    public void getBirdList(Context context) {
        //start background thread
        view.showProgress();
        new BirdListInteractor(executor, mainThread, this, context).execute();
    }

    @Override
    public void onExecutionFailed(Object fail) {

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
        view.showError(message);
    }
}
