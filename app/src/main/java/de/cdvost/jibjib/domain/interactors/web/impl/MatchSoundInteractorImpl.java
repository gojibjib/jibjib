package de.cdvost.jibjib.domain.interactors.web.impl;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.base.Interactor;
import de.cdvost.jibjib.domain.interactors.web.BaseWebInteractor;
import de.cdvost.jibjib.domain.interactors.web.IMatchSoundInteractor;
import de.cdvost.jibjib.repository.web.BirdWebServiceImpl;
import de.cdvost.jibjib.repository.web.IBirdWebService;

public class MatchSoundInteractorImpl extends BaseWebInteractor implements IMatchSoundInteractor {

    private final Object audio;

    public MatchSoundInteractorImpl(Executor executor, MainThread mainThread,
                                    Object audio, Interactor.Callback callback) {
        super(executor, mainThread, callback);
        this.audio = audio;
    }

    private Object matchBirdSound(Object audio) {
        return birdWebService.match(audio);
    }

    @Override
    public void run() {
        Object result = matchBirdSound(audio);
        executionFinished(result);
    }
}
