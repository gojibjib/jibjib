package de.cdvost.jibjib.domain.interactors.web;

import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IMatchSoundInteractor {
    //this interface is implemented by the presenter
    //to receive the callback information
    interface Callback{
        public void onMatchingFinished(List<Bird> results);
        public void onExecutionFailed(Object fail);
    }

}
