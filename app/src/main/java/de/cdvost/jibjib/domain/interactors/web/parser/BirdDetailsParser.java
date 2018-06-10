package de.cdvost.jibjib.domain.interactors.web.parser;

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
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonObject = jsonResponse.getJSONObject("data");
            int id = jsonObject.getInt(ID);
            String name = jsonObject.getString(NAME);
            String genus = jsonObject.getString(GENUS);
            String species = jsonObject.getString(SPECIES);
            String titleDe = jsonObject.getString(TITLE_DE);
            String titleEn = jsonObject.getString(TITLE_EN);
            String descDe = jsonObject.getString(DESCRIPTION_DE);
            String descEN = jsonObject.getString(DESCRIPTION_EN);
            return new Bird(id, name, genus, species, titleDe, titleEn, descDe, descEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
