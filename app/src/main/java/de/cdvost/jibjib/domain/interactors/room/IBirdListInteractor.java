package de.cdvost.jibjib.domain.interactors.room;

import java.util.List;

import de.cdvost.jibjib.domain.interactors.base.Interactor;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IBirdListInteractor extends Interactor {
    //this interface is implemented by the presenter
    //to receive the callback information
    interface Callback{
        public void birdListRetrieved(List<Bird> birdList);
        public void onExecutionFailed(Object fail);
    }
}
