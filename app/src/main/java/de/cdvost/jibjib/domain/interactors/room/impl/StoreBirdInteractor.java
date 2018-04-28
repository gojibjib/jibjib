package de.cdvost.jibjib.domain.interactors.room.impl;

import android.content.Context;

import java.util.List;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.room.IBirdListInteractor;
import de.cdvost.jibjib.domain.interactors.room.IStoreBirdInteractor;
import de.cdvost.jibjib.repository.room.IRoomDataBaseRepository;
import de.cdvost.jibjib.repository.room.RoomDataBaseRepository;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class StoreBirdInteractor extends AbstractInteractor implements IStoreBirdInteractor {

    private IRoomDataBaseRepository repository;
    private Callback callback;
    private Context context;
    private Bird bird;

    public StoreBirdInteractor(Executor threadExecutor, MainThread mainThread, Callback callback,
                               Bird bird,
                               Context context) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.context = context;
        this.bird = bird;
        repository = new RoomDataBaseRepository();
    }

    @Override
    public void run(){
        boolean success = repository.storeBird(bird, context);
        mainThread.post(()->callback.onStoreComplete(success));
    }

}
