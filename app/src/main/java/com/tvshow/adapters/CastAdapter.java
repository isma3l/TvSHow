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
import com.tvshow.model.Character;

import java.util.ArrayList;
import java.util.List;


public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Character> characterList;

    public CastAdapter(Context context) {
        this.context = context;
        this.characterList = new ArrayList<>();
    }

    public void addData(List<Character> list) {
        characterList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Character character = characterList.get(position);
        if(character != null) {
            CharacterViewHolder characterViewHolder = (CharacterViewHolder) holder;
            characterViewHolder.realName.setText(character.getName());
            characterViewHolder.characterName.setText(character.getCharacter());
            loadImage(characterViewHolder.image, position);
        }
    }

    private void loadImage(ImageView imageView, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.image_default);

        String urlImage = characterList.get(position).getProfile_path();
        Glide.with(context)
                .applyDefaultRequestOptions(requestOptions)
                .load(urlImage)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }


    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView realName, characterName;

        public CharacterViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.characterImage);
            characterName = itemView.findViewById(R.id.character_name);
            realName = itemView.findViewById(R.id.realName);
        }
    }

}
