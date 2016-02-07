package de.ironjan.mensaupb.opening_times;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.stw.Restaurant;
import de.ironjan.mensaupb.opening_times.OpeningTimesDialogFragment_;

/**
 * Dialog to show opening times
 */
@EFragment(R.layout.fragment_dialog_opening_times)
public class OpeningTimesDialogFragment extends DialogFragment {

    public static final String ARG_RESTAURANT = "restaurantKey";

    @ViewById
    TextView content;

    String restaurantKey;

    public static OpeningTimesDialogFragment newInstance(String restaurantKey) {
        OpeningTimesDialogFragment f = OpeningTimesDialogFragment_.builder().arg(ARG_RESTAURANT, restaurantKey).build();

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantKey = getArguments().getString(ARG_RESTAURANT);
    }

    @AfterViews
    void displayOpeningTimes() {
        final String openingTimesString;
        Restaurant restaurant = Restaurant.fromKey(restaurantKey);
        switch (restaurant) {
            case MENSA_FORUM:
                openingTimesString = getString(R.string.mensaAcademicaOpeningTimes);
                break;
            case BISTRO_HOTSPOT:
                openingTimesString = getString(R.string.hotspotOpeningTimes);
                break;
            case GRILL_CAFE:
                openingTimesString = getString(R.string.grillCafeOpeningTimes);
                break;
            case CAFETE:
                openingTimesString = getString(R.string.cafeteOpeningTimes);
                break;
            case MENSA_ACADEMICA:
            default:
                openingTimesString = getString(R.string.mensaAcademicaOpeningTimes);
                break;
        }
        Spanned html = Html.fromHtml(openingTimesString);
        content.setText(html);

        Dialog dialog = getDialog();
        if (dialog != null)
            dialog.setTitle(restaurant.getNameStringId());
    }

    @Click(R.id.buttonClose)
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
