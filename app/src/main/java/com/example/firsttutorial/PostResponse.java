package com.example.firsttutorial;

import com.google.gson.annotations.SerializedName;

public class PostResponse {
    private int userId;
//   Change int to Integer cause we will make the backend ignore id
    private Integer id;
    private String title;
    //  For Gson annotation
    @SerializedName("body")
    private String text;
//      In the backend should auto generate the id
    public PostResponse(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
