package com.david.gotmuzei.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by davidhodge on 5/20/14.
 */
public class Photo {

    @SerializedName("photo")
    public String photoLink;

    @SerializedName("house")
    public String house;

}
