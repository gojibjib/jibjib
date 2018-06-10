package de.cdvost.jibjib.domain.interactors.web.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;

public class MatchResponseParser {

    public static List<MatchResult> parse(String response) throws JSONException{
        List<MatchResult> results = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray responseArray = jsonResponse.getJSONArray("data");
        for(int i=0;i<responseArray.length();i++){
            JSONObject jsonObject = responseArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            double accuracy = jsonObject.getDouble("accuracy");
            results.add(new MatchResult(accuracy, id));
        }
        Collections.sort(results, (r1, r2)-> Double.compare(r2.getAccuracy(), r1.getAccuracy()));
        return results;
    }
}
