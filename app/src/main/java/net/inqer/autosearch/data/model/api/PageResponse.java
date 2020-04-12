package net.inqer.autosearch.data.model.api;

import androidx.annotation.Nullable;

import java.util.List;

public class PageResponse<T> {

    private Integer count;

    @Nullable
    private String next;
    @Nullable
    private String previous;

    private List<T> results;

    public PageResponse(Integer count, @Nullable String next, @Nullable String previous, List<T> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    @Nullable
    public String getNext() {
        return next;
    }

    @Nullable
    public String getPrevious() {
        return previous;
    }

    public List<T> getResults() {
        return results;
    }
}
