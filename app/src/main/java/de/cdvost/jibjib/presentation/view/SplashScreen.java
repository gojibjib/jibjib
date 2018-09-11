package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class SplashScreen extends Activity {

    @BindView(R.id.splash_screen)
    public ViewGroup splashScreen;
    @BindView(R.id.splash_screen_image)
    public ImageView splashScreenImage;
    private AnimationDrawable splashAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        splashScreenImage.setImageResource(R.drawable.splash_animation);
        splashAnimation = (AnimationDrawable) splashScreenImage.getDrawable();
        splashAnimation.start();


        final Runnable progress = () -> {
            AlphaAnimation animate = new AlphaAnimation(1.0f, 0.0f);
            animate.setDuration(1000);
            splashScreen.startAnimation(animate);
            splashScreen.setVisibility(View.GONE);
            splashAnimation.stop();
            startActivity(new Intent(this, MatchView.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        };

        Handler splashHandler = new Handler();
        splashHandler.postDelayed(progress, 3000);

        final Runnable finish = this::finish;

        Handler finishHandler = new Handler();
        finishHandler.postDelayed(finish, 3100);

    }
}
