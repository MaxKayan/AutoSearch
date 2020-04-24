package net.inqer.autosearch.ui.dialog.listsearch.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.inqer.autosearch.data.model.ListItem;
import net.inqer.autosearch.databinding.DialogSearchItemBinding;

import org.jetbrains.annotations.NotNull;

class SearchItemViewHolder extends RecyclerView.ViewHolder {
    private DialogSearchItemBinding binding;

    SearchItemViewHolder(@NonNull DialogSearchItemBinding itemBinding) {
        super(itemBinding.getRoot());
        this.binding = itemBinding;
    }

    void bind(@NotNull ListItem item, final SearchItemClickListener listener, final int pos) {
        binding.dItemLabel.setText(item.getName());
        binding.dItem.setOnClickListener(v -> listener.onClick(pos));
    }

    public interface SearchItemClickListener {
        void onClick(int position);
    }
}
