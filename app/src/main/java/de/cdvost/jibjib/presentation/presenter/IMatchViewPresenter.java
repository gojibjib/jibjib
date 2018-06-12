package de.cdvost.jibjib.presentation.presenter;

import android.content.Context;

import java.io.File;
import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public interface IMatchViewPresenter extends IPresenter {
    interface View extends BaseView {
        public void showMatchResults(List<MatchedBird> results);

        public File getFileStreamPath();

        public void updateProgressBar();

        public void stopProgressBar();
    }

    public void matchSound(Context context);

    public void stopRecordingPlayback();

    public void startStopRecordingPlayback();

    public void startRecording();

    public File getRecordingFile();

    public void stopRecording();
}
