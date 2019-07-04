package de.ironjan.mensaupb.menus_ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import arrow.core.Either;
import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.api.ClientV2;
import de.ironjan.mensaupb.api.model.Menu;
import de.ironjan.mensaupb.model.LocalizedMenu;
import de.ironjan.mensaupb.stw.Restaurant;
import de.ironjan.mensaupb.stw.rest_api.Allergen;
import de.ironjan.mensaupb.stw.rest_api.Badge;
import de.ironjan.mensaupb.stw.rest_api.PriceType;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;
import kotlin.jvm.functions.Function1;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends Fragment {

    public static final String ARG_KEY = "ARG_KEY";
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
    @ViewById(android.R.id.progress)
    ProgressBar indefiniteProgressBar;

    @SuppressWarnings("WeakerAccess")
    @StringRes
    String localizedDatePattern;

    private StwMenu mMenu;

    public static MenuDetailFragment newInstance(String key) {
        if (BuildConfig.DEBUG)
            LOGGER.debug("newInstance({})", key);

        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        MenuDetailFragment menuDetailFragment = new MenuDetailFragment_();
        menuDetailFragment.setArguments(args);

        if (BuildConfig.DEBUG)
            LOGGER.debug("Created new MenuDetailFragment({})", key);

        if (BuildConfig.DEBUG)
            LOGGER.debug("newInstance({}) done", key);
        return menuDetailFragment;
    }

    @AfterViews
    void bindData() {
        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData()");

        final String key = getArguments().getString(ARG_KEY);

        loadMenu(key);

        if (BuildConfig.DEBUG)
            LOGGER.debug("bindData() done");
    }

    @Background
    void loadMenu(String key) {
        final Either<String, Menu> either = ClientV2.Companion.getClient().getMenu(key);

        if (either.isLeft()) {
            either.mapLeft(new Function1<String, String>() {
                @Override
                public String invoke(String s) {
                    showError(s);
                    return s;
                }
            });
        } else {
            either.map(new Function1<Menu, Menu>() {
                @Override
                public Menu invoke(Menu menu) {
                    showMenu(menu);
                    return menu;
                }
            });
        }
    }

    @UiThread
    void showError(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    private void showMenu(Menu menu) {
        bindMenuDataToViews(menu);
    }

    @UiThread
    void bindMenuDataToViews(Menu menu) {
        boolean isEnglish = Locale.getDefault().getLanguage().startsWith(Locale.ENGLISH.toString());
        final LocalizedMenu localizedMenu = new LocalizedMenu(menu, isEnglish);

        if (textName != null) textName.setText(localizedMenu.getName());
        if (textCategory != null) textCategory.setText(localizedMenu.getCategory());

        bindDescription(localizedMenu.getDescription());

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar supportActionBar = activity.getSupportActionBar();
            if (supportActionBar != null) supportActionBar.setTitle("");
        }

        bindRestaurant(localizedMenu);
        bindDate(localizedMenu);
        bindPrice(localizedMenu);
        bindAllergens(localizedMenu);
        bindBadges(localizedMenu);
        loadImage(localizedMenu, false);
    }

    private void bindDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            if (textDescription != null) textDescription.setVisibility(View.GONE);
        } else {
            if (textDescription != null) textDescription.setText(description);
        }
    }

    private void bindBadges(LocalizedMenu stwMenu) {
        List<Badge> badges = new ArrayList<>();

        final String[] badgesAsString = stwMenu.getBadges();
        for (String badgeKey : badgesAsString) {
            badges.add(Badge.fromString(badgeKey));
        }

        if (badges.isEmpty()) {
            if (textBadges != null) textBadges.setVisibility(View.GONE);
            return;
        }
        if (textBadges != null) textBadges.setVisibility(View.VISIBLE);
        StringBuilder stringBuilder = new StringBuilder(getActivity().getString(badges.get(0).getStringId()));
        for (int i = 1; i < badges.size(); i++) {
            String badgeString = getActivity().getString(badges.get(i).getStringId());
            stringBuilder.append(", ")
                    .append(badgeString);
        }
        if (textBadges != null) textBadges.setText(stringBuilder.toString());
    }

    private void bindRestaurant(LocalizedMenu stwMenu) {
        String restaurantId = stwMenu.getRestaurant();
        int restaurantNameId = Restaurant.fromKey(restaurantId).getNameStringId();
        if (textRestaurant != null) textRestaurant.setText(restaurantNameId);
    }

    private void bindDate(LocalizedMenu stwMenu) {
        SimpleDateFormat sdf = new SimpleDateFormat(localizedDatePattern);
        final Date date = stwMenu.getDate();
        if (date != null) {
            if (textDate != null) textDate.setText(sdf.format(date));
        }
    }

    private void bindPrice(LocalizedMenu stwMenu) {
        double price = stwMenu.getPriceStudents();
        String priceAsString = String.format(Locale.GERMAN, "%.2f €", price);

        if (textPrice != null) textPrice.setText(priceAsString);
        if (PriceType.Constants.WEIGHT_STRING.equals(stwMenu.getPricetype())) {
            if (textPrice != null) textPrice.append("/100g");
        }
    }

    private void bindAllergens(LocalizedMenu stwMenu) {
        List<Allergen> allergens = new ArrayList<>();
        for (String allergenKey : stwMenu.getAllergens()) {
            allergens.add(Allergen.fromString(allergenKey));
        }
        if (allergens.isEmpty()) {
            hideAllergenList();
        } else {
            showAllergensList(allergens);
        }
    }

    private void showAllergensList(List<Allergen> allergens) {
        boolean notFirst = false;
        StringBuilder allergensListAsStringBuffer = new StringBuilder();
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
        if (textAllergens != null) {
            textAllergens.setText(allergensListAsStringBuffer.toString());
            textAllergens.setVisibility(View.VISIBLE);
            textAllergensHeader.setVisibility(View.VISIBLE);
        }
    }

    private void hideAllergenList() {
        if (textAllergens != null) {
            textAllergens.setVisibility(View.GONE);
            textAllergensHeader.setVisibility(View.GONE);
        }
    }

    /**
     * Asynchronously load the image of the supplied menu
     *
     * @param stwMenu The menu to load a image for
     */
    @Background
    void loadImage(LocalizedMenu stwMenu, boolean forced) {
        setProgressVisibility(View.VISIBLE);

        LOGGER.debug("loadImage()");
        final long start = System.currentTimeMillis();

        LOGGER.debug("loadImage() - start={}", start);
        final String uri;
        if (!TextUtils.isEmpty(stwMenu.getImage())) {
            uri = stwMenu.getImage();
        } else {
            uri = URI_NO_IMAGE_FILE;
        }

        LOGGER.debug("loadImage() - URI set [{}]", start);

        try {
            Context context = getContext();
            if (context == null) {
                return;
            }

            Bitmap bitmap = Ion.with(context)
                    .load(uri)
                    .setLogging("MenuDetailFragment", Log.VERBOSE)
                    .progressBar(progressBar)
                    .asBitmap()
                    .get();
            applyLoadedImage(bitmap);
        } catch (InterruptedException | ExecutionException e) {
            applyErrorImage();
            LOGGER.error("InterruptedException: {}", e);
        }


        LOGGER.debug("loadImage() done");
    }

    @UiThread
    void setProgressVisibility(int visible) {
        if (progressBar != null) progressBar.setVisibility(visible);
        if (indefiniteProgressBar != null) indefiniteProgressBar.setVisibility(visible);
    }

    @UiThread
    void applyErrorImage() {
        Ion.with(getActivity())
                .load(URI_NO_IMAGE_FILE)
                .intoImageView(image);
        setProgressVisibility(View.GONE);
    }

    @UiThread
    void applyLoadedImage(Bitmap bitmap) {
        if(image!=null) image.setImageBitmap(bitmap);
        setProgressVisibility(View.GONE);
    }

}
