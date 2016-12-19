package com.justinraczak.android.squadgoals.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 11/18/16.
 */

public class Workout extends RealmObject implements Parcelable {

    @PrimaryKey
    private String id;
    @Required
    private String name;
    public RealmList<Set> sets;
    public RealmList<Exercise> exercises;
    private Date date;

    public Workout(String name, RealmList<Set> sets, Date date) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.sets = sets;
        this.exercises = new RealmList<>();
    }

    public Workout() {
    }

    // PARCELABLE REQUIREMENTS

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        //TODO: Figure out if it matters that I don't pass all fields into writer
    }

    public static final Parcelable.Creator<Workout> CREATOR = new Parcelable.Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel source) {
            //TODO: Figure out if I actually need to implement this
            return null;
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };


    // END PARCELABLE REQUIREMENTS

    public Date getDate() {
        return date;
    }

    public String getFriendlyDate() {
        //TODO: Easy method to get full date as string
        return null;
    }

    public String getFriendlyDateWithYear() {
        //TODO: Easy method to get full date as string including year
        return null;
    }

    public String getTime() {
        //TODO: Easy method to get workout time as string
        return null;
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
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.sets.add(set);
        realm.commitTransaction();
    }

    public RealmList<Exercise> getExercises() {
        return exercises;
    }

    //TODO: Create a bulk add feature
    //public void RealmList<Exercise> addExercises(RealmList<Exercise> exercises)

    public void addExercise(Exercise exercise) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.exercises.add(exercise);
        realm.commitTransaction();
    }

    public int getSetCountForExercise(Exercise exercise) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Set> results;

        results = realm.where(Set.class).equalTo("workout.id", this.getId())
                .equalTo("exercise.id", exercise.getId())
                .findAll();
        return results.size();
    }

    public RealmResults<Set> getSetsForExercise(Exercise exercise) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Set> results;

        results = realm.where(Set.class).equalTo("workout.id", this.getId())
                .equalTo("exercise.id", exercise.getId())
                .findAll();
        return results;
    }
}
