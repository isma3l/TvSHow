package com.tvshow.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tvshow.R;
import com.tvshow.model.TVShowInfo;
import java.util.List;

/**
 * Created by ismael on 14/03/18.
 */

public class TvShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<TVShowInfo> tvShowInfos;
    private TvShowListener tvShowListener;

    public TvShowAdapter(Context context, List<TVShowInfo> tvShowInfos, TvShowListener tvShowListener) {
        this.context = context;
        this.tvShowInfos = tvShowInfos;
        this.tvShowListener = tvShowListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show_info, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TVShowInfo tvShowInfo = tvShowInfos.get(position);
        if(tvShowInfo != null) {
            TvShowViewHolder tvShowViewHolder = (TvShowViewHolder) holder;
            tvShowViewHolder.name.setText(tvShowInfo.getName());
            tvShowViewHolder.overView.setText(tvShowInfo.getOverview());
            tvShowViewHolder.popularity.setText(tvShowInfo.getPopularity());
            loadImage(tvShowViewHolder.image, position);
        }
    }

    private void loadImage(ImageView imageView, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_default);

        String urlImage = tvShowInfos.get(position).getPoster_path();
        Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(urlImage)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return tvShowInfos.size();
    }


    public class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name, overView, popularity;

        public TvShowViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageShowViewInfo);
            name = itemView.findViewById(R.id.name_show_info);
            overView = itemView.findViewById(R.id.over_show_info);
            popularity = itemView.findViewById(R.id.popularity_show_info);

            itemView.setOnClickListener(this);
            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvShowListener.onClickListener(view, getAdapterPosition());
                }
            });
            */
        }

        @Override
        public void onClick(View view) {
             tvShowListener.onClickListener(view, getAdapterPosition());
        }
    }

    public interface TvShowListener {
        void onClickListener(View v, int position);
    }
}
