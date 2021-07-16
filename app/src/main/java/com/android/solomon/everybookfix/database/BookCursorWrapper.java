package com.android.solomon.everybookfix.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.solomon.everybookfix.Book;

import java.util.Date;
import java.util.UUID;

import static com.android.solomon.everybookfix.database.BookDbSchema.BookTable.*;

public class BookCursorWrapper extends CursorWrapper {
    public BookCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Book getBook(){
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        long date = getLong(getColumnIndex(Cols.DATE));
        int isSolved = getInt(getColumnIndex(Cols.SOLVED));
        String suspect = getString(getColumnIndex(Cols.SUSPECT));

        Book book = new Book(UUID.fromString(uuidString));
        book.setTitle(title);
        book.setDate(new Date(date));
        book.setSolved(isSolved != 0);
        book.setSuspect(suspect);

        return book;
    }
}
