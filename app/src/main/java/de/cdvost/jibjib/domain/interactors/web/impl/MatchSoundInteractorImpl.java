package de.cdvost.jibjib.domain.interactors.web.impl;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.cdvost.jibjib.domain.executor.Executor;
import de.cdvost.jibjib.domain.executor.MainThread;
import de.cdvost.jibjib.domain.interactors.base.AbstractInteractor;
import de.cdvost.jibjib.domain.interactors.web.IMatchSoundInteractor;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;
import de.cdvost.jibjib.domain.interactors.web.parser.BirdDetailsParser;
import de.cdvost.jibjib.domain.interactors.web.parser.MatchResponseParser;
import de.cdvost.jibjib.repository.room.RoomDataBaseRepository;
import de.cdvost.jibjib.repository.room.model.entity.Bird;
import de.cdvost.jibjib.repository.web.BirdWebServiceImpl;
import de.cdvost.jibjib.repository.web.IBirdWebService;

public class MatchSoundInteractorImpl extends AbstractInteractor implements IMatchSoundInteractor {

    private final Object audio;
    private final Context context;
    private final IMatchSoundInteractor.Callback callback;

    public MatchSoundInteractorImpl(Executor executor, MainThread mainThread,
                                    Object audio,
                                    Context context,
                                    IMatchSoundInteractor.Callback callback) {
        super(executor, mainThread);
        this.callback = callback;
        this.audio = audio;
        this.context = context;
    }

    private String matchBirdSound(Object audio) {
        return new BirdWebServiceImpl().match(audio);
    }


    private void executionFinished(List<MatchedBird> results){
        mainThread.post(()->callback.onMatchingFinished(results));
    }

    private void executionFailed(Object fail){
        mainThread.post(()->callback.onExecutionFailed(fail));
    }

    @Override
    public void run() {
        List<MatchedBird> birds = new ArrayList<>();
        String serviceResponse = matchBirdSound(audio);
        if(serviceResponse==null){
            executionFailed("No response from server");
            return;
        }
        List<MatchResult> matchResults = new ArrayList<>();
        try {
            matchResults = MatchResponseParser.parse(serviceResponse);
        } catch (JSONException e) {
            executionFailed(e);
            return;
        }
        //check if birds are already stored in the DB
        RoomDataBaseRepository roomDataBaseRepository = new RoomDataBaseRepository();
        for (MatchResult result : matchResults) {
            Bird bird = roomDataBaseRepository.loadBirdById(result.getId(), context);
            if(bird!=null){
                birds.add(new MatchedBird(bird, result.getAccuracy()));
            }
            else{
                String response = new BirdWebServiceImpl().getMatchBird(result.getId());
                bird = BirdDetailsParser.parse(response);
                if(bird!=null){
                    birds.add(new MatchedBird(bird, result.getAccuracy()));
                }
            }
        }
        executionFinished(birds);
    }
}
