package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.presentation.presenter.BirdListViewPresenter;
import de.cdvost.jibjib.presentation.presenter.InfoViewPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class InfoView extends Activity {

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottomNavBar;
    private Context context;
    private Activity activity;
    private InfoViewPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_view);
        ((TextView) findViewById(R.id.info_TextView)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.info_TextView)).setText(Html.fromHtml(getResources().getString(R.string.app_info)));
        this.presenter = new InfoViewPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        context = this;
        activity = this;

        bottomNavBar.setSelectedItemId(R.id.nav_info);
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

}
