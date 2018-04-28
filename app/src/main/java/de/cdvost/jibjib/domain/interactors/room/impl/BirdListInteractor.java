package de.cdvost.jibjib.domain.interactors.room.impl;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.room.IBirdListInteractor;
import de.cdvost.jibjib.repository.room.IRoomDataBaseRepository;
import de.cdvost.jibjib.repository.room.RoomDataBaseRepository;

public class BirdListInteractor extends AbstractInteractor implements IBirdListInteractor {

    protected IRoomDataBaseRepository repository;
    private  IBirdListInteractor.Callback callback;

    public BirdListInteractor(Executor threadExecutor, MainThread mainThread, Callback callback) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        repository = new RoomDataBaseRepository();
    }

    @Override
    public void run(){
        Object birdList = getBirdList();
        callback.onExecutionFinished(birdList);
    }

    private Object getBirdList() {
        return repository.getListOfBirds();
    }
}
