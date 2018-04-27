package de.cdvost.jibjib.domain.interactors.base;


/**
 * This is the main interface of an interactor. Each interactor serves a specific use case.
 */
public interface Interactor {

    //this interface is implemented by the presenter
    //to receive the callback information
    interface Callback{
        public void onExecutionFinished(Object result);
        public void onExecutionFailed(Object fail);
    }
    /**
     * This is the main method that starts an interactor. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();
}
