package de.cdvost.jibjib.domain.interactors.room;

import de.cdvost.jibjib.domain.interactors.base.Interactor;

public interface IStoreBirdInteractor extends Interactor {
    interface Callback{
        public void onStoreComplete();
    }
}
