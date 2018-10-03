package de.cdvost.jibjib.presentation.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

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
    @BindView(R.id.BirdDescription)
    TextView description;
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

        String wikiLink = "https://en.wikipedia.org/wiki/";
        String title = bird.getBird().getTitle_en();
        String descriptionText = bird.getBird().getDescription_en();

        String language = Locale.getDefault().getLanguage();
        if(language.startsWith("de")){
            title = bird.getBird().getTitle_de();
            wikiLink = "https://de.wikipedia.org/wiki/";
            descriptionText = bird.getBird().getDescription_de();
        }
        String genus = bird.getBird().getGenus();
        String species = bird.getBird().getSpecies();
        if(genus!=null && species!=null){
            wikiLink += genus+"_"+species;
            uri.setText(wikiLink);
            title += " ("+genus+" "+species+")";
        }
        name.setText(title);
        description.setText(descriptionText);
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
        Toast.makeText(this, R.string.details_view_save_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, R.string.general_exception_text, Toast.LENGTH_LONG).show();
    }


}
