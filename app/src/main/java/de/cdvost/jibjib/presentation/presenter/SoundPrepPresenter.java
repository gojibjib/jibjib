package de.cdvost.jibjib.presentation.presenter;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;

public class SoundPrepPresenter extends AbstractPresenter {

    public SoundPrepPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }
}
