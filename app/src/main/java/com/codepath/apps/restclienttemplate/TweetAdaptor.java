package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetAdaptor extends RecyclerView.Adapter<TweetAdaptor.ViewHolder>{



    private List<Tweet> mTweet;
    Context context;
    //pass in the tweets array into the constructor
    public TweetAdaptor(List<Tweet> tweets){
        mTweet=tweets;

    }

    //for each row, inflate the layout and cache references in the ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View tweetView= inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder= new ViewHolder(tweetView);
        return viewHolder;
    }


    //bind the values based on the position of the element in the row view

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //get the data according to its position
        Tweet tweet=mTweet.get(position);

        //give the views the data in the correct order/populate the views
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        Glide.with(context).load(tweet.user.profileImageURL).into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweet.size();
    }

    //create the viewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;


        public ViewHolder (View itemView){
            super(itemView);

            //Find view by id
            ivProfileImage=itemView.findViewById(R.id.ivProfileImage);
            tvUsername=itemView.findViewById(R.id.tvUsername);
            tvBody=itemView.findViewById(R.id.tvBody);
        }
    }
}
