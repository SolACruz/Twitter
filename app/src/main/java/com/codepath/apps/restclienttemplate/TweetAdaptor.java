package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdaptor extends RecyclerView.Adapter<TweetAdaptor.ViewHolder>{



    private List<Tweet> mTweet;
    Context context;
    //pass in the tweets array into the constructor
    public TweetAdaptor(List<Tweet> tweets){
        mTweet=tweets;

    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
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
        holder.tvRelDate.setText(getRelativeTimeAgo(tweet.createdAt));

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
        public TextView tvRelDate;


        public ViewHolder (View itemView){
            super(itemView);

            //Find view by id
            ivProfileImage=itemView.findViewById(R.id.ivProfileImage);
            tvUsername=itemView.findViewById(R.id.tvUsername);
            tvBody=itemView.findViewById(R.id.tvBody);
            tvRelDate=itemView.findViewById(R.id.tvRelDate);
        }

    }

    public void clear(){
        mTweet.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweets){
        mTweet.addAll(tweets);
        notifyDataSetChanged();
    }
}
