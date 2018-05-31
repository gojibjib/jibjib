package de.cdvost.jibjib.presentation.presenter;

import java.io.File;
import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.presentation.presenter.base.IPresenter;
import de.cdvost.jibjib.presentation.view.base.BaseView;

public interface IMatchViewPresenter extends IPresenter {
    interface View extends BaseView {
        public void showMatchResults(List<MatchResult> results);

        public File getFileStreamPath();

        public void updateProgressBar();

        public void stopProgressBar();
    }

    public void matchSound(Object audio);

    public void stopRecordingPlayback();

    public void startStopRecordingPlayback();

    public void startRecording();

    public File getRecordingFile();

    public void stopRecording();
}
