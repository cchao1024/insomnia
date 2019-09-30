package com.cchao.insomnia.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.cchao.simplelib.core.Logs;

import java.util.ArrayList;
import java.util.Locale;

public class EmailFilterAdapter extends ArrayAdapter<String> {

    private ArrayList<String> mItems;
    private ArrayList<String> mItemsAll;
    private ArrayList<String> mSuggestions;

    public EmailFilterAdapter(Context context, ArrayList<String> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.mItems = items;
        this.mItemsAll = (ArrayList<String>) items.clone();
        this.mSuggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(android.R.layout.simple_list_item_1, null);
        }
        String customer = mItems.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v;
            if (customerNameLabel != null) {
                customerNameLabel.setText(customer);
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    final Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = (String) resultValue;
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                String palabra = constraint.toString();
                if (!TextUtils.isEmpty(palabra)) {
                    String palabra2 = null;
                    String antesArroba = palabra;

                    if (palabra.contains("@")) {
                        palabra2 = palabra.substring(palabra.indexOf("@"));
                        try {
                            antesArroba = palabra.substring(0, palabra.indexOf("@"));
                        } catch (Exception ex) {
                            antesArroba = "";
                        }
                    }

                    mSuggestions.clear();
                    for (String customer : mItemsAll) {
                        if (TextUtils.isEmpty(palabra2)) {
                            mSuggestions.add(antesArroba + customer);
                        } else if (customer.toLowerCase(Locale.US).startsWith(palabra2.toLowerCase(Locale.US))
                            && customer.length() != palabra2.length()) {
                            mSuggestions.add(antesArroba + customer);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mSuggestions;
                    filterResults.count = mSuggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                ArrayList<String> filteredList = (ArrayList<String>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    notifyDataSetChanged();
                    for (int i = 0; i < filteredList.size(); i++) {
                        add(filteredList.get(i));
                    }
                    notifyDataSetChanged();
                }
            } catch (Exception ex) {
                Logs.logException(ex);
            }
        }
    };

}
