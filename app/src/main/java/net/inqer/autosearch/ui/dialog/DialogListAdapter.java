package net.inqer.autosearch.ui.dialog;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import net.inqer.autosearch.data.model.ListItem;
import net.inqer.autosearch.databinding.DialogSearchItemBinding;

public class DialogListAdapter extends ListAdapter<ListItem, SearchItemViewHolder> {
    private static final String TAG = "DialogListAdapter";

    private static final DiffUtil.ItemCallback<ListItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<ListItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
            return oldItem.isContentTheSameAs(newItem);
        }
    };

    private DialogSearchItemBinding binding;
    private final SearchItemViewHolder.SearchItemClickListener itemClickListener;

    protected DialogListAdapter(@NonNull DiffUtil.ItemCallback<ListItem> diffCallback,
                                SearchItemViewHolder.SearchItemClickListener listener) {
        super(diffCallback);
        this.itemClickListener = listener;
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
}
