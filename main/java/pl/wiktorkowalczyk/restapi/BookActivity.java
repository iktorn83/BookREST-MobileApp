package com.example.restapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restapi.model.Book;
import com.example.restapi.remote.APIUtils;
import com.example.restapi.remote.BookService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    BookService bookService;
    EditText edtUId;
    EditText edtbookName;
    Button btnSave;
    Button btnDel;
    TextView txtUId;
    EditText edtbookAuthor;
    EditText edtbookYear;

    private String bookId;
    private String bookName;
    private String bookAuthor;
    private int bookYear;
    private List booklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setTitle("Books");
        findViewsByIds();
        setListeners();
        initBookServide();
        getBundleExtras();
        setTextViews();
        setFieldsVisibility();
    }

    private void findViewsByIds() {
        txtUId = (TextView) findViewById(R.id.txtId);
        edtUId = (EditText) findViewById(R.id.edtUId);
        edtbookName = (EditText) findViewById(R.id.edtBookname);
        edtbookAuthor = (EditText) findViewById(R.id.edtBookauthor);
        edtbookYear = (EditText) findViewById(R.id.edtBookyear);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);
    }

    private void getBundleExtras() {
        Bundle extras = getIntent().getExtras();
        bookId = extras.getString("book_id");
        bookName = extras.getString("book_name");
        bookAuthor = extras.getString("book_author");
        bookYear = extras.getInt("book_year");
    }

    private void setFieldsVisibility() {
        if (bookId != null && bookId.trim().length() > 0) {
            edtUId.setFocusable(false);
        } else {
            txtUId.setVisibility(View.GONE);
            edtUId.setVisibility(View.GONE);
            btnDel.setVisibility(View.GONE);
        }
    }

    private void setTextViews() {
        edtUId.setText(bookId);
        edtbookName.setText(bookName);
        edtbookAuthor.setText(bookAuthor);
        edtbookYear.setText(String.valueOf(bookYear));
    }

    private void initBookServide() {
        bookService = APIUtils.getBookService();
    }

    private void setListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book u = new Book();
                u.setName(edtbookName.getText().toString());
                u.setAuthor(edtbookAuthor.getText().toString());
                u.setYear(Integer.parseInt(edtbookYear.getText().toString()));

                if (bookId != null && bookId.trim().length() > 0) {

                    updateBook(Integer.parseInt(bookId), u);
                } else {

                    addBook(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook(Integer.parseInt(bookId));

                Intent intent = new Intent(BookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goToMainScreen() {
        startActivity(new Intent(BookActivity.this, MainActivity.class));
    }

    public void addBook(Book u) {
        Call<Book> call = bookService.addBook(u);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
               if (response.isSuccessful()) {
                    Toast.makeText(BookActivity.this, "Book created!", Toast.LENGTH_SHORT).show();
                    goToMainScreen();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateBook(int id, Book u) {
        Call<Book> call = bookService.updateBook(id, u);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookActivity.this, "Book updated!", Toast.LENGTH_SHORT).show();
                    goToMainScreen();
                }

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteBook(int id) {
        Call<Boolean> call = bookService.deleteBook(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(BookActivity.this, "Book deleted !", Toast.LENGTH_SHORT).show();
                    goToMainScreen();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       goToMainScreen();
    }
}