package de.cdvost.jibjib.domain.interactors.web;

import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;

public interface IMatchSoundInteractor {

    //this interface is implemented by the presenter
    //to receive the callback information
    interface Callback{
        public void onMatchFinished(Object matchResults);
    }
}
