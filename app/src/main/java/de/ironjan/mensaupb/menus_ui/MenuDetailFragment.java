package de.ironjan.mensaupb.menus_ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.menus_ui.MenuDetailFragment_;
import de.ironjan.mensaupb.persistence.DatabaseHelper;
import de.ironjan.mensaupb.persistence.DatabaseManager;
import de.ironjan.mensaupb.stw.Restaurant;
import de.ironjan.mensaupb.stw.rest_api.Allergen;
import de.ironjan.mensaupb.stw.rest_api.Badge;
import de.ironjan.mensaupb.stw.rest_api.PriceType;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends Fragment {

    public static final String ARG_ID = "ARG_ID";
    public static final String URI_NO_IMAGE_FILE = "file:///android_asset/menu_has_no_image.png";
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDetailFragment.class.getSimpleName());
    @SuppressWarnings("WeakerAccess")
    @ViewById
    TextView textName, textCategory, textAllergensHeader, textAllergens, textPrice, textRestaurant, textDate, textBadges, textDescription;
    @SuppressWarnings("WeakerAccess")
    @ViewById
    ImageView image;
    @SuppressWarnings("WeakerAccess")
    @ViewById
    ProgressBar progressBar;
    @SuppressWarnings("WeakerAccess")
    @StringRes
    String localizedDatePattern;

    private StwMenu mMenu;

    public static MenuDetailFragment newInstance(long _id) {
        if (BuildConfig.DEBUG)
            LOGGER.debug("newInstance({})", _id);

        Bundle args = new Bundle();
        args.putLong(ARG_ID, _id);

        MenuDetailFragment menuDetailFragment = new MenuDetailFragment_();
        menuDetailFragment.setArguments(args);

        if (BuildConfig.DEBUG)
            LOGGER.debug("Created new MenuDetailFragment({})", _id);

        if (BuildConfig.DEBUG)
            LOGGER.debug("newInstance({}) done", _id);
        return menuDetailFragment;
    }

    @AfterViews
    void bindData() {
        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData()");

        final long _id = getArguments().getLong(ARG_ID);

        try {
            mMenu = loadMenu(_id);
            if (mMenu != null) {
                bindMenuDataToViews(mMenu);
            } else {
                // FIXME notify fail load
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("Could not load menu details", e);
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData()");
    }

    private StwMenu loadMenu(long _id) throws java.sql.SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseHelper helper = (databaseManager.getHelper(getActivity()));
        ConnectionSource connectionSource = new AndroidConnectionSource(helper);
        Dao<StwMenu, Long> dao = DaoManager.createDao(connectionSource, StwMenu.class);
        return dao.queryForId(_id);
    }

    private void bindMenuDataToViews(StwMenu stwMenu) {
        bindManuallyLocalizedData(stwMenu);
        bindRestaurant(stwMenu);
        bindDate(stwMenu);
        bindPrice(stwMenu);
        bindAllergens(stwMenu);
        bindBadges(stwMenu);
        loadImage(stwMenu);
    }

    private void bindManuallyLocalizedData(StwMenu stwMenu) {
        boolean isEnglish = Locale.getDefault().getLanguage().startsWith(Locale.ENGLISH.toString());
        final String name = (isEnglish) ? stwMenu.getName_en() : stwMenu.getName_de();
        final String category = (isEnglish) ? stwMenu.getCategory_en() : stwMenu.getCategory_de();
        final String description = (isEnglish) ? stwMenu.getDescription_en() : stwMenu.getDescription_de();

        textName.setText(name);
        textCategory.setText(category);

        bindDescription(description);

        ActionBarActivity activity = (ActionBarActivity) getActivity();
        if (activity == null) return;
        ActionBar supportActionBar = activity.getSupportActionBar();
        if (supportActionBar == null) return;
        supportActionBar.setTitle("");
    }

    private void bindDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            textDescription.setVisibility(View.GONE);
        } else {
            textDescription.setText(description);
        }
    }

    private void bindBadges(StwMenu stwMenu) {
        Badge[] badges = stwMenu.getBadges();

        if (badges == null || badges.length < 1) {
            textBadges.setVisibility(View.GONE);
            return;
        }
        textBadges.setVisibility(View.VISIBLE);
        StringBuilder stringBuilder = new StringBuilder(getActivity().getString(badges[0].getStringId()));
        for (int i = 1; i < badges.length; i++) {
            String badgeString = getActivity().getString(badges[i].getStringId());
            stringBuilder.append(", ")
                    .append(badgeString);
        }
        textBadges.setText(stringBuilder.toString());
    }

    private void bindRestaurant(StwMenu stwMenu) {
        String restaurantId = stwMenu.getRestaurant();
        int restaurantNameId = Restaurant.fromKey(restaurantId).getNameStringId();
        textRestaurant.setText(restaurantNameId);
    }

    private void bindDate(StwMenu stwMenu) {
        SimpleDateFormat sdf = new SimpleDateFormat(localizedDatePattern);
        textDate.setText(sdf.format(stwMenu.getDate()));
    }

    private void bindPrice(StwMenu stwMenu) {
        double price = stwMenu.getPriceStudents();
        String priceAsString = String.format(Locale.GERMAN, "%.2f €", price);

        textPrice.setText(priceAsString);
        if (stwMenu.getPricetype() == PriceType.WEIGHT) {
            textPrice.append("/100g");
        }
    }

    private void bindAllergens(StwMenu stwMenu) {
        Allergen[] allergens = stwMenu.getAllergens();

        if (allergens == null || allergens.length == 0) {
            hideAllergenList();
        } else {
            showAllergensList(allergens);
        }

    }

    private void showAllergensList(Allergen[] allergens) {
        boolean notFirst = false;
        StringBuffer allergensListAsStringBuffer = new StringBuffer();
        for (Allergen allergen : allergens) {
            if (allergen != null) {
                if (notFirst) {
                    allergensListAsStringBuffer.append("\n");
                } else {
                    notFirst = true;
                }
                int stringId = allergen.getStringId();
                String string = getResources().getString(stringId);
                allergensListAsStringBuffer.append(string);
            }
        }
        textAllergens.setText(allergensListAsStringBuffer.toString());
        textAllergens.setVisibility(View.VISIBLE);
        textAllergensHeader.setVisibility(View.VISIBLE);
    }

    private void hideAllergenList() {
        textAllergens.setVisibility(View.GONE);
        textAllergensHeader.setVisibility(View.GONE);
    }

    /**
     * Asynchronously load the image of the supplied menu
     *
     * @param stwMenu The menu to load a image for
     */
    private void loadImage(StwMenu stwMenu) {

        final String uri;
        if (!TextUtils.isEmpty(stwMenu.getImage())) {
            uri = stwMenu.getImage();
        } else {
            uri = URI_NO_IMAGE_FILE;
        }

        Ion.with(image)
                .load(uri)
                .setCallback(new FutureCallback<ImageView>() {
                    @Override
                    public void onCompleted(Exception e, ImageView result) {
                        if (e != null) {
                            LOGGER.error(e.getMessage(), e);
                            Ion.with(image).load(URI_NO_IMAGE_FILE);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}
