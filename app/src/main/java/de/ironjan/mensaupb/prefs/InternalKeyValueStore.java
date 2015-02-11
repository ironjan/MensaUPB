package de.ironjan.mensaupb.prefs;

import org.androidannotations.annotations.sharedpreferences.*;

/**
 * A shared pref object which is used as an internal key values store
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface InternalKeyValueStore {
    @DefaultLong(0L)
    public long lastSyncTimeStamp();
}
