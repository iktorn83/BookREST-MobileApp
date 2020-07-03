package com.example.restapi.remote;

public class APIUtils {
    private APIUtils(){
    }

    private static final String API_URL = "http://10.0.2.2:8080/";

    public static BookService getBookService(){
        return RetrofitClient.getClient(API_URL).create(BookService.class);
    }

}
