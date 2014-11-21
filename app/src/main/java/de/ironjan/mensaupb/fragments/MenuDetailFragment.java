package de.ironjan.mensaupb.fragments;


import android.app.*;
import android.os.*;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.j256.ormlite.android.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.library.stw.*;
import de.ironjan.mensaupb.persistence.*;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends DialogFragment {

    public static final String ARG_ID = "ARG_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDetailFragment.class.getSimpleName());
    @ViewById
    TextView textName, textCategory, textAllergens, textPrice;
    @ViewById
    ImageView image;
    @ViewById
    ProgressBar progressBar;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Details");
        return dialog;
    }

    /**
     * Asynchronously load the image of the supplied menu
     *
     * @param rawMenu
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

    @AfterViews
    void bindData() {
        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData()");

        final long _id = getArguments().getLong(ARG_ID);

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            DatabaseHelper helper = (databaseManager.getHelper(getActivity()));
            ConnectionSource connectionSource = new AndroidConnectionSource(helper);
            Dao<RawMenu, Long> dao = DaoManager.createDao(connectionSource, RawMenu.class);
            RawMenu rawMenu = dao.queryForId(_id);
            if (rawMenu != null) {
                loadImage(rawMenu);
                textName.setText(rawMenu.getName_de());
                textCategory.setText(rawMenu.getCategory_de());

                double price = rawMenu.getPriceStudents();
                String priceAsString = String.format(Locale.GERMAN, "%.2f €", price);

                textPrice.setText(priceAsString);
                if (rawMenu.getPricetype() == PriceType.WEIGHT) {
                    textPrice.append("/100g");
                }

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
            } else {
                // notify fail load
            }
        } catch (java.sql.SQLException e) {
            LOGGER.error("Could not load menu details", e);
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData()");
    }
}
