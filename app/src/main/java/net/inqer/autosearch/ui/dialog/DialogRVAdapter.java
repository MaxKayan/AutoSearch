package net.inqer.autosearch.ui.dialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.inqer.autosearch.data.model.Region;
import net.inqer.autosearch.databinding.DialogSearchItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DialogRVAdapter extends RecyclerView.Adapter<DialogRVAdapter.SearchItemViewHolder> implements Filterable {
    private static final String TAG = "DialogRVAdapter";
    private final SearchItemViewHolder.SearchItemClickListener onItemClickListener;
    private List<Region> fullList;
    private List<Region> mainList;
    private Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d(TAG, "performFiltering: " + constraint);
            List<Region> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                Log.d(TAG, "performFiltering: adding full: "+fullList.size());
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
            mainList.clear();
            mainList.addAll(((List) results.values));
            Log.d(TAG, "publishResults: " + mainList.size());
            notifyDataSetChanged();
        }
    };

    DialogRVAdapter(List<Region> fullList, SearchItemViewHolder.SearchItemClickListener listener) {
        this.mainList = fullList;
        this.fullList = new ArrayList<>(fullList); // IMPORTANT! if you'll just assign full list to
        //this variable, it would just link to the same list object. So when changing the main list,
        //the full list would change too automatically.
        this.onItemClickListener = listener;
    }

    public void setFullList(List<Region> list) {
        fullList = list;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DialogSearchItemBinding itemBinding = DialogSearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        Region currentRegion = mainList.get(position);
        holder.bind(currentRegion, onItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    @Override
    public Filter getFilter() {
        return locationFilter;
    }

    static class SearchItemViewHolder extends RecyclerView.ViewHolder {
        private DialogSearchItemBinding binding;

        SearchItemViewHolder(@NonNull DialogSearchItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

        void bind(@NotNull Region region, final SearchItemClickListener listener, final int pos) {
            binding.dItemLabel.setText(region.getName());
            binding.dItem.setOnClickListener(v -> listener.onClick(pos));
        }

        public interface SearchItemClickListener {
            void onClick(int position);
        }
    }


}
