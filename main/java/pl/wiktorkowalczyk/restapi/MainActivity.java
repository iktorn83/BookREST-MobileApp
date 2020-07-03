package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.restapi.model.Book;
import com.example.restapi.remote.APIUtils;
import com.example.restapi.remote.BookService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnAddBook;
    ListView listView;
    BookService bookService;
    List<Book> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BookREST Application");
        findViewsByIds();
        initBookService();
        setListeners();
        getBooksList();
    }

    private void setListeners() {
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                intent.putExtra("book_name", "");
                startActivity(intent);
            }
        });
    }

    private void initBookService() {
        bookService = APIUtils.getBookService();

    }

    private void findViewsByIds() {
        btnAddBook = (Button) findViewById(R.id.btnAddBook);
        listView = (ListView) findViewById(R.id.listView);
    }

    public void getBooksList() {
        Call<List<Book>> call = bookService.getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new BookAdapter(MainActivity.this, R.layout.list_book, list));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getBooksList();
    }
}
