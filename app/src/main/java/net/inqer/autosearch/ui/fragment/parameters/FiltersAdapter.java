package net.inqer.autosearch.ui.fragment.parameters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import net.inqer.autosearch.data.model.Filter;
import net.inqer.autosearch.databinding.FilterItemBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

public class FiltersAdapter extends ListAdapter<Filter, FiltersAdapter.FilterViewHolder> {
    private static final String TAG = "FiltersAdapter";

    private static final DiffUtil.ItemCallback<Filter> DIFF_CALLBACK = new DiffUtil.ItemCallback<Filter>() {
        @Override
        public boolean areItemsTheSame(@NonNull Filter oldItem, @NonNull Filter newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Filter oldItem, @NonNull Filter newItem) {
//            return oldItem.equals(newItem);
            return oldItem.getId().equals(newItem.getId());
        }
    };

    private final SimpleDateFormat dateFormat;

    @Inject
    public FiltersAdapter(SimpleDateFormat dateFormat) {
        super(DIFF_CALLBACK);
        this.dateFormat = dateFormat;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilterItemBinding itemBinding = FilterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FilterViewHolder(itemBinding, dateFormat);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        Filter currentItem = getItem(position);
        holder.bind(currentItem);
    }

    static class FilterViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDateFormat dateFormat;
        private TextView carMark, carModel, city, createdAt, resultCount;

        FilterViewHolder(@NonNull FilterItemBinding itemBinding, SimpleDateFormat dateFormat) {
            super(itemBinding.getRoot());
            this.dateFormat = dateFormat;
            carMark = itemBinding.fItemCarMark;
            carModel = itemBinding.fItemCarModel;
            city = itemBinding.fItemCity;
            createdAt = itemBinding.fItemCreatedAt;
            resultCount = itemBinding.fItemResultCountValue;
        }

        void bind(@NotNull Filter filter) {
            carMark.setText(filter.getCarMark());
            carModel.setText(filter.getCarModel());
            city.setText(filter.getCities());
            createdAt.setText(dateFormat.format(filter.getCreated_at()));
            resultCount.setText(String.valueOf(filter.getQuantity()));
        }

    }
}
