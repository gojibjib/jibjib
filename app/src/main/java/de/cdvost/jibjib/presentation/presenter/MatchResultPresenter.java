package de.cdvost.jibjib.presentation.presenter;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.room.IStoreBirdInteractor;
import de.cdvost.jibjib.domain.interactors.web.IMatchSoundInteractor;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.domain.interactors.web.impl.MatchSoundInteractorImpl;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;
import de.cdvost.jibjib.presentation.view.MatchResultView;

public class MatchResultPresenter extends AbstractPresenter
        implements IMatchResultPresenter {

    private View view;

    public MatchResultPresenter(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

}
