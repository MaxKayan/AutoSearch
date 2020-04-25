package net.inqer.autosearch.ui.dialog.listsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.inqer.autosearch.R;
import net.inqer.autosearch.data.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteListItemAdapter<T extends ListItem> extends ArrayAdapter<T> {
    private List<T> listItemsFull;

    @NonNull
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_search_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.d_item_label);

        T listItem = getItem(position);

        if (listItem != null) {
            textViewName.setText(listItem.getName());
        }

        return convertView;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<T> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length()==0) {
                suggestions.addAll(listItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (T item : listItemsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll(((List) results.values));
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((T) resultValue).getName();
        }
    };

    public AutoCompleteListItemAdapter(@NonNull Context context, @NonNull List<T> listItems) {
        super(context, 0, listItems);
        listItemsFull = new ArrayList<>(listItems);
    }
}
