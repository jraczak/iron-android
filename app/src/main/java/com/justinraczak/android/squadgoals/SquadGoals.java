package com.justinraczak.android.squadgoals;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Justin on 9/29/16.
 */

public class SquadGoals extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder()
                .name("squad_goals.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(defaultConfig);

        RealmConfiguration exerciseConfig = new RealmConfiguration.Builder()
                .name("exercises.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
