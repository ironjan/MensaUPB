package de.ironjan.mensaupb.menus_ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Locale;

import arrow.core.Either;
import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.api.ClientV3;
import de.ironjan.mensaupb.api.model.Menu;
import de.ironjan.mensaupb.prefs.InternalKeyValueStore_;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

@SuppressWarnings("WeakerAccess")
@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_DATE = "date";
    public static final String ARG_LOCATION = "restaurant";
    public static final int TIMEOUT_30_SECONDS = 30000;

    private final Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());

    @ViewById(R.id.emptyExplanation)
    View mLoadingView;
    @ViewById(R.id.could_not_load_view)
    View mcouldNotLoadView;
    @ViewById(android.R.id.empty)
    View empty;
    @ViewById(android.R.id.list)
    StickyListHeadersListView list;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @ViewById(R.id.txtError)
    TextView txtError;
    @ViewById(R.id.txtClosed)
    TextView txtClosed;
    @ViewById(R.id.emptyExplanation)
    View emptyExplanation;


    @Pref
    InternalKeyValueStore_ mInternalKeyValueStore;

    private ArrayBasedMenuListingAdapter adapter;
    private MenusNavigationCallback navigationCallback;
    private long startedAt = Long.MAX_VALUE;

    public static MenuListingFragment getInstance(String dateAsKey, String restaurant) {
        MenuListingFragment fragment = new MenuListingFragment_();
        Bundle arguments = new Bundle();

        arguments.putString(MenuListingFragment.ARG_DATE, dateAsKey);
        arguments.putString(MenuListingFragment.ARG_LOCATION, restaurant);

        fragment.setArguments(arguments);
        return fragment;
    }

    @AfterViews
    void rememberStartupTime() {
        this.startedAt = System.currentTimeMillis();
    }

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof MenusNavigationCallback)) {
            throw new IllegalArgumentException("MenuListingFragment can only be attached to an Activity implementing MenusNavigationCallback.");
        }
        super.onAttach(context);
        this.navigationCallback = (MenusNavigationCallback) context;

    }

    private String getArgLocation() {
        String location = getArguments().getString(ARG_LOCATION);
        return location.replaceAll("\\*", "%");
    }

    private String getArgDate() {
        return getArguments().getString(ARG_DATE);
    }

    @AfterViews
    void afterViews() {
        prepareListForRefresh();
        loadContent(false);
    }


    @Background
    void loadContent(boolean noCache) {
        Context nonNullContext = getContext();
        if (nonNullContext == null) {
            return;
        }


        final Either<String, Menu[]> either = new ClientV3(nonNullContext).getMenus(getArgLocation(), getArgDate(), noCache);

        if (either.isLeft()) {
            either.mapLeft(s -> {
                showError(s);
                return s;
            });
        } else {
            either.map(menus1 -> {
                showMenusV2(menus1);
                return menus1;
            });
        }
    }

    @UiThread
    void prepareListForRefresh() {
        final Context context = getContext();
        if (context == null) {
            LOGGER.info("MenuListingFragment is not associated to a context at the moment.");
            return;
        }
        mLoadingView.setVisibility(View.VISIBLE);
        mcouldNotLoadView.setVisibility(View.GONE);
        txtError.setText("");

        adapter = new ArrayBasedMenuListingAdapter(context, new ArrayList<>(0));
        list.setAdapter(adapter);
        list.setAreHeadersSticky(false);
        list.setOnItemClickListener((parent, view, position, id) -> listItemClicked(position));
        list.setEmptyView(empty);
    }

    @UiThread
    void showMenusV2(Menu[] menus) {
        adapter.clear();
        adapter.addAll(menus);

        if (menus.length == 0) {
            txtClosed.setVisibility(View.VISIBLE);
            emptyExplanation.setVisibility(View.GONE);
        }else {
            txtClosed.setVisibility(View.GONE);
            emptyExplanation.setVisibility(View.VISIBLE);
        }
    }


    @UiThread
    void showError(String msg) {
        final Context context = getContext();
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
        txtError.setText("Error: " + msg);
        updateLoadingMessage();
    }

    private boolean isEnglish() {
        return Locale.getDefault().getLanguage().startsWith(Locale.ENGLISH.toString());
    }

    @UiThread
    void updateLoadingMessage() {
        mLoadingView.setVisibility(View.GONE);
        mcouldNotLoadView.setVisibility(View.VISIBLE);
    }

    void listItemClicked(int pos) {
        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({})", pos);

        navigationCallback.showMenu(adapter.getItem(pos));

        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({}) done", pos);
    }

    @Override
    public void onRefresh() {
        prepareListForRefresh();
        loadContent(true);
    }


}

