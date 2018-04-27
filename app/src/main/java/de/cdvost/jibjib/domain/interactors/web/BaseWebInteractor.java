package de.cdvost.jibjib.domain.interactors.web;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.repository.web.BirdWebServiceImpl;
import de.cdvost.jibjib.repository.web.IBirdWebService;

public abstract class BaseWebInteractor extends AbstractInteractor {

    protected IBirdWebService birdWebService;

    public BaseWebInteractor(Executor threadExecutor, MainThread mainThread, Callback callback) {
        super(threadExecutor, mainThread, callback);
        this.birdWebService = new BirdWebServiceImpl();
    }
}
