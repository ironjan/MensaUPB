package de.ironjan.mensaupb.fragments;


import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.text.*;
import android.view.*;
import android.widget.*;

import com.j256.ormlite.android.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.koushikdutta.async.future.*;
import com.koushikdutta.ion.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import java.text.*;
import java.util.*;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.activities.*;
import de.ironjan.mensaupb.helpers.*;
import de.ironjan.mensaupb.persistence.*;
import de.ironjan.mensaupb.stw.*;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends Fragment {

    public static final String ARG_ID = "ARG_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDetailFragment.class.getSimpleName());
    @SuppressWarnings("WeakerAccess")
    @ViewById
    TextView textName, textCategory, textAllergens, textPrice, textRestaurant, textDate, textBadges;
    @SuppressWarnings("WeakerAccess")
    @ViewById
    ImageView image;
    @SuppressWarnings("WeakerAccess")
    @ViewById
    ProgressBar progressBar;
    @SuppressWarnings("WeakerAccess")
    @StringRes
    String localizedDatePattern;

    @SuppressWarnings("WeakerAccess")
    @Bean
    RestaurantHelper mRestaurantHelper;
    private RawMenu mMenu;

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

    private RawMenu loadMenu(long _id) throws java.sql.SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseHelper helper = (databaseManager.getHelper(getActivity()));
        ConnectionSource connectionSource = new AndroidConnectionSource(helper);
        Dao<RawMenu, Long> dao = DaoManager.createDao(connectionSource, RawMenu.class);
        return dao.queryForId(_id);
    }

    private void bindMenuDataToViews(RawMenu rawMenu) {
        bindNameAndCategory(rawMenu);
        bindRestaurant(rawMenu);
        bindDate(rawMenu);
        bindPrice(rawMenu);
        bindAllergens(rawMenu);
        bindBadges(rawMenu);
        loadImage(rawMenu);
    }

    private void bindNameAndCategory(RawMenu rawMenu) {
        boolean isEnglish = Locale.getDefault().getLanguage().startsWith(Locale.ENGLISH.toString());
        final String name = (isEnglish) ? rawMenu.getName_en() : rawMenu.getName_de();
        final String category = (isEnglish) ? rawMenu.getCategory_en() : rawMenu.getCategory_de();

        textName.setText(name);
        textCategory.setText(category);
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        if (activity == null) return;
        ActionBar supportActionBar = activity.getSupportActionBar();
        if (supportActionBar == null) return;
        supportActionBar.setTitle(name);
    }

    private void bindBadges(RawMenu rawMenu) {
        Badge[] badges = rawMenu.getBadges();

        if (badges == null || badges.length < 1) {
            textBadges.setText("");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder(getActivity().getString(badges[0].getStringId()));
        for (int i = 1; i < badges.length; i++) {
            String badgeString = getActivity().getString(badges[i].getStringId());
            stringBuilder.append(", ")
                    .append(badgeString);
        }
        textBadges.setText(stringBuilder.toString());
    }

    private void bindRestaurant(RawMenu rawMenu) {
        String restaurantId = rawMenu.getRestaurant();
        String restaurantName = mRestaurantHelper.getNameFor(restaurantId);
        textRestaurant.setText(restaurantName);
    }

    private void bindDate(RawMenu rawMenu) {
        SimpleDateFormat sdf = new SimpleDateFormat(localizedDatePattern);
        textDate.setText(sdf.format(rawMenu.getDate()));
    }

    private void bindPrice(RawMenu rawMenu) {
        double price = rawMenu.getPriceStudents();
        String priceAsString = String.format(Locale.GERMAN, "%.2f €", price);

        textPrice.setText(priceAsString);
        if (rawMenu.getPricetype() == PriceType.WEIGHT) {
            textPrice.append("/100g");
        }
    }

    private void bindAllergens(RawMenu rawMenu) {
        boolean notFirst = false;
        for (NewAllergen allergen : rawMenu.getAllergens()) {
            if (notFirst) {
                textAllergens.append("\n");
            } else {
                notFirst = true;
            }
            int stringId = allergen.getStringId();
            String string = getResources().getString(stringId);
            textAllergens.append(string);
        }
    }

    /**
     * Asynchronously load the image of the supplied menu
     *
     * @param rawMenu The menu to load a image for
     */
    private void loadImage(RawMenu rawMenu) {
        if (!TextUtils.isEmpty(rawMenu.getImage())) {
            Ion.with(image)
                    .load(rawMenu.getImage())
                    .setCallback(new FutureCallback<ImageView>() {
                        @Override
                        public void onCompleted(Exception e, ImageView result) {
                            if (e != null) {
                                e.printStackTrace();
                                image.setVisibility(View.GONE);
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            image.setVisibility(ImageView.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void addExtrasTo(Intent intent) {
        intent.putExtra(Menus.KEY_DATE, DateHelper.toString(mMenu.getDate()));
        intent.putExtra(Menus.KEY_RESTAURANT, mMenu.getRestaurant());
    }

}
