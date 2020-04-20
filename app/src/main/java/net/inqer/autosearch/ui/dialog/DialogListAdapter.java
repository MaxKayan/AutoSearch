package net.inqer.autosearch.ui.dialog;

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

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.LocationViewHolder> implements Filterable {
    private List<Region> fullList;
    private List<Region> filteredList;

    private final OnItemClickListener onItemClickListener;

    public DialogListAdapter(List<Region> fullList, OnItemClickListener listener) {
        this.fullList = fullList;
        this.filteredList = new ArrayList<>(fullList);
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DialogSearchItemBinding itemBinding = DialogSearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LocationViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Region currentRegion = fullList.get(position);
        holder.bind(currentRegion, onItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return fullList.size();
    }

    @Override
    public Filter getFilter() {
        return locationFilter;
    }

    private Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };


    static class LocationViewHolder extends RecyclerView.ViewHolder {
//        TextView label;
        DialogSearchItemBinding binding;

        public LocationViewHolder(@NonNull DialogSearchItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
//            label = itemBinding.dItemLabel;
        }

        private void bind(@NotNull Region region, final OnItemClickListener listener, final int pos) {
            binding.dItemLabel.setText(region.getName());
            binding.dItem.setOnClickListener(v -> listener.onClick(pos));
        }
    }


    public interface OnItemClickListener {
        void onClick(int position);
    }
}
