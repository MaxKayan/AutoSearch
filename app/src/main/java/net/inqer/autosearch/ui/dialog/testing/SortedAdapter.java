package net.inqer.autosearch.ui.dialog.testing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.databinding.DialogSearchItemBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortedAdapter extends SortedListAdapter<Region> implements Filterable {
    private static final String TAG = "SortedAdapter";
    private DialogSearchItemBinding binding;
    private List<Region> fullList = new ArrayList<>();

    public SortedAdapter(@NonNull Context context, @NonNull Class<Region> itemClass, @NonNull Comparator<Region> comparator) {
        super(context, itemClass, comparator);
    }

    @NonNull
    @Override
    protected ViewHolder<? extends Region> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        binding = DialogSearchItemBinding.inflate(inflater, parent, false);
        return new SortedItemViewHolder(binding);
    }

    public void setFullList(List<Region> list) {
        fullList = list;
    }

    @Override
    public Filter getFilter() {
        return locationFilter;
    }

    private Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d(TAG, "performFiltering: "+constraint);
            List<Region> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Region item : fullList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            edit().replaceAll(((List) results));
        }
    };

    public static class SortedItemViewHolder extends SortedListAdapter.ViewHolder<Region> {
        private final DialogSearchItemBinding binding;
        SortedItemViewHolder(@NonNull DialogSearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        protected void performBind(@NonNull Region item) {
            binding.dItemLabel.setText(item.getName());
        }
    }
}
