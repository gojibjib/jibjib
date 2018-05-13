package de.cdvost.jibjib.domain.interactors.web.dto;

import java.net.URI;

public class MatchResult {

    private double percentage;
    private URI uri;
    private String name;

    public MatchResult(double percentage, URI uri, String name) {
        this.percentage = percentage;
        this.uri = uri;
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
}
