package com.example.restapi;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.example.restapi.model.Book;


import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private Context context;
    private List<Book> books;
    private TextView txtBookName, txtBookAuthor, txtBookYear;

    public BookAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.books = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_book, parent, false);
        findViewsByIds(rowView);
        setTextViews(pos);
        setListeners(rowView, pos);
        return rowView;
    }

    private void findViewsByIds(View rowView) {

        txtBookName = (TextView) rowView.findViewById(R.id.txtBookname);
        txtBookAuthor = (TextView) rowView.findViewById(R.id.txtBookauthor);
        txtBookYear = (TextView) rowView.findViewById(R.id.txtBookyear);
    }

    private void setTextViews(int pos) {

        txtBookName.setText(String.format("Title: %s", books.get(pos).getName()));
        txtBookAuthor.setText(String.format("Author: %s", books.get(pos).getAuthor()));
        txtBookYear.setText(String.format("Year: %s", books.get(pos).getYear()));
    }

    private void setListeners(View rowView, final int pos) {
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("book_id", String.valueOf(books.get(pos).getId()));
                intent.putExtra("book_name", books.get(pos).getName());
                intent.putExtra("book_author", books.get(pos).getAuthor());
                intent.putExtra("book_year", books.get(pos).getYear());
                context.startActivity(intent);
            }
        });
    }
}