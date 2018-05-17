package de.cdvost.jibjib.domain.interactors.web;


import de.cdvost.jibjib.domain.interactors.web.dto.BirdResult;

public interface IGetBirdDetailsInteractor {

    interface Callback {
        public void onBirdReceived(BirdResult result);

        public void onExecutionFailed(Object fail);
    }
}
