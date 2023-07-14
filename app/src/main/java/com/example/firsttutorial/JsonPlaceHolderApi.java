package com.example.firsttutorial;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET("posts")
//  The path will look like this  {https://jsonplaceholder.typicode.com/posts?userId=1&userId=2&_sort=id&_order=desc}

//   int can't set value to null but Integer has additional properties and methods that make it can assigned "null"
    Call<List<PostResponse>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    Call<List<PostResponse>> getPosts(
            @QueryMap PostRequest parameters
    );

    @GET("posts/{id}/comments")
//                                    Set parameter for get the argument in another class
    Call<List<Comment>> getComments(@Path("id") int id);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("posts")
//    return Call of type "Post"
    Call<PostResponse>createPost(@Body PostResponse postResponse);

//      For encode the url for security in html website
    @FormUrlEncoded
    @POST("posts")
    Call<PostResponse> createPost(
            @Field("userId")int userId,
            @Field("title")String title,
            @Field("body")String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<PostResponse> createPost(@FieldMap Map<String,String> fields);

//    Add header
    @Headers({"Static-Header: 213","Static-Header2: 888"})
//     PUT used to update resource
//    @PUT  Will replace the existing resource with the new representation
//    Example we didn't send value and it null the value in data base will be null no matter previous got the value or not
    @PUT("posts/{id}")
    Call<PostResponse> putPost(@Header("Dynamic-Header") String header,
                               @Path("id")int id, @Body PostResponse postResponse);
//      PATCH used to modify the resource
//    Example we send the value title="" or null and text="Update" the value of title value didn't change but the text value will changed into "Update"
    @PATCH("posts/{id}")
    Call<PostResponse> patchPost(@Path("id")int id, @Body PostResponse postResponse);

    @DELETE("posts/{id}")
//    Use void to ignore response body
    Call <Void> deletePost(@Path("id")int id);

}
