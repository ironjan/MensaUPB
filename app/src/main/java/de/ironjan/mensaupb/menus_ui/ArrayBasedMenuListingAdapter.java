package de.ironjan.mensaupb.menus_ui;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.model.LocalizedMenu;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * An adapter to load the list of menus for a MenuListingFragment.
 */
public class ArrayBasedMenuListingAdapter
        extends ArrayAdapter<LocalizedMenu>
        implements StickyListHeadersAdapter {
    @NonNull
    private final Context context;

    public ArrayBasedMenuListingAdapter(@NonNull Context context, @NonNull List<LocalizedMenu> menus) {
        super(context, R.layout.view_menu_list_item, menus);
        this.context = context;
    }


    @Override
    public View getHeaderView(int pos, View convertView, ViewGroup parent) {
        String categoryOfPosition = getLocalizedCategoryOfPosition(pos);
        MenuListingHeaderView view;
        if (convertView == null) {
            view = MenuListingHeaderView_.build(context);
        } else {
            view = (MenuListingHeaderView) convertView;
        }
        view.setText(categoryOfPosition);

        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_menu_list_item, parent, false);
        }

        LocalizedMenu menu = getItem(position);
        MenuDetailViewBinder.setViewValue(convertView.findViewById(R.id.textName), menu);
        MenuDetailViewBinder.setViewValue(convertView.findViewById(R.id.textPrice), menu);
        MenuDetailViewBinder.setViewValue(convertView.findViewById(R.id.textPricePer100g), menu);
        MenuDetailViewBinder.setViewValue(convertView.findViewById(R.id.textBadges), menu);

        return convertView;
    }


    @Override
    public long getHeaderId(int pos) {
        String category = getLocalizedCategoryOfPosition(pos);
        return category.hashCode();
    }

    private String getLocalizedCategoryOfPosition(int pos) {
        final LocalizedMenu item = getItem(pos);
        if (item != null) {
            String category = item.getCategory();
            if (category != null) {
                return category;
            }
        }

        return "";
    }

    public String getKey(int position) {
        return getItem(position).getKey();
    }
}
