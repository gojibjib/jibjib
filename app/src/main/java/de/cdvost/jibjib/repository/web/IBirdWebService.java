package de.cdvost.jibjib.repository.web;

public interface IBirdWebService {

    /**
     * Queries the web service with the given audio file
     * which runs a matching algorithm. The response will
     * be returned as a string
     * @param audio the audio which should be mathcer
     * @return the response of the web service as a string
     */
    public String match(Object audio);

    public String getMatchBird(int id);
}
