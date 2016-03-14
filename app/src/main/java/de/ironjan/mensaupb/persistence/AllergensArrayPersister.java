package de.ironjan.mensaupb.persistence;

import android.text.TextUtils;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;
import java.util.Arrays;

import de.ironjan.mensaupb.stw.rest_api.Allergen;

/**
 * A custom persister that saves an AllergensArray as String in the database.
 */
public class AllergensArrayPersister extends StringType {
    private static final String DELIMITER = ";";
    private static AllergensArrayPersister instance = null;

    private AllergensArrayPersister() {
        super(SqlType.STRING, new Class<?>[]{Allergen.class});
    }

    public synchronized static AllergensArrayPersister getSingleton() {
        if (instance == null) {
            instance = new AllergensArrayPersister();
        }
        return instance;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return Allergen.UNKNOWN;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) throws SQLException {
        if (!(object instanceof Allergen[])) {
            throw new IllegalArgumentException("Must only persist Allergen[]");
        }

        Allergen[] allergens = (Allergen[]) object;
        if (allergens.length == 0) {
            return "";
        }

        Arrays.sort(allergens);
        StringBuilder stringBuffer = new StringBuilder(DELIMITER);
        for (Allergen allergen : allergens) {
            stringBuffer.append(allergen.toString());
            stringBuffer.append(DELIMITER);
        }
        return stringBuffer.toString();
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        String allergensAsString = results.getString(columnPos);
        if (TextUtils.isEmpty(allergensAsString) || (allergensAsString.length() <= 1)) {
            return new Allergen[0];
        }

        // we need to remove the first and last DELIMITER. The format is ';(A;)+' where 'A' is an allergen or additional
        String strippedAllergenString = allergensAsString.substring(1, allergensAsString.length() - 1);
        String[] splittedAllergensAsString = strippedAllergenString.split(DELIMITER);
        int numberOfAllergens = splittedAllergensAsString.length;
        Allergen[] allergens = new Allergen[numberOfAllergens];
        for (int i = 0; i < numberOfAllergens; i++) {
            allergens[i] = Allergen.fromString(splittedAllergensAsString[i]);
        }
        return allergens;
    }
}
