package com.justinraczak.android.squadgoals.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 11/18/16.
 */

public class Workout extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    public RealmList<Set> sets;


}
