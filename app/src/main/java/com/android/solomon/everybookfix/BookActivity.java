package com.android.solomon.everybookfix;

import android.content.Context;
import android.content.Intent;

import java.util.UUID;

import androidx.fragment.app.Fragment;


public class BookActivity extends SingleFragmentActivity {

    private static final String EXTRA_BOOK_ID="com.android.solomon.everybook.book_id";

    public static final Intent newIntent(Context packageContext, UUID bookId){
        Intent intent=new Intent(packageContext,BookActivity.class);
        intent.putExtra(EXTRA_BOOK_ID,bookId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID bookId=(UUID) getIntent().getSerializableExtra(EXTRA_BOOK_ID);
        return BookFragment.newInstance(bookId);
    }
}