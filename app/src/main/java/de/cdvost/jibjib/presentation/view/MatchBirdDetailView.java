package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.presentation.presenter.IMatchBirdDetailPresenter;
import de.cdvost.jibjib.presentation.presenter.MatchBirdDetailPresenter;
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
    @BindView(R.id.BirdNutrition)
    TextView nutrition;
    @BindView(R.id.savebird)
    Button saveBird;

    private MatchedBird bird;

    private IMatchBirdDetailPresenter presenter;

    public static final String EXTRA_BIRD_KEY = "bird";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_bird_detail_view);
        this.presenter = new MatchBirdDetailPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        ButterKnife.bind(this);
        this.bird = (MatchedBird) getIntent().getSerializableExtra(EXTRA_BIRD_KEY);

        String title = bird.getBird().getTitle_de();
        String genus = bird.getBird().getGenus();
        String species = bird.getBird().getSpecies();
        if(genus!=null && species!=null){
            String link = "https://de.wikipedia.org/wiki/"+genus+"_"+species;
            uri.setText(link);
            title += " ("+genus+" "+species+")";
        }
        name.setText(title);
        nutrition.setText(bird.getBird().getDescription_de());
        if(bird.isInDataBase()){
            saveBird.setVisibility(View.GONE);
        }
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
        Toast.makeText(this, "Bird saved to data base", Toast.LENGTH_SHORT).show();
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
