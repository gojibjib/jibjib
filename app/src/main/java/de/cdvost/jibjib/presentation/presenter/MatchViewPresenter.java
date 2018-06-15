package de.cdvost.jibjib.presentation.presenter;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.room.IStoreBirdInteractor;
import de.cdvost.jibjib.domain.interactors.web.IMatchSoundInteractor;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.domain.interactors.web.impl.MatchSoundInteractorImpl;
import de.cdvost.jibjib.presentation.presenter.base.AbstractPresenter;

public class MatchViewPresenter extends AbstractPresenter
        implements IMatchViewPresenter,
        IMatchSoundInteractor.Callback,
        IStoreBirdInteractor.Callback {

    private IMatchViewPresenter.View view;
    private MediaPlayer recordingMediaPlayer = null;

    private MediaRecorder mediaRecorder = null;
    private static final int MAX_RECORDING_TIME = 20000;

    private boolean isRecording = false;

    public MatchViewPresenter(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);
        this.view = view;
    }

    @Override
    public void matchSound(Context context) {
        //starts the long-running method
        //results will be returned in the callback method (onMatchingFinished())
        view.showProgress();
        new MatchSoundInteractorImpl(executor, mainThread, getRecordingFile(), context, this).execute();
    }

    @Override
    public void onMatchingFinished(ArrayList<MatchedBird> results) {
        view.hideProgress();
        view.showMatchResults(results);
    }

    @Override
    public void onStoreComplete() {

    }

    @Override
    public void onExecutionFailed(Object fail) {
        view.hideProgress();
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

    public void stopRecordingPlayback() {
        if (recordingMediaPlayer == null)
            return;

        try {
            recordingMediaPlayer.stop();
        } catch (IllegalStateException ie) {
            Log.e("MatchViewPresenter", "could not stop recording playback");
        }
        recordingMediaPlayer.release();
    }

    public void startStopRecordingPlayback() {
        stopRecordingPlayback();

        File file = getRecordingFile();
        if (!file.exists()) {
            return;
        }

        recordingMediaPlayer = new MediaPlayer();
        recordingMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
            }
        });

        try {
            // its important that we open the file stream ourselves and just
            // hand over the file descriptor to the media player, otherwise
            // the player wouldn't be able to read the file itself as we
            // haven't created it with global permissions
            FileInputStream inputstream = new FileInputStream(file);
            recordingMediaPlayer.setDataSource(inputstream.getFD());
            recordingMediaPlayer.prepare();
        } catch (IOException io) {
            Log.e("MatchViewPresenter", "Cannot load / prepare audio playback from " + file.getAbsolutePath());
        }

        recordingMediaPlayer.start();
    }

    public void startRecording() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setMaxDuration(MAX_RECORDING_TIME);

        mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {

            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (what != MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED)
                    return;

                stopRecording();
            }
        });

        mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

            public void onError(MediaRecorder mr, int what, int extra) {
                Log.e("StoryActivity", "An error occurred while recording audio, code " + what + ", extra: " + extra);

            }
        });

        try {
            File file = getRecordingFile();
            if (file.exists())
                file.delete();

            mediaRecorder.setOutputFile(file.getAbsolutePath());
            mediaRecorder.prepare();
        } catch (IOException io) {
            Log.e("StoryActivity", "Could not prepare audio recording: " + io.getMessage());
            return;
        }

        mediaRecorder.start();
        isRecording = true;

        view.updateProgressBar();
    }

    public File getRecordingFile() {
        return view.getFileStreamPath();
    }

    public void stopRecording() {
        if (mediaRecorder == null)
            return;
        view.stopProgressBar();
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        mediaRecorder = null;

    }
}
