package com.justinraczak.android.squadgoals.models;

import java.util.HashMap;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 9/27/16.
 */
public class Exercise extends RealmObject {


    private String id;
    @Required @PrimaryKey
    private String name;
    @Required
    private HashMap<String, String[]> muscles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, String[]> getMuscles() {
        return muscles;
    }

    public void setMuscles(HashMap<String, String[]> muscles) {
        this.muscles = muscles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
