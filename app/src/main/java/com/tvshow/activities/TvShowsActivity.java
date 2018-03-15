package com.tvshow.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.tvshow.R;
import com.tvshow.adapters.TvShowAdapter;
import com.tvshow.model.TVShowInfo;
import com.tvshow.network.response.ResponseTvShows;
import com.tvshow.network.service.ApiService;
import com.tvshow.network.service.ApiUtils;
import com.tvshow.CustomComponents.ScrollListener;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsActivity extends AppCompatActivity implements TvShowAdapter.TvShowListener {
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private TvShowAdapter tvShowAdapter;
    private List<TVShowInfo> tvShowInfoList;
    private int current_page;
    private boolean hasMore;
    private ScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeComponents();
        loadFirstPage();
    }

    private void buildProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    private void initializeComponents() {
        buildProgressDialog();

        current_page = 1;
        hasMore = true;
        tvShowInfoList = new ArrayList<>();
        tvShowAdapter = new TvShowAdapter(getBaseContext(), tvShowInfoList, this);

        recyclerView = findViewById(R.id.listMovies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

        scrollListener = new ScrollListener(linearLayoutManager) {
            @Override
            public void loadMore() {
                if(hasMore) {
                    addFooter();
                    loadTvShow();
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(tvShowAdapter);
    }

    private void loadFirstPage() {
        progressDialog.show();
        loadTvShow();
    }

    private void addFooter() {
        tvShowInfoList.add(null);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                tvShowAdapter.notifyItemInserted(tvShowInfoList.size() - 1);
            }
        });
    }


    private void loadTvShow() {
        Call<ResponseTvShows> call = ApiUtils.getApiService().getTvShows(ApiService.API_KEY, Integer.toString(current_page));

        call.enqueue(new Callback<ResponseTvShows>() {
            @Override
            public void onResponse(Call<ResponseTvShows> call, Response<ResponseTvShows> response) {
                progressDialog.dismiss();

                if(response.isSuccessful()) {
                    List<TVShowInfo> list = response.body().getResults();
                    processResults(list);
                } else {
                    processError();
                }
            }

            @Override
            public void onFailure(Call<ResponseTvShows> call, Throwable t) {
                progressDialog.dismiss();
                processError();
            }
        });

    }

    private void processResults(List<TVShowInfo> list) {
        if(isFirstPage()) {
            processFirstPage(list);
        } else {
            processPage(list);
        }
        updateValues(list.size());
        scrollListener.loadFinished();
    }

    private void processError() {
        if(hasFooter()) {
            removeFooter();
        }
    }

    private boolean hasFooter() {
        return !tvShowInfoList.isEmpty() &&  tvShowInfoList.get(tvShowInfoList.size() - 1) == null;
    }

    private void updateValues(int size) {
        if(size == 0) {
            hasMore = false;
        } else {
            current_page++;
        }
    }

    private void processPage(List<TVShowInfo> list) {
        removeFooter();

        if(!list.isEmpty()) {
            tvShowInfoList.addAll(list);
            tvShowAdapter.notifyItemRangeInserted(list.size(), list.size() - 1);
        }
    }

    private void removeFooter() {
        tvShowInfoList.remove(tvShowInfoList.size() - 1);
        tvShowAdapter.notifyItemRemoved(tvShowInfoList.size());
    }

    private void processFirstPage(List<TVShowInfo> list) {
        if(!list.isEmpty()) {
            tvShowInfoList.addAll(list);
            tvShowAdapter.notifyDataSetChanged();
        }
    }

    private boolean isFirstPage() {
        return current_page == 1;
    }

    @Override
    public void onClickListener(View v, int position) {
        TVShowInfo tvShowInfo = tvShowInfoList.get(position);
        Gson gson = new Gson();
        String serialized = gson.toJson(tvShowInfo);
        Log.i("SHOW", Integer.toString(position));
        Intent intent = new Intent(this, DetailTvSHowActivity.class);
        intent.putExtra(DetailTvSHowActivity.SERIALIZED, serialized);
        startActivity(intent);
    }
}
