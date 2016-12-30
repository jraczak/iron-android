package fitness.iamiron.android.iron.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

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

    private static final String LOG_TAG = Workout.class.getSimpleName();

    @PrimaryKey
    private Integer realmId;
    @Required
    private String name;
    private RealmList<Set> sets;
    public RealmList<Exercise> exercises;
    private Date date;
    private Date startTime;
    private Date finishTime;

    public Workout(String name, RealmList<Set> sets, Date date, Date startTime, Date finishTime) {
        this.realmId = getNewAutoIncrementId();
        this.name = name;
        this.date = date;
        this.sets = sets;
        this.exercises = new RealmList<>();
        this.startTime = startTime;
        this.finishTime = finishTime;
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
        dest.writeInt(realmId);
        dest.writeString(name);
        //TODO: Figure out if it matters that I don't pass all fields into writer
        //TODO: Fix parcelable, check if sets are actually related to workouts
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

    public Integer getRealmId() {
        return realmId;
    }

    public void setRealmId(Integer id) {
        this.realmId = id;
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

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public void setExercises(RealmList<Exercise> exercises) {
        this.exercises = exercises;
    }

    //TODO: Create a bulk add feature
    //public void RealmList<Exercise> addExercises(RealmList<Exercise> exercises)

    public void addExercise(Exercise exercise) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.exercises.add(exercise);
        realm.commitTransaction();
        realm.close();
    }

    public int getSetCountForExercise(Exercise exercise) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Set> results;

        results = realm.where(Set.class).equalTo("workout.realmId", this.getRealmId())
                .equalTo("exercise.id", exercise.getId())
                .findAll();
        realm.close();
        return results.size();
    }

    public RealmResults<Set> getSetsForExercise(Exercise exercise) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Set> results;

        results = realm.where(Set.class).equalTo("workout.realmId", this.getRealmId())
                .equalTo("exercise.id", exercise.getId())
                .findAll();
        realm.close();
        return results;
    }

    public static Integer getNewAutoIncrementId() {
        Realm realm = Realm.getDefaultInstance();
        //Integer oldMaxId = (Integer) realm.where(Workout.class).max("realmId").intValue();

        //Log.d(LOG_TAG, "old max id is " + oldMaxId);
        if (realm.where(Workout.class).max("realmId") == null) {
            Log.d(LOG_TAG,"old max id was null, returning 1");
            realm.close();
            return 1;
        } else {
            Log.d(LOG_TAG, "incrementing max id");
            realm.close();
            return (realm.where(Workout.class).max("realmId").intValue()+1);
        }
    }
}
