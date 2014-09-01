package de.ironjan.mensaupb.fragments;


import android.os.*;
import android.support.v4.app.*;
import android.view.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.adapters.*;
import de.ironjan.mensaupb.library.stw.*;

@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends ListFragment {

    public static String ARG_DATE = "date";
    public static String ARG_LOCATION = "location";

    private final Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());
    private MenuListingAdapter adapter;

    @ViewById(android.R.id.empty)
    View mLoadingView;
    @ViewById(android.R.id.content)
    View mNoMenus;
    @Bean
    OpeningTimesLookup mOpeningTimesLookup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mOpeningTimesLookup.isPotentiallyClosed(getArgDate()
                , getArgLocation())) {
            return inflater.inflate(R.layout.fragment_menu_listing_closed, null);
        }
        return null;
    }


    private String getArgLocation() {
        return getArguments().getString(ARG_LOCATION);
    }

    private String getArgDate() {
        return getArguments().getString(ARG_DATE);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent();

    }

    void loadContent() {
        adapter = new MenuListingAdapter(getActivity(), getArgDate(), getArgLocation());
        adapter.setViewBinder(new MenuDetailViewBinder());
        getLoaderManager().initLoader(0, null, adapter);
        setListAdapter(adapter);
    }


    @ItemClick
    void listItemClicked(int pos) {
        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({})", pos);

        final long _id = getListAdapter().getItemId(pos);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        MenuDetailFragment fragment = MenuDetailFragment.newInstance(_id);
        fragment.show(fm, "fragment_edit_name");

        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({}) done", pos);
    }

}
