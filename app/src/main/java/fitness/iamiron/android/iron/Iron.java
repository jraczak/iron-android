package fitness.iamiron.android.iron;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import fitness.iamiron.android.iron.models.Exercise;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Justin on 9/29/16.
 */

public class Iron extends Application {

    private final String LOG_TAG = Iron.class.getSimpleName();
    private Realm realm;

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
        realm = Realm.getDefaultInstance();


        Log.d(LOG_TAG, "JSON string is: " + loadExerciseJSONFromAsset());
    }

    public String loadExerciseJSONFromAsset() {
        String json;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.exercises);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
        createExercisesFromJSONString(json);
        return json;
    }

    public void createExercisesFromJSONString(String jsonString) {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            final JSONArray jsonArray = jsonObject.getJSONArray("exercises");
            Log.d(LOG_TAG, "JSON is of type: " + jsonArray.getClass());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createOrUpdateAllFromJson(Exercise.class, jsonArray);
                }
            });

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

}
