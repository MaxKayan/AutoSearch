package net.inqer.autosearch.ui.dialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import net.inqer.autosearch.data.model.ListItem;
import net.inqer.autosearch.databinding.DialogSearchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DialogListAdapter<T extends ListItem> extends ListAdapter<T, SearchItemViewHolder> implements Filterable {
    private static final String TAG = "DialogListAdapter";
    private final SearchItemViewHolder.SearchItemClickListener itemClickListener;
    private List<T> fullList = new ArrayList<>();
    private DialogSearchItemBinding binding;

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d(TAG, "performFiltering: " + constraint);
            List<T> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                Log.d(TAG, "performFiltering: adding full: " + fullList.size());
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (T item : fullList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            submitList(((List<T>) results.values));
        }
    };

    protected DialogListAdapter(SearchItemViewHolder.SearchItemClickListener listener) {
        super(new ListItemDiffUtil<T>());
        this.itemClickListener = listener;
    }

    public void setNewList(List<T> list) {
        Log.d(TAG, "setNewList: " + list.size());
        fullList = list;
        submitList(list);
        Log.d(TAG, "setNewList: list now: " + getItemCount());
    }

    @Override
    public void submitList(@Nullable List<T> list) {
        super.submitList(list);
        Log.d(TAG, "submitList: " + (list == null ? "null" : list.size()));
    }

    public T getItemAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DialogSearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        holder.bind(getItem(position), itemClickListener, position);
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }
}
