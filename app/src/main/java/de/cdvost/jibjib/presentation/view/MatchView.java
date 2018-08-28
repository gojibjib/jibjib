package de.cdvost.jibjib.presentation.view;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.IMatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchView extends Activity implements IMatchViewPresenter.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.button)
    Button btnMatch;
    //@BindView(R.id.list_match)
    RecyclerView matchBirds;
    @BindView(R.id.progress)
    ProgressBar matchProgress;
    //@BindView(R.id.birdlist)
    //ImageButton birdlist;
    @BindView(R.id.bird_background)
    ImageView birdbackground;
    @BindView(R.id.determinateBar)
    public ProgressBar mProgress;
    @BindView(R.id.splash_screen)
    public ViewGroup splashScreen;
    @BindView(R.id.splash_screen_image)
    public ImageView splashScreenImage;
    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottomNavBar;

    @BindView(R.id.bird_background_empty)
    public ImageView backgroundEmpty;
    @BindView(R.id.clouds)
    public ViewGroup clouds;
    @BindView(R.id.cloud1)
    public ImageView cloud1;
    @BindView(R.id.cloud2)
    public ImageView cloud2;
    @BindView(R.id.cloud3)
    public ImageView cloud3;
    @BindView(R.id.cloud4)
    public ImageView cloud4;

    private AnimatorSet movecloud1;
    private AnimatorSet movecloud2;
    private AnimatorSet movecloud3;
    private AnimatorSet movecloud4;

    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 1;
    private IMatchViewPresenter presenter;
    private AnimationDrawable birdanimation;
    private AnimationDrawable splashAnimation;
    private String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    private final Handler progressHandler = new Handler();
    private boolean needsSplashAnimation = true;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_view_layout);
        this.presenter = new MatchViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        //btnMatch.setOnClickListener(this);
        matchBirds = (RecyclerView) findViewById(R.id.list_match);
        context = this;
        activity = this;

        bottomNavBar.setSelectedItemId(R.id.nav_record);
        birdbackground.setImageResource(R.drawable.jibjib);
        birdanimation = (AnimationDrawable) birdbackground.getDrawable();
        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_record:
                        if (!(activity instanceof MatchView)) {
                            startActivity(new Intent(context, MatchView.class), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        break;
                    case R.id.nav_collection:
                        if (!(activity instanceof BirdListView)) {
                            Intent intent = new Intent(context, BirdListView.class);
                            startActivity(intent,
                                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        break;
                    case R.id.nav_info:
                        if (!(activity instanceof InfoView)) {
                            startActivity(new Intent(context, InfoView.class), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        }
                        break;
                }

                return true;
            }
        });

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
    }

    public int getContentViewId() {
        return 0;
    }


    public int getNavigationMenuItemId() {
        return 0;
    }

    @Override
    public void showMatchResults(ArrayList<MatchedBird> results) {
        //TODO: reset background
        Intent intent = new Intent(this, MatchResultView.class);
        intent.putExtra(MatchResultView.EXTRA_BIRD_LIST, results);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /*@OnClick(R.id.birdlist)
    public void onClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }*/

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
                break;
        }
        return true;
    }

    public void startMatchAnimation() {

        backgroundEmpty.setVisibility(View.VISIBLE);
        AlphaAnimation animate = new AlphaAnimation(1.0f, 0.0f);
        animate.setDuration(1000);
        birdbackground.startAnimation(animate);
        birdbackground.setVisibility(View.GONE);
        //clouds.startAnimation(animate);
        clouds.setVisibility(View.VISIBLE);

        //birdbackground.setImageResource(R.drawable.matching_animatio_start);
        movecloud1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move0);
        movecloud1.setTarget(cloud1);

        movecloud2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move1);
        movecloud2.setTarget(cloud2);

        movecloud3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move2);
        movecloud3.setTarget(cloud3);

        movecloud4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move3);
        movecloud4.setTarget(cloud4);

        movecloud1.start();
        movecloud2.start();
        movecloud3.start();
        movecloud4.start();

    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    public void startRecord() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
            presenter.startRecording();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION_RECORD_AUDIO);
        }
    }

    public void stopRecord() {
        try {
            presenter.stopRecording();
            startMatchAnimation();
            presenter.matchSound(this);
        }
        catch(Exception e){
            //when the match button is just clicked and not held down
            //this method can be invoked before the media player is all set up
            //and recording.

            //ignore exception and clean up
        }
        finally {
            presenter.cleanUpMediaRecorder();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        birdbackground.setVisibility(View.VISIBLE);
        mProgress.setProgress(0);
        clouds.setVisibility(View.GONE);
        backgroundEmpty.setVisibility(View.GONE);
        //birdbackground.setImageResource(R.drawable.jibjib);
        //birdanimation = (AnimationDrawable) birdbackground.getDrawable();
    }

    @Override
    public void onPause(){
        super.onPause();
        AlphaAnimation animate = new AlphaAnimation(1.0f, 0.0f);
        animate.setDuration(1000);
        clouds.startAnimation(animate);
        clouds.setVisibility(View.GONE);
        if (movecloud1 != null) {
            movecloud1.cancel();
            movecloud2.cancel();
            movecloud3.cancel();
            movecloud4.cancel();
        }

        // backgroundEmpty.startAnimation(animate);
        //backgroundEmpty.setVisibility(View.GONE);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.e("matchView", "click info");
        switch (item.getItemId()) {
            case R.id.nav_record:
                startActivity(new Intent(this, MatchView.class));
                Log.e("matchView", "click record");
                break;
            case R.id.nav_collection:
                startActivity(new Intent(this, BirdListView.class));
                Log.e("matchView", "click collect");
                break;
            case R.id.nav_info:
                Log.e("matchView", "click info");
                //startActivity(new Intent(this, AccountsActivity.class));
                break;
        }
        finish();
        return true;
    }

}
