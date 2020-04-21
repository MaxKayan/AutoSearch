package net.inqer.autosearch.ui.dialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import net.inqer.autosearch.data.model.ListItem;

public class ListItemDiffUtil<T extends ListItem> extends DiffUtil.ItemCallback<T> {
    @Override
    public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return oldItem.isSameModelAs(newItem);
    }

    @Override
    public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
        return oldItem.isContentTheSameAs(newItem);
    }
}
