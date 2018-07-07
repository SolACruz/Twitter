package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    //list the attributes
    public String name;
    public Long uid;
    public String username;
    public String profileImageURL;

    //deserialize JSON
    public static User fromJSON(JSONObject jsonObject) throws JSONException{

        User user = new User();

        //extract and fill values
        user.name= jsonObject.getString("name");
        user.uid= jsonObject.getLong("id");
        user.username= jsonObject.getString("screen_name");
        user.profileImageURL= jsonObject.getString("profile_image_url");

        return user;
    }
}
