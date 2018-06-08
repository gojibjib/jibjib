package de.cdvost.jibjib.domain.interactors.web.dto;

import java.net.URI;

public class MatchResult {

    private double percentage;
    private URI uri;
    private int id;
    private String name;

    public MatchResult(double percentage, URI uri, int id, String name) {
        this.percentage = percentage;
        this.uri = uri;
        this.id = id;
        this.name = name;
    }

    public double getPercentage() {
        return percentage;
    }

    public URI getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
