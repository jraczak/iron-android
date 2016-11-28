package com.justinraczak.android.squadgoals.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Justin on 9/27/16.
 */
public class Exercise extends RealmObject implements Parcelable {


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

    // PARCELABLE REQUIREMENTS
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(primaryMuscles);
        dest.writeString(secondaryMuscles);
        dest.writeString(equipment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Exercise(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.primaryMuscles = in.readString();
        this.secondaryMuscles = in.readString();
        this.equipment = in.readString();
    }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel source) {
            return new Exercise(source);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    // END PARCELABLE REQUIREMENTS

    // GETTERS AND SETTERS

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

    // END GETTERS AND SETTERS
}
