package net.inqer.autosearch.ui.dialog.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.inqer.autosearch.data.model.ListItem;
import net.inqer.autosearch.databinding.DialogSearchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DialogRVAdapter<T extends ListItem> extends RecyclerView.Adapter<SearchItemViewHolder> implements Filterable {
    private static final String TAG = "DialogRVAdapter";
    private final SearchItemViewHolder.SearchItemClickListener onItemClickListener;
    private List<T> fullList;
    private List<T> mainList;
    private Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d(TAG, "performFiltering: " + constraint);
            List<T> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                Log.d(TAG, "performFiltering: adding full: "+fullList.size());
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (T item : fullList) {
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

    DialogRVAdapter(List<T> fullList, SearchItemViewHolder.SearchItemClickListener listener) {
        this.mainList = fullList;
        this.fullList = new ArrayList<>(fullList); // IMPORTANT! if you'll just assign full list to
        //this variable, it would just link to the same list object. So when changing the main list,
        //the full list would change too automatically.
        this.onItemClickListener = listener;
    }

    void setFullList(List<T> list) {
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
        T currentRegion = mainList.get(position);
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


}
