package de.cdvost.jibjib.presentation.view;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.IMatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.events.MatchBirdEvent;
import de.cdvost.jibjib.presentation.presenter.model.BirdItemPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchView extends Activity implements IMatchViewPresenter.View {

    @BindView(R.id.button)
    Button btnMatch;
    //@BindView(R.id.list_match)
    RecyclerView matchBirds;
    @BindView(R.id.progress)
    ProgressBar matchProgress;
    @BindView(R.id.birdlist)
    ImageButton birdlist;
    @BindView(R.id.bird_background)
    ImageView birdbackground;
    @BindView(R.id.determinateBar)
    public ProgressBar mProgress;
    @BindView(R.id.splash_screen)
    public ViewGroup splashScreen;
    @BindView(R.id.splash_screen_image)
    public ImageView splashScreenImage;

    @BindView(R.id.cloud1)
    public ImageView cloud1;
    @BindView(R.id.cloud2)
    public ImageView cloud2;
    @BindView(R.id.cloud3)
    public ImageView cloud3;
    @BindView(R.id.cloud4)
    public ImageView cloud4;

    public ArrayList<BirdItemPresenter> matchedBirds = new ArrayList<>();

    public static final String EXTRA_BIRD_LIST = "matchResultBirdList";

    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 1;
    private IMatchViewPresenter presenter;
    private AnimationDrawable birdanimation;
    private AnimationDrawable splashAnimation;
    private String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    private final Handler progressHandler = new Handler();
    private boolean needsSplashAnimation = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_view_layout);
        this.presenter = new MatchViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        //btnMatch.setOnClickListener(this);
        matchBirds = (RecyclerView) findViewById(R.id.list_match);

        birdbackground.setImageResource(R.drawable.jibjib);
        birdanimation = (AnimationDrawable) birdbackground.getDrawable();

    }

    @Override
    protected void onStart() {
        super.onStart();

        splashScreenImage.setImageResource(R.drawable.splash_animation);
        splashAnimation = (AnimationDrawable) splashScreenImage.getDrawable();
        /*splashScreen.getLayoutTransition()
                .enableTransitionType(LayoutTransition.DISAPPEARING);*/
        //splashScreen.getLayoutTransition().setDuration(3000);
        splashAnimation.start();


        final Runnable progress = () -> {
            needsSplashAnimation = false;
            AlphaAnimation animate = new AlphaAnimation(1.0f, 0.0f);
            animate.setDuration(1000);
            splashScreen.startAnimation(animate);
            splashScreen.setVisibility(View.GONE);
            splashAnimation.stop();
        };

        Handler splashHandler = new Handler();
        if (needsSplashAnimation)
            splashHandler.postDelayed(progress, 3000);



    }


    @Override
    public File getFileStreamPath() {
        return this.getFileStreamPath("recording_" + RandomAudioFileName + ".3gp");
    }

    @Override
    public void updateProgressBar() {
        birdanimation.start();
//        textView.setText("recording");
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
        birdanimation.selectDrawable(0);
//        textView.setText("Done");
    }

    @Override
    public void showMatchResults(ArrayList<MatchedBird> results) {
        //TODO: reset background
        Intent intent = new Intent(this, MatchResultView.class);
        intent.putExtra(EXTRA_BIRD_LIST, results);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @OnClick(R.id.birdlist)
    public void onClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @OnTouch(R.id.button)
    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "touched down");
                btnMatch.setBackgroundResource(R.drawable.buttonshape);
                startRecord();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("TAG", "moving:");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "touched up");
                btnMatch.setBackgroundResource(R.drawable.buttonshape_outline);
                stopRecord();
                startMatchAnimation();
                //TODO: change background with matching animation
                presenter.matchSound(this);
                break;
        }
        return true;
    }

    public void startMatchAnimation() {
        birdbackground.setImageResource(R.drawable.matching_animatio_start);
        cloud1.setVisibility(View.VISIBLE);
        AnimatorSet movecloud1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move0);
        movecloud1.setTarget(cloud1);

        cloud2.setVisibility(View.VISIBLE);
        AnimatorSet movecloud2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move1);
        movecloud2.setTarget(cloud2);

        cloud3.setVisibility(View.VISIBLE);
        AnimatorSet movecloud3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move2);
        movecloud3.setTarget(cloud3);

        cloud4.setVisibility(View.VISIBLE);
        AnimatorSet movecloud4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move3);
        movecloud4.setTarget(cloud4);

        movecloud1.start();
        movecloud2.start();
        movecloud3.start();
        movecloud4.start();
    }

    public void stopAnimation() {
        birdbackground.setImageResource(R.drawable.jibjib);

        cloud1.setVisibility(View.GONE);

        cloud2.setVisibility(View.GONE);

        cloud3.setVisibility(View.GONE);

        cloud4.setVisibility(View.GONE);

    }

    @Override
    public void showProgress() {
        //matchProgress.setVisibility(View.VISIBLE);

//        Toast.makeText(this, "Matching sound", Toast.LENGTH_SHORT).show();
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

    public void startRecord() {
//        Toast.makeText(this, "Start Record", Toast.LENGTH_SHORT).show();
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
            presenter.startRecording();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION_RECORD_AUDIO);
        }
    }

    public void stopRecord() {
//        Toast.makeText(this, "Stop Record", Toast.LENGTH_SHORT).show();
        presenter.stopRecording();
    }

    @Override
    public void onResume() {
        super.onResume();
        stopAnimation();
    }

   /* @Override
    public void onPause(){
        super.onPause();
        birdbackground.setImageResource(R.drawable.jibjib);
    }*/
}
