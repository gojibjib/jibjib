package de.cdvost.jibjib.domain.interactors.room.impl;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.room.BaseDBInteractor;
import de.cdvost.jibjib.domain.interactors.room.IBirdListInteractor;

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
