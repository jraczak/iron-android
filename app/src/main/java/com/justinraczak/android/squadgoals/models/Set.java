package com.justinraczak.android.squadgoals.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 11/18/16.
 */

public class Set extends RealmObject{

    @PrimaryKey
    private int id;
    @Required
    private Date date;
    @Required
    private Workout workout;
}
