package com.example.firsttutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.text_view_result);
//      To set the value that we serialize to null when use patch
//       Used to include null values during the serialization process.
        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                                  This level will show their respective headers and bodies (if present)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
//                Use JSON to pass the response here
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
//                 To rewrite the retrofit interface use this way
//        Instance for interface
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        getPosts();
//        getComments();
//        createPost();
        updatePost();
//        deletePost();
    }

    private void getPosts() {
//        From interface @QueryMap

        PostRequest parameters=new PostRequest(1,"id","desc");


        //        Execute network request and response back
        Call<List<PostResponse>> call = jsonPlaceHolderApi.getPosts(parameters);
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(4,null,null);//If you don't want to sorting use null
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(new Integer[]{1,2},"id","desc");


//        retrofit auto create and manage thread with enqueue
        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code:" + response.code());
                    return;
                }
                List<PostResponse> postResponses = response.body();
                for (PostResponse postResponse : postResponses) {
                    String content = "";
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "User ID: " + postResponse.getUserId() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getText() + "\n\n";

                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code:" + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });

    }

    public void createPost() {
//        This is the body of post request
        PostResponse post = new PostResponse(23, "New title", "new text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "24");
        fields.put("title", "Title1");
//        fields.put("body","Text1");//If we didn't put this field it will be null

        Call<PostResponse> call = jsonPlaceHolderApi.createPost(fields);
//        Execute asynchronously
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code:" + response.code());
                    return;
                }
                PostResponse postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textResult.setText(content);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        PostResponse post = new PostResponse(29, "New title", null);

//        Call<Post> call= jsonPlaceHolderApi.putPost(5,post);

        Call<PostResponse> call = jsonPlaceHolderApi.putPost("Main Header",5, post);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code:" + response.code());
                    return;
                }
                PostResponse postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textResult.setText(content);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }
}