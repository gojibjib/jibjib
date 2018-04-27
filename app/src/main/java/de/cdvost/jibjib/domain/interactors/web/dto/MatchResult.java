package de.cdvost.jibjib.domain.interactors.web.dto;

import java.net.URI;

public class MatchResult {

    private float percentage;
    private URI uri;
    private String name;

    public MatchResult(float percentage, URI uri, String name) {
        this.percentage = percentage;
        this.uri = uri;
        this.name = name;
    }

    public float getPercentage() {
        return percentage;
    }

    public URI getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }
}
