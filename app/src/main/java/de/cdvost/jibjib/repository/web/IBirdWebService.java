package de.cdvost.jibjib.repository.web;

import java.io.IOException;

public interface IBirdWebService {

    /**
     * Queries the web service with the given audio file
     * which runs a matching algorithm. The response will
     * be returned as a string
     * @param audio the audio which should be mathcer
     * @return the response of the web service as a string
     */
    public String match(Object audio) throws Exception;

    public String getMatchBird(int id) throws Exception;
}
