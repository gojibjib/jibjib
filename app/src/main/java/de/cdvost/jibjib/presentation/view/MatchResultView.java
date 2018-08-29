package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.IMatchResultPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchResultPresenter;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchResultView extends Activity implements IMatchResultPresenter.View, View.OnClickListener {


    public static final String EXTRA_BIRD_LIST = "matchResultBirdList";

    //@BindView(R.id.list_match)
    RecyclerView matchBirds;
    //@BindView(R.id.birdlist)
    //ImageButton birdlist;

    private File audioFile;
    private IMatchResultPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchresult);
        this.presenter = new MatchResultPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);

        //result list
        ArrayList<MatchedBird> matchedBirds = (ArrayList<MatchedBird>) getIntent().getSerializableExtra(EXTRA_BIRD_LIST);
        matchBirds = (RecyclerView) findViewById(R.id.list_match);


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

    public void itemClick(MatchedBird bird) {
        Intent intent = new Intent(this, MatchBirdDetailView.class);
        intent.putExtra(MatchBirdDetailView.EXTRA_BIRD_KEY, bird);
        startActivity(intent);
    }

    /*@OnClick(R.id.birdlist)
    public void onClick() {
        Intent intent = new Intent(this, BirdListView.class);
        startActivity(intent);
    }*/

    @Override
    public void showProgress() {
        Toast.makeText(this, "Matching sound", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
