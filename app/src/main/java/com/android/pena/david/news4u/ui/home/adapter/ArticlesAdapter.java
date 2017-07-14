package com.android.pena.david.news4u.ui.home.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 13/07/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>{

    private RealmResults<Article> articles;
    private Context mContext;
    private int lastPosition;

    public ArticlesAdapter(Context context, RealmResults<Article> articles){
        //setHasStableIds(true);
        lastPosition = -1;
        this.mContext = context;
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_row,null);
        return new ArticlesAdapter.ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindArticle(articles.get(position));
        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.article_title) TextView articleTitle;
    @BindView(R.id.article_img) ImageView articleImg;
    @BindView(R.id.article_category) TextView articleCategory;
    @BindView(R.id.article_layout) ConstraintLayout layout;

    private ViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);
        layout.setOnClickListener(this);
    }

    private void bindArticle(Article article){
        articleTitle.setText(article.getTitle());
        articleCategory.setText(article.getSection());
        if(article.getMedia() != null) {
            Glide.with(mContext).load(article.getMedia().getUrl()).into(articleImg);
        }
    }

    @Override
    public void onClick(View v) {
        Timber.d("clicked");
    }
}
}