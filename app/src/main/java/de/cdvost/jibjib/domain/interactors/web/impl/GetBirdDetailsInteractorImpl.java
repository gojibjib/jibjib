package de.cdvost.jibjib.domain.interactors.web.impl;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.web.IGetBirdDetailsInteractor;
import de.cdvost.jibjib.domain.interactors.web.dto.BirdResult;
import de.cdvost.jibjib.repository.web.BirdWebServiceImpl;
import de.cdvost.jibjib.repository.web.IBirdWebService;


public class GetBirdDetailsInteractorImpl extends AbstractInteractor {

    private final int id;
    private final IGetBirdDetailsInteractor.Callback callback;

    protected IBirdWebService birdWebService;

    public GetBirdDetailsInteractorImpl(Executor executor, MainThread mainThread,
                                        int id, IGetBirdDetailsInteractor.Callback callback) {
        super(executor, mainThread);
        this.callback = callback;
        this.id = id;
        birdWebService = new BirdWebServiceImpl();
    }

    private void executionFinished(BirdResult result) {
        mainThread.post(() -> callback.onBirdReceived(result));
    }

    @Override
    public void run() {

    }
}
