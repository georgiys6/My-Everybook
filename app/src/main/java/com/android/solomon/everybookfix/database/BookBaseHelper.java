package com.android.solomon.everybookfix.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.android.solomon.everybookfix.database.BookDbSchema.*;

public class BookBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "bookBase.db";

    public BookBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BookTable.NAME + "(" +
                " _id integer primary key autoincrement, " + BookTable.Cols.UUID + ", " + BookTable.Cols.TITLE + ", " +
                BookTable.Cols.DATE + ", " +
                BookTable.Cols.SOLVED + ", " +
                BookTable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
