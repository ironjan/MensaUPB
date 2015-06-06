package de.ironjan.mensaupb.prefs;

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
import de.ironjan.mensaupb.stw.rest_api.Allergen;

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
        addToTables(R.id.checkBoxEggs, Allergen.EGGS.getType());
        addToTables(R.id.checkBoxPeanuts, Allergen.PEANUTS.getType());
        addToTables(R.id.checkBoxFish, Allergen.FISH.getType());
        addToTables(R.id.checkBoxGluten, Allergen.GLUTEN.getType());
        addToTables(R.id.checkBoxCurstacean, Allergen.CRUSTACEAN.getType());
        addToTables(R.id.checkBoxLupines, Allergen.LUPINE.getType());
        addToTables(R.id.checkBoxMilk, Allergen.LACTOSE.getType());
        addToTables(R.id.checkBoxNuts, Allergen.NUTS.getType());
        addToTables(R.id.checkBoxSulfates, Allergen.SULFITES.getType());
        addToTables(R.id.checkBoxCeleriac, Allergen.CELERIAC.getType());
        addToTables(R.id.checkBoxMustard, Allergen.MUSTARD.getType());
        addToTables(R.id.checkBoxSesame, Allergen.SESAME.getType());
        addToTables(R.id.checkBoxSoy, Allergen.SOYA.getType());
        addToTables(R.id.checkBoxMollusks, Allergen.MOLLUSKS.getType());
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
