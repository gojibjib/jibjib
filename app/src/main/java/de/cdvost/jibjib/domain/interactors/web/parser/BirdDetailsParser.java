package de.cdvost.jibjib.domain.interactors.web.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.cdvost.jibjib.domain.interactors.web.dto.MatchResult;
import de.cdvost.jibjib.repository.room.model.entity.Bird;

public class BirdDetailsParser {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String GENUS = "genus";
    private static final String SPECIES = "species";
    private static final String TITLE_DE = "title_de";
    private static final String TITLE_EN = "title_en";
    private static final String DESCRIPTION_DE = "desc_de";
    private static final String DESCRIPTION_EN = "desc_en";

    public static Bird parse(String response){
        int id = -1;
        String name = null;
        String genus =null;
        String species = null;
        String titleDe = null;
        String titleEn = null;
        String descDe = null;
        String descEN = null;
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonObject = jsonResponse.getJSONObject("data");
            id = jsonObject.getInt(ID);
            Log.i("g","Parsing details for bird with id: "+id);
            name = jsonObject.getString(NAME);
            genus = jsonObject.getString(GENUS);
            species = jsonObject.getString(SPECIES);
            titleDe = jsonObject.getString(TITLE_DE);
            titleEn = jsonObject.getString(TITLE_EN);
            descDe = jsonObject.getString(DESCRIPTION_DE);
            descEN = jsonObject.getString(DESCRIPTION_EN);
            return new Bird(id, name, genus, species, titleDe, titleEn, descDe, descEN);
        } catch (JSONException e) {
            e.printStackTrace();
            if(id>-1){
                return new Bird(id, name, genus, species, titleDe, titleEn, descDe, descEN);
            }
        }
        return null;
    }
}
