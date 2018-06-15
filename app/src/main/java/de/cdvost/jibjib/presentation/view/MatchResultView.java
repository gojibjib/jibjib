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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.IMatchResultPresenter;
import de.cdvost.jibjib.presentation.presenter.IMatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchResultPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.model.BirdItemPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchResultView extends Activity implements IMatchResultPresenter.View, View.OnClickListener {

    public ArrayList<BirdItemPresenter> matchedBirds = new ArrayList<>();

    //@BindView(R.id.list_match)
    RecyclerView matchBirds;
    @BindView(R.id.birdlist)
    ImageButton birdlist;

    private File audioFile;
    private IMatchResultPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_view_layout);
        this.presenter = new MatchResultPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);

        //result list
        ArrayList<MatchedBird> results = (ArrayList) getIntent().getSerializableExtra(MatchView.EXTRA_BIRD_LIST);
        matchBirds = (RecyclerView) findViewById(R.id.list_match);
        for (MatchedBird result : results) {
            matchedBirds.add(new BirdItemPresenter(result));
        }

        RecyclerView.LayoutManager blLayoutManager = new LinearLayoutManager(this);
        matchBirds.setLayoutManager(blLayoutManager);
        RecyclerView.Adapter blAdapter = new BirdListAdapter(this, matchedBirds);
        matchBirds.setAdapter(blAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
    }

    public void itemClick(BirdItemPresenter bird) {
        Intent intent = new Intent(this, MatchBirdDetailView.class);
        intent.putExtra("bird", bird);
        startActivity(intent);
    }

    @OnClick(R.id.birdlist)
    public void onClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        Toast.makeText(this, "Matching sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String message) {

    }

}
