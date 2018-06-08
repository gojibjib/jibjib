package de.cdvost.jibjib.domain.interactors.web;


import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IGetBirdDetailsInteractor {

    interface Callback {
        public void onBirdReceived(Bird bird);

        public void onExecutionFailed(Object fail);
    }
}
