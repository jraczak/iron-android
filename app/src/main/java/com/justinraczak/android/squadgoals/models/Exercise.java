package com.justinraczak.android.squadgoals.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 9/27/16.
 */
public class Exercise extends RealmObject {


    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private String primaryMuscles;
    private String secondaryMuscles;
    private String equipment;

    public Exercise() {

    }

    public Exercise(String equipment, int id, String name, String primaryMuscles, String secondaryMuscles) {
        this.equipment = equipment;
        this.id = id;
        this.name = name;
        this.primaryMuscles = primaryMuscles;
        this.secondaryMuscles = secondaryMuscles;
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
