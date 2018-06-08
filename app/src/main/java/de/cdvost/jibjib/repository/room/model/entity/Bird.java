package de.cdvost.jibjib.repository.room.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bird")
public class Bird {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "genus")
    private String genus;

    @ColumnInfo(name = "species")
    private String species;

    @ColumnInfo(name = "title_de")
    private String title_de;

    @ColumnInfo(name = "title_en")
    private String title_en;

    @ColumnInfo(name = "description_de")
    private String description_de;

    @ColumnInfo(name = "description_en")
    private String description_en;

    @ColumnInfo(name = "uri")
    private String uri;

    public Bird(int id,
                String name,
                String genus,
                String species,
                String title_de,
                String title_en,
                String description_de,
                String description_en) {
        this.id = id;
        this.name = name;
        this.genus = genus;
        this.species = species;
        this.title_de = title_de;
        this.title_en = title_en;
        this.description_de = description_de;
        this.description_en = description_en;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getTitle_de() {
        return title_de;
    }

    public void setTitle_de(String title_de) {
        this.title_de = title_de;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getDescription_de() {
        return description_de;
    }

    public void setDescription_de(String description_de) {
        this.description_de = description_de;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    @Override
    public String toString() {
        return name;
    }
}
