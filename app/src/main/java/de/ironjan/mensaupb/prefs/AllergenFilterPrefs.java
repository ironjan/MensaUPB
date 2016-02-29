package de.ironjan.mensaupb.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultStringSet;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.util.Set;

/**
 * Interface to generate AllergenFilter Preferences
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface AllergenFilterPrefs {
    @DefaultStringSet
    Set<String> filteredAllergens();

    @DefaultStringSet
    Set<String> filteredAdditionals();
}
