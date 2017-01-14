package fitness.iamiron.android.iron.models;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Justin on 1/10/17.
 */

public class DatabaseMigration implements RealmMigration {

    @Override
    public void migrate(final DynamicRealm realm, long oldVeresion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        // Migrate from version 1 to version 2
        if (oldVeresion == 1) {
            // Get the schema for each class
            // RealmObjectSchema exerciseSchema = schema.get("Exercise");

            // Add, remove, or edit fields
            // exerciseSchema
            //     .addField("fieldName", String.class, FieldAttribute.REQUIRED)

            // Increment the version to move on to next required migration oldVersion++
        }
    }

}
