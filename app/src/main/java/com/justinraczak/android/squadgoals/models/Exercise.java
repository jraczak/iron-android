package com.justinraczak.android.squadgoals.models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Justin on 9/27/16.
 */
public class Exercise extends RealmObject {


    private String id;
    @Required
    private String name;
    @Required
    private String primaryMuscles;
    private String secondaryMuscles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryMuscles() {
        return primaryMuscles;
    }

    public void setPrimaryMuscles(String primaryMuscles) {
        this.primaryMuscles = primaryMuscles;
    }

    public String getSecondaryMuscles() {
        return secondaryMuscles;
    }

    public void setSecondaryMuscles(String secondaryMuscles) {
        this.secondaryMuscles = secondaryMuscles;
    }
}
