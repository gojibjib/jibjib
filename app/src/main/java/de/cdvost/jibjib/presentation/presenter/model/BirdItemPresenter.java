package de.cdvost.jibjib.presentation.presenter.model;

import android.widget.TextView;

import java.io.Serializable;

import butterknife.BindView;
import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class BirdItemPresenter implements Serializable {

    /*@BindView(R.id.match_bird_name)
    TextView birdName;
    @BindView(R.id.match_bird_accuracy)
    TextView birdAccuracy;*/

    private String name;
    private float accuracy;
    private MatchedBird bird;

    public BirdItemPresenter(MatchedBird bird) {
        this.bird = bird;
        name = bird.getBird().getName();
        accuracy = bird.getAccuracy();

        //birdName.setText(name);
        //birdAccuracy.setText(Float.toString(accuracy));
    }

    public String getName() {
        return name;
    }

    public Bird getBird() {
        return bird.getBird();
    }

    public float getAccuracy() {
        return accuracy;
    }
}
