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
@EFragment(R.layout.fragment_additional_settings)
public class AdditionalSettingsFragment extends Fragment {
    private static final int[] CHECK_BOXES = {
            R.id.checkBoxAntioxidants,
            R.id.checkBoxColored,
            R.id.checkBoxTasteEnhancer,
            R.id.checkBoxBlackened,
            R.id.checkBoxSulfured,
            R.id.checkBoxWaxed,
            R.id.checkBoxCaffeine,
            R.id.checkBoxConserved,
            R.id.checkBoxLactoProtein,
            R.id.checkBoxNitrateSalt,
            R.id.checkBoxPhenylalanine,
            R.id.checkBoxPhosphate,
            R.id.checkBoxSweetener,
            R.id.checkBoxQuinine,
            R.id.checkBoxTaurine
    };


    @Pref
    AllergenFilterPrefs_ mAllergenFilterPrefs;
    private Set<String> mFilteredAllergens = new HashSet<>();
    private HashMap<Integer, String> mCheckBoxIdToAllergenCode = new HashMap<>();
    private HashMap<String, Integer> mAllergenCodeToCheckBoxId = new HashMap<>();

    @AfterInject
    @Trace(level = 5)
    void loadPreferences() {
        mFilteredAllergens = mAllergenFilterPrefs
                .filteredAdditionals()
                .getOr(new HashSet<String>());
        buildHashMaps();
    }

    @Trace
    void buildHashMaps() {
        addToTables(R.id.checkBoxAntioxidants, Allergen.ANTIOXIDANTS.getType());
        addToTables(R.id.checkBoxColored, Allergen.COLORED.getType());
        addToTables(R.id.checkBoxTasteEnhancer, Allergen.FLAVOR_ENHANCERS.getType());
        addToTables(R.id.checkBoxBlackened, Allergen.BLACKENED.getType());
        addToTables(R.id.checkBoxSulfured, Allergen.SULFURATED.getType());
        addToTables(R.id.checkBoxWaxed, Allergen.WAXED.getType());
        addToTables(R.id.checkBoxCaffeine, Allergen.COFFEINE.getType());
        addToTables(R.id.checkBoxConserved, Allergen.CONSERVED.getType());
        addToTables(R.id.checkBoxLactoProtein, Allergen.LACTOPROTEIN.getType());
        addToTables(R.id.checkBoxNitrateSalt, Allergen.NITRATE_SALT.getType());
        addToTables(R.id.checkBoxPhenylalanine, Allergen.PHENYLALANINE.getType());
        addToTables(R.id.checkBoxPhosphate, Allergen.PHOSPHAT.getType());
        addToTables(R.id.checkBoxSweetener, Allergen.SWEETENER.getType());
        addToTables(R.id.checkBoxQuinine, Allergen.QUININE.getType());
        addToTables(R.id.checkBoxTaurine, Allergen.TAURINE.getType());
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

        mAllergenFilterPrefs.edit().filteredAdditionals()
                .put(mFilteredAllergens).apply();
        Set<String> strings = mAllergenFilterPrefs.filteredAdditionals().get();
        super.onPause();
    }
}
