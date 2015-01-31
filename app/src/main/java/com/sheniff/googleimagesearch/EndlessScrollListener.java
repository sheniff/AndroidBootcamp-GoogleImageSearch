package com.sheniff.googleimagesearch;

import android.widget.AbsListView;

/**
 * Created by sheniff on 1/31/15.
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
    private int visibleThreshold = 3;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private int totalPages = Integer.MAX_VALUE; // infinite pages

    public EndlessScrollListener() {}

    public EndlessScrollListener(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if(loading && (totalItemCount > previousTotalItemCount)) {
            this.loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if(!loading && currentPage < totalPages && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore(currentPage, totalItemCount);
            loading = true;
        }
    }

    protected abstract void onLoadMore(int page, int totalItemCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
}
