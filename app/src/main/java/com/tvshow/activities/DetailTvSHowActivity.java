package com.tvshow.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tvshow.R;
import com.tvshow.adapters.CastAdapter;
import com.tvshow.model.Character;
import com.tvshow.model.TVShow;
import com.tvshow.model.TVShowInfo;
import com.tvshow.network.response.ResponseCast;
import com.tvshow.network.service.ApiService;
import com.tvshow.network.service.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailTvSHowActivity extends AppCompatActivity {
    public static final String SERIALIZED = "SERIALIZED/SHOW";

    private TVShow tvShow;
    private ImageView mainPhoto;
    private TextView title;
    private TextView description;
    private TextView popularity;
    private CastAdapter castAdapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializeComponents();
        buildTvShow();
        showBasicData();
        getCast();
    }

    private void initializeComponents() {
        buildProgressDialog();
        mainPhoto = findViewById(R.id.image_detail_main);
        title = findViewById(R.id.title_tvshow);
        description = findViewById(R.id.description_tv_show);
        popularity = findViewById(R.id.detail_popularity);

        castAdapter = new CastAdapter(getBaseContext());

        RecyclerView recyclerView = findViewById(R.id.recyclerView_cast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(castAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void buildProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    private void showBasicData() {
        TVShowInfo tvShowInfo = tvShow.getTvShowInfo();

        title.setText(tvShowInfo.getName());
        description.setText(tvShowInfo.getOverview());
        popularity.setText(tvShowInfo.getPopularity());

        String urlImage = tvShowInfo.getPoster_path();
        loadImage(mainPhoto, urlImage);
    }

    private void loadImage(ImageView imageView, String urlImage) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_default);

        Glide.with(getBaseContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(urlImage)
                .into(imageView);
    }

    private void getCast() {
        progressDialog.show();

        if(tvShow.getTvShowInfo() == null) return;
        String tvId = tvShow.getTvShowInfo().getId();
        Call<ResponseCast> callCast = ApiUtils.getApiService().getCast(tvId, ApiService.API_KEY);

        callCast.enqueue(new Callback<ResponseCast>() {
            @Override
            public void onResponse(Call<ResponseCast> call, Response<ResponseCast> response) {
                progressDialog.dismiss();

                if(response.isSuccessful()) {
                    List<Character> list = response.body().getCast();
                    processCast(list);
                }
            }

            @Override
            public void onFailure(Call<ResponseCast> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void processCast(List<Character> list) {
        castAdapter.addData(list);
    }

    private void buildTvShow() {
        tvShow = new TVShow();

        String serialized = getIntent().getStringExtra(SERIALIZED);
        if(serialized != null) {
            TVShowInfo tvShowInfo = hydrateTvShowInfo(serialized);
            tvShow.setTvShowInfo(tvShowInfo);
        }
    }

    private TVShowInfo hydrateTvShowInfo(String serialized) {
        Gson gson = new Gson();
        return gson.fromJson(serialized, TVShowInfo.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
