package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.github.scribejava.apis.TwitterApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    //creates a reference to the client
    private TwitterClient client;
    TweetAdaptor tweetAdaptor;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.Compose:
                composeMessage();
                return true;
//            case R.id.miProfile:
//                showProfileView();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK_CODE && requestCode==REQUEST_CODE){
            Tweet tweet= Parcels.unwrap(data.getParcelableExtra(Tweet.class.getSimpleName()));
            tweets.add(0, tweet);
            tweetAdaptor.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
            Toast.makeText(this, "Tweet Posted!", Toast.LENGTH_LONG);
        }

        // Use data parameter
        //Tweet tweet = (Tweet) data.getSerializableExtra("tweet");
    }

    private final int REQUEST_CODE = 20;
    private final int RESULT_OK_CODE=20;
    // FirstActivity, launching an activity for a result
    public void composeMessage() {
        Intent i = new Intent(this, ComposeTweet.class);
        i.putExtra("mode", 2); // pass arbitrary data to launched activity
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);

        //find the recyclerView
        rvTweets= findViewById(R.id.rvTweet);
        //provide an instance of the array
        tweets= new ArrayList<>();
        //construct the adapter from the data source
        tweetAdaptor= new TweetAdaptor(tweets);

        //setup the recyclerView (layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        //set adapter
        rvTweets.setAdapter(tweetAdaptor);

        populateTimeline();
    }

//    private void fetchTimelineAsync(int page){
//
//        client.getHomeTimeline(new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                tweetAdaptor.clear();
//                //iterate through the JSON array
//                //for each entry, deserialize the JSON object
//                for(int i=0; i<response.length(); i++) {
//                    //convert each object into the tweet model
//
//                    //add the tweet model to the data source
//
//                    //notify adapter that an item was added
//                    Tweet tweet= null;
//                    try {
//                        tweet = Tweet.fromJSON(response.getJSONObject(i));
//                        tweets.add(tweet);
//                        tweetAdaptor.notifyItemInserted(tweets.size()-1);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                tweetAdaptor.addAll(tweets);
//
//            }
//
//        }
//    }


    private void populateTimeline(){
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClient", response.toString());
                //iterate through the JSON array
                //for each entry, deserialize the JSON object
                for(int i=0; i<response.length(); i++) {
                    //convert each object into the tweet model

                    //add the tweet model to the data source

                    //notify adapter that an item was added
                    Tweet tweet= null;
                    try {
                        tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdaptor.notifyItemInserted(tweets.size()-1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
