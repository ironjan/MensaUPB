package de.ironjan.mensaupb.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * A shared pref object which is used as an internal key values store
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface InternalKeyValueStore {
    @DefaultLong(0L)
    long lastSyncTimeStamp();

    @DefaultInt(0)
    int lastLocation();
}
