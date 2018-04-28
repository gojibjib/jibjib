package de.cdvost.jibjib.domain.interactors.room.impl;

import android.content.Context;

import java.util.List;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.room.IBirdListInteractor;
import de.cdvost.jibjib.repository.room.IRoomDataBaseRepository;
import de.cdvost.jibjib.repository.room.RoomDataBaseRepository;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class BirdListInteractor extends AbstractInteractor implements IBirdListInteractor {

    private IRoomDataBaseRepository repository;
    private IBirdListInteractor.Callback callback;
    private Context context;

    public BirdListInteractor(Executor threadExecutor, MainThread mainThread, Callback callback,
    Context context) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.context = context;
        repository = new RoomDataBaseRepository();
    }

    @Override
    public void run(){
        List<Bird> birdList = getBirdList();
        mainThread.post(()->callback.birdListRetrieved(birdList));
    }

    private List<Bird> getBirdList() {
        return repository.getListOfBirds(context);
    }
}
