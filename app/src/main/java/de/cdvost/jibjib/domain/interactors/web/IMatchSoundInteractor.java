package de.cdvost.jibjib.domain.interactors.web;

import java.util.List;

import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;

public interface IMatchSoundInteractor {
    //this interface is implemented by the presenter
    //to receive the callback information
    interface Callback{
        public void onMatchingFinished(List<MatchResult> results);
        public void onExecutionFailed(Object fail);
    }

}
