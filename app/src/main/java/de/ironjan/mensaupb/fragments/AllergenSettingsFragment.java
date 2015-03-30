package de.ironjan.mensaupb.fragments;

import android.support.v4.app.Fragment;
import android.widget.CheckBox;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.prefs.AllergenFilterPrefs_;
import de.ironjan.mensaupb.stw.rest_api.NewAllergen;

/**
 * Fragment to change filter settings w.r.t allergens and additionals.
 */
@EFragment(R.layout.fragment_allergen_settings)
public class AllergenSettingsFragment extends Fragment {

    private static final int[] CHECK_BOXES = {R.id.checkBoxEggs,
            R.id.checkBoxPeanuts,
            R.id.checkBoxFish,
            R.id.checkBoxGluten,
            R.id.checkBoxCurstacean,
            R.id.checkBoxLupines,
            R.id.checkBoxMilk,
            R.id.checkBoxNuts,
            R.id.checkBoxSulfates,
            R.id.checkBoxCeleriac,
            R.id.checkBoxMustard,
            R.id.checkBoxSesame,
            R.id.checkBoxSoy,
            R.id.checkBoxMollusks};

    @Pref
    AllergenFilterPrefs_ mAllergenFilterPrefs;
    private Set<String> mFilteredAllergens = new HashSet<>();
    private HashMap<Integer, String> mCheckBoxIdToAllergenCode = new HashMap<>();
    private HashMap<String, Integer> mAllergenCodeToCheckBoxId = new HashMap<>();

    @AfterInject
    @Trace(level = 5)
    void loadPreferences() {
        mFilteredAllergens = mAllergenFilterPrefs
                .filteredAllergens()
                .getOr(new HashSet<String>());
        buildHashMaps();
    }

    @Trace
    void buildHashMaps() {
        addToTables(R.id.checkBoxEggs, NewAllergen.EGGS.getType());
        addToTables(R.id.checkBoxPeanuts, NewAllergen.PEANUTS.getType());
        addToTables(R.id.checkBoxFish, NewAllergen.FISH.getType());
        addToTables(R.id.checkBoxGluten, NewAllergen.GLUTEN.getType());
        addToTables(R.id.checkBoxCurstacean, NewAllergen.CRUSTACEAN.getType());
        addToTables(R.id.checkBoxLupines, NewAllergen.LUPINE.getType());
        addToTables(R.id.checkBoxMilk, NewAllergen.LACTOSE.getType());
        addToTables(R.id.checkBoxNuts, NewAllergen.NUTS.getType());
        addToTables(R.id.checkBoxSulfates, NewAllergen.SULFITES.getType());
        addToTables(R.id.checkBoxCeleriac, NewAllergen.CELERIAC.getType());
        addToTables(R.id.checkBoxMustard, NewAllergen.MUSTARD.getType());
        addToTables(R.id.checkBoxSesame, NewAllergen.SESAME.getType());
        addToTables(R.id.checkBoxSoy, NewAllergen.SOYA.getType());
        addToTables(R.id.checkBoxMollusks, NewAllergen.MOLLUSKS.getType());
    }

    @Trace
    void addToTables(int id, String type) {
        mCheckBoxIdToAllergenCode.put(id, type);
        mAllergenCodeToCheckBoxId.put(type, id);
    }

    @AfterViews
    void showPreferences() {
        for (int id : CHECK_BOXES) {
            CheckBox checkBox = (CheckBox) getView().findViewById(id);
            String type = mCheckBoxIdToAllergenCode.get(id);
            boolean isFiltered = mFilteredAllergens.contains(type);
            checkBox.setChecked(isFiltered);
        }
    }

    @Override
    public void onPause() {
        mFilteredAllergens.clear();
        for (int id : CHECK_BOXES) {
            CheckBox checkBox = (CheckBox) getView().findViewById(id);
            String type = mCheckBoxIdToAllergenCode.get(id);
            if (checkBox.isChecked()) {
                mFilteredAllergens.add(type);
            }
        }

        mAllergenFilterPrefs.edit().filteredAllergens()
                .put(mFilteredAllergens).apply();
        Set<String> strings = mAllergenFilterPrefs.filteredAllergens().get();
        super.onPause();
    }

}
