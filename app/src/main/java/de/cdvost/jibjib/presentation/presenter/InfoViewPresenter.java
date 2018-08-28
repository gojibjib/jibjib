package de.cdvost.jibjib.presentation.presenter;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;
import de.cdvost.jibjib.presentation.view.InfoView;

public class InfoViewPresenter extends AbstractPresenter implements IInfoViewPresenter {

    private IInfoViewPresenter.View view;

    public InfoViewPresenter(Executor executor, MainThread mainThread, InfoView infoView) {
        super(executor, mainThread);
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
