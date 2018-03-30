package com.example.v_shevchyk.rx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.v_shevchyk.rx.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ArticleModel> articleModels  = new ArrayList<>();
    private Context context;

    public ArticleRv(ArrayList<ArticleModel> articleModels, Context context) {
        this.articleModels = articleModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PostsViewHolder viewHolder = (PostsViewHolder) holder;
        viewHolder.postTitle.setText(articleModels.get(position).getName());
        Glide.with(context)
                .load(articleModels.get(position).getImageUrl())
                .into(viewHolder.postPicture);
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_post)
        public ImageView postPicture;

        @BindView(R.id.tv_post_title)
        public TextView postTitle;

        public PostsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
