package com.example.restapi.remote;

import com.example.restapi.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookService {

    @GET("books/")
    Call<List<Book>> getBooks();

    @POST("books/")
    Call<Book> addBook(@Body Book book);

    @PUT("books/{id}")
    Call<Book> updateBook(@Path("id") int id, @Body Book book);

    @DELETE("books/{id}")
    Call<Boolean> deleteBook(@Path("id") int id);


}
