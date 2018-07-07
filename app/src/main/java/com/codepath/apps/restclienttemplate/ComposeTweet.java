package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeTweet extends AppCompatActivity {

    //
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        updateStatus();
    }

    public void onClick() {
        Intent i = new Intent(this, TimelineActivity.class);
        i.putExtra("tweet", String.valueOf(R.id.etComposeBody)); // pass arbitrary data to launched activity
        finish();
    }

    private final int REQUEST_CODE = 20;
    private final int RESULT_OK_CODE=20;

    private void updateStatus(){
        //onClickListener
        Button tweetButton=findViewById(R.id.bvTweet);
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText message = findViewById(R.id.etComposeBody);
                client=TwitterApp.getRestClient(ComposeTweet.this);
                String compMessage=message.getText().toString();
                client.sendTweet(compMessage, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Tweet tweet=Tweet.fromJSON(response);
                            Intent intent = new Intent();
                            intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                            setResult(RESULT_OK_CODE, intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        });
    }
}
