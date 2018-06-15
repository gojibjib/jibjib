package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.presentation.presenter.IMatchBirdDetailPresenter;
import de.cdvost.jibjib.presentation.presenter.IMatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchBirdDetailPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchViewPresenter;
import de.cdvost.jibjib.presentation.presenter.model.BirdItemPresenter;
import de.cdvost.jibjib.repository.room.model.entity.Bird;
import de.cdvost.jibjib.threading.MainThreadImpl;
import de.cdvost.jibjib.threading.ThreadExecutor;

public class MatchBirdDetailView extends Activity implements IMatchBirdDetailPresenter.View {


    @BindView(R.id.BirdPic)
    ImageView birdPic;
    @BindView(R.id.BirdName)
    TextView name;
    @BindView(R.id.URI)
    TextView uri;
    @BindView(R.id.BirdSpecies)
    TextView species;
    @BindView(R.id.BirdNutrition)
    TextView nutrition;
    @BindView(R.id.savebird)
    Button saveBird;

    private String birdID;
    private int birdId;
    private BirdItemPresenter bird;

    private IMatchBirdDetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_bird_detail_view);
        this.presenter = new MatchBirdDetailPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        this.bird = (BirdItemPresenter) getIntent().getSerializableExtra("bird");
        birdID = bird.getBird().getUri();
        birdId = bird.getBird().getId();
        presenter.getMatchBird(birdId);

        name.setText(bird.getBird().getTitle_de());
    }

    @OnClick(R.id.savebird)
    public void onClick() {
        presenter.saveBird(bird.getBird(), this);
    }

    @Override
    public void showMatchBirdDetail(Bird bird) {

    }

    @Override
    public void showBirdSavedHint() {
        Toast.makeText(this, "bird saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

}
