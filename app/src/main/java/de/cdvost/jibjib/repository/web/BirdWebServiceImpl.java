package de.cdvost.jibjib.repository.web;

public class BirdWebServiceImpl implements IBirdWebService {
    @Override
    public Object match(Object audio) {
        //access web service (some seconds delay)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "chirp chirp says the sparrow";
    }
}
