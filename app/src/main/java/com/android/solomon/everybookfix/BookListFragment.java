package com.android.solomon.everybookfix;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE="subtitle";

    private RecyclerView mBookRecyclerView;
    private BookAdapter mAdapter;
    private boolean mSubtitleVisible;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        mBookRecyclerView = (RecyclerView) view.findViewById(R.id.book_recycler_view);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState!=null){
            mSubtitleVisible=savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_book_list,menu);

        MenuItem subtitleItem=menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_book:
                Book book=new Book();
                BookLab.get(getActivity()).addBook(book);
                Intent intent =BookPagerActivity.newIntent(getActivity(), book.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle(){
        BookLab bookLab=BookLab.get(getActivity());
        int bookCount=bookLab.getBooks().size();
        String subtitle=getString(R.string.subtitle_format,bookCount);

        if (!mSubtitleVisible){
            subtitle=null;
        }
        AppCompatActivity activity=(AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        BookLab bookLab = BookLab.get(getActivity());
        List<Book> books = bookLab.getBooks();

        if (mAdapter == null) {
            mAdapter = new BookAdapter(books);
            mBookRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setBooks(books);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Book mBook;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mCheckBox;



        public BookHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_book,parent,false));
            itemView.setOnClickListener(this);

            mTitleTextView=(TextView)itemView.findViewById(R.id.book_title);
            mDateTextView=(TextView)itemView.findViewById(R.id.book_date);
            mCheckBox=(CheckBox)itemView.findViewById(R.id.checkBox);

        }

        public void bind(Book book){
            mBook=book;
            mTitleTextView.setText(mBook.getTitle());
            mDateTextView.setText(mBook.getDate().toString());
            mCheckBox.setChecked(mBook.isSolved());
        }

        @Override
        public void onClick(View view) {
            Intent intent=BookPagerActivity.newIntent(getActivity(),mBook.getId());
            startActivity(intent);
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder>{
        private List<Book> mBooks;
        public BookAdapter(List<Book> books){
            mBooks=books;
        }


        @Override
        public BookHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BookHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder( BookHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount() {

            return mBooks.size();
        }
        public void setBooks(List<Book> books){
            mBooks=books;
        }
    }
}
