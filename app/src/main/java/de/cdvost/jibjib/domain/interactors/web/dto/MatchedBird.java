package de.cdvost.jibjib.domain.interactors.web.dto;

import android.os.Parcelable;

import java.io.Serializable;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class MatchedBird implements Serializable {

    private Bird bird;
    private float accuracy;
    boolean isInDataBase;

    public MatchedBird(Bird bird, float accuracy, boolean isInDataBase) {
        this.bird = bird;
        this.accuracy = accuracy;
        this.isInDataBase = isInDataBase;
    }

    public Bird getBird() {
        return bird;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public boolean isInDataBase() {
        return isInDataBase;
    }
}
