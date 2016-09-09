package com.wu.alison.wordsearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by Alison on 2016-06-12.
 */
public class LetterAdapter extends BaseAdapter {

    private Context mContext;

    private String[] wordList = {"Alison", "Timothy", "Leona", "Stella", "Lily"};
    private static WordSearch wordSearch;
    private static int row, col;

    public LetterAdapter(Context c) {
        mContext = c;
        wordSearch = new WordSearch(wordList, 10);
    }

    @Override
    public int getCount() {
        return wordSearch.getSize()*wordSearch.getSize();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }
        row = position/wordSearch.getSize();
        col = position%wordSearch.getSize();
        textView.setText(Character.toString(wordSearch.getWordSearch()[row][col]));
        return textView;
    }

    public static String getLetter(int position) {
        row = position / wordSearch.getSize();
        col = position % wordSearch.getSize();
        return Character.toString(wordSearch.getWordSearch()[row][col]);
    }
}