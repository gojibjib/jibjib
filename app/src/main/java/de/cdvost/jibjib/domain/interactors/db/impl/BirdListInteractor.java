package de.cdvost.jibjib.domain.interactors.db.impl;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.db.BaseDBInteractor;
import de.cdvost.jibjib.domain.interactors.db.IBirdListInteractor;

public class BirdListInteractor extends BaseDBInteractor implements IBirdListInteractor {

    public BirdListInteractor(Executor threadExecutor, MainThread mainThread, Callback callback) {
        super(threadExecutor, mainThread, callback);
    }

    @Override
    public void run() {
        Object birdList = getBirdList();
        executionFinished(birdList);
    }

    private Object getBirdList() {
        return repository.getListOfBirds();
    }
}
