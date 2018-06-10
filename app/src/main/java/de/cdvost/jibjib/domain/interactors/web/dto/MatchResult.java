package de.cdvost.jibjib.domain.interactors.web.dto;

import java.net.URI;

import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class MatchResult {

    private double accuracy;
    private int id;

    public MatchResult(double accuracy, int id) {
        this.accuracy = accuracy;
        this.id = id;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getId() {
        return id;
    }

}
