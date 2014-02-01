package de.najidev.mensaupb.activities;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;

import org.androidannotations.annotations.*;

import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.fragments.*;

@EActivity(R.layout.activity_menu_listing)
public class Test extends ActionBarActivity {

    @AfterViews
    void showMenus() {
        Bundle arguments = new Bundle();
        arguments.putString(MenuListingFragment.ARG_DATE, getNextWeekDay());
        arguments.putString(MenuListingFragment.ARG_LOCATION, "Mensa");

        final MenuListingFragment_ fragment = new MenuListingFragment_();
        fragment.setArguments(arguments);

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, fragment);
        ft.commit();
    }

    private String getNextWeekDay() {
        Calendar cal = Calendar.getInstance();
        while (dayIsWeekend(cal)) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }

        final String day = de.najidev.mensaupb.stw.Menu.DATABASE_DATE_FORMAT.format(cal.getTime());

        return day;
    }

    private boolean dayIsWeekend(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

}
