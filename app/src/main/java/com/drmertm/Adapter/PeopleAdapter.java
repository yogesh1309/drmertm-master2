package com.drmertm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.drmertm.ModelClass.ViewOfficeOrderData;
import com.drmertm.R;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends ArrayAdapter<ViewOfficeOrderData> {

    Context context;
    int resource, textViewResourceId;
    List<ViewOfficeOrderData> items, tempItems, suggestions;

    public PeopleAdapter(Context context, int resource, int textViewResourceId, List<ViewOfficeOrderData> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<ViewOfficeOrderData>(items); // this makes the difference.
        suggestions = new ArrayList<ViewOfficeOrderData>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.auto_completetextview_row, parent, false);
        }
        ViewOfficeOrderData people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getDepartmentName()+"\n"+people.getHeadName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ViewOfficeOrderData) resultValue).getDepartmentName()+((ViewOfficeOrderData) resultValue).getHeadName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ViewOfficeOrderData people : tempItems) {
                    if (people.getDepartmentName().toLowerCase().contains(constraint.toString().toLowerCase()) || people.getHeadName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<ViewOfficeOrderData> filterList = (ArrayList<ViewOfficeOrderData>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ViewOfficeOrderData people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}