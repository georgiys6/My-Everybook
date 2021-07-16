package com.android.solomon.everybookfix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.solomon.everybookfix.database.BookBaseHelper;
import com.android.solomon.everybookfix.database.BookCursorWrapper;
import com.android.solomon.everybookfix.database.BookDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.android.solomon.everybookfix.database.BookDbSchema.*;

public class BookLab {
    private static BookLab sBookLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static BookLab get(Context context) {
        if (sBookLab == null) {
            sBookLab = new BookLab(context);
        }

        return sBookLab;
    }

    private BookLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BookBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addBook(Book c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(BookTable.NAME, null, values);
    }

    public void removeBook(Book book){
        mDatabase.delete(BookTable.NAME, BookTable.Cols.UUID + " = ?",
                new String[]{book.getId().toString()});
    }

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        BookCursorWrapper cursor = queryBooks(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                books.add(cursor.getBook());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return books;
    }

    public Book getBook(UUID id) {
        BookCursorWrapper cursor = queryBooks(
                BookTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBook();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Book book){
        File fileDir=mContext.getFilesDir();
        return new File(fileDir, book.getPhotoFilename());
    }

    public void updateBook(Book book) {
        String uuidString = book.getId().toString();
        ContentValues values = getContentValues(book);
        mDatabase.update(BookTable.NAME, values,
                BookTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private BookCursorWrapper queryBooks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BookTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new BookCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Book book) {
        ContentValues values = new ContentValues();
        values.put(BookTable.Cols.UUID, book.getId().toString());
        values.put(BookTable.Cols.TITLE, book.getTitle());
        values.put(BookTable.Cols.DATE, book.getDate().getTime());
        values.put(BookTable.Cols.SOLVED, book.isSolved() ? 1 : 0);
        values.put(BookTable.Cols.SUSPECT, book.getSuspect());
        return values;
    }


}