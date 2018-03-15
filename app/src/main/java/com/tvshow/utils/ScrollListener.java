package com.tvshow.utils;

/**
 * Created by ismael on 15/03/18.
 */


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class ScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private int visibleThreshold;
    private int lastVisibleItem, totalItemCount;
    private static final int VISIBLE = 5;
    private RecyclerView.LayoutManager genericLayoutManager;

    public ScrollListener(LinearLayoutManager linearLayoutManager){
        //this.linearLayoutManager = linearLayoutManager;
        genericLayoutManager = linearLayoutManager;
        visibleThreshold = VISIBLE;
    }

    public ScrollListener(GridLayoutManager linearLayoutManager) {
        genericLayoutManager = linearLayoutManager;
        visibleThreshold = linearLayoutManager.getSpanCount() * VISIBLE;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        //totalItemCount = linearLayoutManager.getItemCount();
        totalItemCount = genericLayoutManager.getItemCount();

        if(genericLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItem = ((LinearLayoutManager) genericLayoutManager).findLastVisibleItemPosition();
        } else if(genericLayoutManager instanceof  GridLayoutManager){
            lastVisibleItem = ((GridLayoutManager) genericLayoutManager).findLastVisibleItemPosition();
        }


        if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            isLoading = true;
            loadMore();
        }

    }

    protected abstract void loadMore();

    public void loadFinished() {
        isLoading = false;
    }
}
