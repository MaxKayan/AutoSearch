package net.inqer.autosearch.ui.fragment.filters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import net.inqer.autosearch.data.model.QueryFilter;
import net.inqer.autosearch.databinding.FilterItemBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

public class FiltersAdapter extends ListAdapter<QueryFilter, FiltersAdapter.FilterViewHolder> {
    private static final String TAG = "FiltersAdapter";

    private static final DiffUtil.ItemCallback<QueryFilter> DIFF_CALLBACK = new DiffUtil.ItemCallback<QueryFilter>() {
        @Override
        public boolean areItemsTheSame(@NonNull QueryFilter oldItem, @NonNull QueryFilter newItem) {
//            Log.d(TAG, "areItemsTheSame: "+oldItem.getId().equals(newItem.getId()));
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull QueryFilter oldItem, @NonNull QueryFilter newItem) {
            boolean isEqual = oldItem.equals(newItem);
//            Log.d(TAG, "areContentsTheSame: "+isEqual);
            return isEqual;
        }
    };

    private final SimpleDateFormat dateFormat;

    @Inject
    public FiltersAdapter(SimpleDateFormat dateFormat) {
        super(DIFF_CALLBACK);
        this.dateFormat = dateFormat;
    }

    public QueryFilter getFilterAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilterItemBinding itemBinding = FilterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FilterViewHolder(itemBinding, dateFormat);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        QueryFilter currentItem = getItem(position);
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

        void bind(@NotNull QueryFilter filter) {
            carMark.setText(filter.getCarMark());
            carModel.setText(filter.getCarModel());
            city.setText(filter.getCities());
            createdAt.setText(dateFormat.format(filter.getCreated_at()));
            resultCount.setText(String.valueOf(filter.getQuantity()));
        }

    }
}
