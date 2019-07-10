package com.legatir.mylearnenglish;

/**
 * Created by lital on 08/09/2017.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LearnWordsListAdapter extends ArrayAdapter<String> {

    private Activity context = null;

    private ArrayList<String> englishWordsArrayList = null;

    public LearnWordsListAdapter(
                Activity context, ArrayList<String> englishWordsArray) {

            //super(context, R.layout.lesson_title_row, englishWordsArray);
            super(context, R.layout.learn_words_row, englishWordsArray);

            this.context=context;
            this.englishWordsArrayList = englishWordsArray;
    }


    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.learn_words_row, null,true);

        TextView txtEnglishWord = (TextView) rowView.findViewById(R.id.englishWord);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txtEnglishWord.setText(englishWordsArrayList.get(position));

        return rowView;


    };
}
