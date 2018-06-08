package de.cdvost.jibjib.presentation.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.presentation.presenter.IMatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchView extends Activity implements IMatchViewPresenter.View, View.OnClickListener  {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button btnMatch;
    @BindView(R.id.list_match)
    ListView matchBirds;
    @BindView(R.id.progress)
    ProgressBar matchProgress;
    @BindView(R.id.birdlist)
    ImageButton birdlist;
    @BindView(R.id.bird_background)
    ImageView birdbackground;
    @BindView(R.id.determinateBar)
    public ProgressBar mProgress;


    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 1;
    private IMatchViewPresenter presenter;
    private AnimationDrawable birdanimation;
    private String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    private final Handler progressHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_view_layout);
        this.presenter = new MatchViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        btnMatch.setOnClickListener(this);

        birdbackground.setImageResource(R.drawable.jibjib);
        birdanimation = (AnimationDrawable) birdbackground.getDrawable();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            presenter.matchSound();
        }
    }
    @Override
    public void showMatchResults(List<MatchResult> results) {


        /*StringBuilder builder = new StringBuilder();
        results.forEach(result->
                builder.append(result.getPercentage())
                        .append(": ")
                        .append(result.getName())
                        .append("\n"));
        textView.setText(builder.toString());*/

        List<String> matchResult = new ArrayList<String>();
        for (MatchResult result : results) {
            matchResult.add(result.getName() /*+ " " + result.getPercentage()*/);
        }

        ListAdapter birdListAdapter = new ArrayAdapter(
                this,
                R.layout.match_bird_item,
                R.id.match_bird_text,
                matchResult);

        matchBirds.setAdapter(birdListAdapter);
        matchBirds.setVisibility(View.VISIBLE);
        textView.setText("MATCHES");
    }

    @Override
    public File getFileStreamPath() {
        return this.getFileStreamPath("recording_" + RandomAudioFileName + ".3gp");
    }

    @Override
    public void updateProgressBar() {
        birdanimation.start();
        textView.setText("recording");
        mProgress.setProgress(0);
        final Runnable progress = () -> {
            //while(mProgress.get()<20 && isRecording){
            mProgress.setProgress(mProgress.getProgress() + 1);
            //}
        };
        for (int i = 0; i < 80; i++) {
            progressHandler.postDelayed(progress, 250 * i);
        }
    }

    @Override
    public void stopProgressBar() {
        progressHandler.removeCallbacksAndMessages(null);
        birdanimation.stop();
        textView.setText("Done");
    }

    @OnItemClick(R.id.list_match)
    public void onItemClick(int position) {
        Toast.makeText(this, "on item click" + matchBirds.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MatchBirdDetailView.class);
        //TODO get ID
        //int id = matchBirds.getItemAtPosition(position);
        //intent.putExtra(MatchBirdDetailView.BIRD_ID, id);
        String id = matchBirds.getItemAtPosition(position).toString();
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    @OnClick(R.id.birdlist)
    public void onClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        //matchProgress.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Matching sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        //matchBirds.setVisibility(View.VISIBLE);
        //matchProgress.setVisibility(View.GONE);
        //Toast.makeText(this, "Matching done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {

    }

    @OnClick(R.id.rec_button)
    public void startRecord() {
        Toast.makeText(this, "Start Record", Toast.LENGTH_SHORT).show();
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
            presenter.startRecording();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION_RECORD_AUDIO);
        }
    }

    @OnClick(R.id.rec_stop_button)
    public void stopRecord() {
        Toast.makeText(this, "Stop Record", Toast.LENGTH_SHORT).show();
        presenter.stopRecording();
    }

    @OnClick(R.id.play_button)
    public void playRecord() {
        Toast.makeText(this, "Play Record", Toast.LENGTH_SHORT).show();
        presenter.startStopRecordingPlayback();
    }

    @OnClick(R.id.stop_button)
    public void stopRecordPlay() {
        Toast.makeText(this, "Stop Record Play", Toast.LENGTH_SHORT).show();
        presenter.stopRecordingPlayback();
    }

    @OnClick(R.id.new_rec_button)
    public void newRecord() {
        Toast.makeText(this, "New Record", Toast.LENGTH_SHORT).show();
        textView.setText("Birdcall audible?");
        mProgress.setProgress(0);
    }

}
