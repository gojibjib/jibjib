package de.cdvost.jibjib.domain.interactors.web;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.repository.web.BirdWebServiceImpl;
import de.cdvost.jibjib.repository.web.IBirdWebService;

public class MatchSoundInteractorImpl extends AbstractInteractor implements IMatchSoundInteractor {

    private final Object audio;
    private final MatchSoundInteractorImpl.Callback callback;
    private IBirdWebService birdWebService;

    public MatchSoundInteractorImpl(Executor executor, MainThread mainThread,
                                    Object audio, IMatchSoundInteractor.Callback callback) {
        super(executor, mainThread);
        this.birdWebService = new BirdWebServiceImpl();
        this.audio = audio;
        this.callback = callback;
    }

    private Object matchBirdSound(Object audio) {
        return birdWebService.match(audio);
    }

    private void matchingDone(Object result){
        mainThread.post(()->callback.onMatchFinished(result));
    }

    @Override
    public void run() {
        Object result = matchBirdSound(audio);
        matchingDone(result);
    }
}
