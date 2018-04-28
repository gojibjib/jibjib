package de.cdvost.jibjib.domain.interactors.room;

import java.util.List;

import de.cdvost.jibjib.domain.interactors.base.Interactor;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IStoreBirdInteractor extends Interactor {
    interface Callback{
        public void onStoreComplete(boolean success);
    }
}
