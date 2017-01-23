package com.example.administrator.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//  创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())//GsonConverterFactory.create()表示调用Gson库来解析json返回值
                .build();
        UserService user = retrofit.create(UserService.class);

//        调用请求方法，并得到Call实例。Call其实在Retrofit中就是行使网络请求并处理返回值的类，调用的时候会把需要拼接的参数传递进去
        Call<MovieItem> call = user.getUserInfo("26366465");
//        使用Call实例完成异步请求
        call.enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                Log.d("namenamename", response.body().getMobile_url());
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                Log.d("ThrowableThrowable", t.toString());
            }
        });
    }

    //  创建业务请求接口
    public interface UserService {
        @GET("movie/subject/{id}")
        Call<MovieItem> getUserInfo(@Path("id") String id);
    }
}
