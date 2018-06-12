package de.cdvost.jibjib.domain.interactors.web.dto;

import java.net.URI;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class MatchResult {

    private float accuracy;
    private int id;

    public MatchResult(float accuracy, int id) {
        this.accuracy = accuracy;
        this.id = id;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public int getId() {
        return id;
    }

}
