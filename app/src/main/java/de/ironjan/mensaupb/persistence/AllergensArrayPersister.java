package de.ironjan.mensaupb.persistence;

import android.text.TextUtils;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.field.types.StringType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;

import de.ironjan.mensaupb.stw.NewAllergen;

/**
 * Created by ljan on 12.03.15.
 */
public class AllergensArrayPersister extends StringType {
    private static final String DELIMITER = ";";
    private static AllergensArrayPersister instance = null;

    private AllergensArrayPersister() {
        super(SqlType.STRING, new Class<?>[]{NewAllergen.class});
    }

    public synchronized static AllergensArrayPersister getSingleton() {
        if (instance == null) {
            instance = new AllergensArrayPersister();
        }
        return instance;
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return super.parseDefaultString(fieldType, defaultStr);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        String allergensAsString = results.getString(columnPos);
        if (TextUtils.isEmpty(allergensAsString)) {
            return new NewAllergen[0];
        }

        String[] splittedAllergensAsString = allergensAsString.split(DELIMITER);
        int numberOfAllergens = splittedAllergensAsString.length;
        NewAllergen[] allergens = new NewAllergen[numberOfAllergens];
        for (int i = 0; i < numberOfAllergens; i++) {
            allergens[i] = NewAllergen.fromString(splittedAllergensAsString[i]);
        }
        return allergens;
    }


    public Object sqlArgToJava(FieldType fieldType, NewAllergen[] sqlArg, int columnPos) throws SQLException {
        if (sqlArg == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(DELIMITER);
        for (int i = 0; i < sqlArg.length; i++) {
            stringBuffer.append(sqlArg.toString());
            stringBuffer.append(DELIMITER);
        }
        return stringBuffer.toString();
    }

}
