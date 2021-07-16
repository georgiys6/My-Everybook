package com.android.solomon.everybookfix.database;

public class BookDbSchema {
    public static final class BookTable{
        public static final String NAME="books";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
