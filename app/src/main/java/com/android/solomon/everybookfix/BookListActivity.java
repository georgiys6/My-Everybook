package com.android.solomon.everybookfix;

import androidx.fragment.app.Fragment;

public class BookListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {

        return new BookListFragment();
    }
}
