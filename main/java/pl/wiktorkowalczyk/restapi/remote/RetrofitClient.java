package com.example.restapi.remote;

import java.io.IOException;
import java.time.Instant;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;


    public static Retrofit getClient(String url) {
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Time", String.valueOf(Instant.now().toEpochMilli()))
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.HEADERS);


            httpClient.addInterceptor(interceptor).build();
            OkHttpClient clientHeader = httpClient.build();
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())

                    .client(clientHeader)

                    .build();
        }

        return retrofit;
    }

}
