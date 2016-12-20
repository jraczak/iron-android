package com.justinraczak.android.squadgoals.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 11/18/16.
 */

public class Set extends RealmObject implements Parcelable {

    private final static String LOG_TAG = Set.class.getSimpleName();

    @PrimaryKey
    private Integer realmId;
    @Required
    private Date date;
    private Workout workout;
    private Exercise exercise;
    private int reps;
    private float weight;

    public Set(Integer id, Date date, Workout workout, Exercise exercise, int reps, float weight) {
        this.realmId = id;
        this.date = date;
        this.workout = workout;
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
    }

    public Set() {
    }

    //TODO: Make set parcelable


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(realmId);
        dest.writeParcelable(workout, 0);
        dest.writeParcelable(exercise, 0);
        dest.writeInt(reps);
        dest.writeFloat(weight);
    }

    public static final Creator<Set> CREATOR = new Parcelable.Creator<Set>() {
        @Override
        public Set createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Set[] newArray(int size) {
            return new Set[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getRealmId() {
        return realmId;
    }

    public void setRealmId(int id) {
        this.realmId = id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public static Integer getNewAutoIncrementId() {
        Realm realm = Realm.getDefaultInstance();
        //Integer oldMaxId = realm.where(Set.class).max("realmId").intValue();

        if (realm.where(Set.class).max("realmId") == null) {
            Log.d(LOG_TAG,"max table id was null, returning 1");
            realm.close();
            return 1;
        } else {
            Log.d(LOG_TAG, "incrementing max table id");
            realm.close();
            return (realm.where(Set.class).max("realmId").intValue()+1);
        }
    }
}
