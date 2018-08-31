package de.cdvost.jibjib.domain.interactors.web.parser;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;

public class ErrorResponseParser {

    public static Pair<String, String> parse(String response) throws JSONException{
        List<MatchResult> results = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response);
        String status = jsonResponse.getString("status");
        String message = jsonResponse.getString("message");
        return new Pair<>(status, message);
    }
}
