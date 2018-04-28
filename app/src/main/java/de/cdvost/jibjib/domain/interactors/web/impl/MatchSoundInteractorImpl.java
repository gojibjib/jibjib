package de.cdvost.jibjib.domain.interactors.web.impl;

import java.util.List;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.web.IMatchSoundInteractor;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.repository.web.BirdWebServiceImpl;
import de.cdvost.jibjib.repository.web.IBirdWebService;

public class MatchSoundInteractorImpl extends AbstractInteractor implements IMatchSoundInteractor {

    private final Object audio;
    private final IMatchSoundInteractor.Callback callback;

    protected IBirdWebService birdWebService;

    public MatchSoundInteractorImpl(Executor executor, MainThread mainThread,
                                    Object audio, IMatchSoundInteractor.Callback callback) {
        super(executor, mainThread);
        this.callback = callback;
        this.audio = audio;
        birdWebService = new BirdWebServiceImpl();
    }

    private String matchBirdSound(Object audio) {
        return birdWebService.match(audio);
    }


    private void executionFinished(List<MatchResult> results){
        mainThread.post(()->callback.onMatchingFinished(results));
    }

    @Override
    public void run() {
        String serviceResponse = matchBirdSound(audio);
        List<MatchResult> results = MatchResultParser.parse(serviceResponse);
        executionFinished(results);
    }
}
