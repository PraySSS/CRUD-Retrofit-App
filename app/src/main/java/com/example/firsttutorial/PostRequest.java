package com.example.firsttutorial;

import com.google.gson.annotations.SerializedName;

public class PostRequest {
    @SerializedName("userId")
    int userId;
    @SerializedName("_sort")
    String sort;
    @SerializedName("_order")
    String order;

    public PostRequest(String order) {
        this.order = order;
    }
    public PostRequest(int userId, String sort, String order) {
        this.userId = userId;
        this.sort = sort;
        this.order = order;
    }



    public int getUserId() {
        return userId;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }
}
