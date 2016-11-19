package com.justinraczak.android.squadgoals.models;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 11/18/16.
 */

public class Workout extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String name;
    public RealmList<Set> sets;
    private Date date;

    public Workout(String name, RealmList<Set> sets, Date date) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.sets = sets;
    }

    public Workout() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public RealmList<Set> getSets() {
        return sets;
    }

    public void setSets(RealmList<Set> sets) {
        this.sets = sets;
    }

    public void addSet(Set set) {
        this.sets.add(set);
    }
}
