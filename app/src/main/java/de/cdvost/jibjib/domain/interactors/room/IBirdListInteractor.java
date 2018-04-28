package de.cdvost.jibjib.domain.interactors.room;

import de.cdvost.jibjib.domain.interactors.base.Interactor;

public interface IBirdListInteractor extends Interactor {
    //this interface is implemented by the presenter
    //to receive the callback information
    interface Callback{
        public void onExecutionFinished(Object result);
        public void onExecutionFailed(Object fail);
    }
}
