package de.ironjan.mensaupb.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.adapters.MenuDetailViewBinder;
import de.ironjan.mensaupb.adapters.MenuListingAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends Fragment {

    public static String ARG_DATE = "date";
    public static String ARG_LOCATION = "restaurant";

    private final Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());
    @ViewById(android.R.id.empty)
    View mLoadingView;
    @ViewById(android.R.id.content)
    View mNoMenus;
    @ViewById(android.R.id.list)
    StickyListHeadersListView list;

    private MenuListingAdapter adapter;

    private String getArgLocation() {
        return getArguments().getString(ARG_LOCATION);
    }

    private String getArgDate() {
        return getArguments().getString(ARG_DATE);
    }

    @AfterViews
    void loadContent() {
        adapter = new MenuListingAdapter(getActivity(), getArgDate(), getArgLocation());
        adapter.setViewBinder(new MenuDetailViewBinder());
        getLoaderManager().initLoader(0, null, adapter);
        list.setAdapter(adapter);
    }


    @ItemClick
    void listItemClicked(int pos) {
        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({})", pos);

        final long _id = adapter.getItemId(pos);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        MenuDetailFragment fragment = MenuDetailFragment.newInstance(_id);
        fragment.show(fm, "fragment_edit_name");

        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({}) done", pos);
    }

}
