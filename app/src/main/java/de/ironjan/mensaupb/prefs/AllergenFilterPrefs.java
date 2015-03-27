package de.ironjan.mensaupb.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultStringSet;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.util.Set;

/**
 * Created by ljan on 27.03.15.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface AllergenFilterPrefs {
    @DefaultStringSet
    public Set<String> filteredAllergens();

    @DefaultStringSet
    public Set<String> filteredAdditionals();
}
