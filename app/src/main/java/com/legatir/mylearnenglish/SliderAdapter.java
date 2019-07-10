package com.legatir.mylearnenglish;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ahmadsb on 5/20/2018.
 */

public class SliderAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    public String word;
    int size;
    TextView txt_word;
    TextView heb_txt_word;
    Button btn_sound;

    ArrayList<String> wordsAndTheirData = null;
    ArrayList<String> hebWordsAndTheirData = null;

    public SliderAdapter(Context context , ArrayList<String> wordsAndTheirData, ArrayList<String> hebWordsAndTheirData,int size){
        this.mContext=context;
        this.wordsAndTheirData=wordsAndTheirData;
        this.hebWordsAndTheirData = hebWordsAndTheirData;
        this.size=size;

    }


    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,final int position) {
        mLayoutInflater=(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view=mLayoutInflater.inflate(R.layout.slide_layout,container,false);

        txt_word=(TextView)view.findViewById(R.id.txt_word);
        heb_txt_word = view.findViewById(R.id.txt_word_heb);
        btn_sound=(Button) view.findViewById(R.id.btn_sound);
        txt_word.setText(wordsAndTheirData.get(position));
        heb_txt_word.setText(hebWordsAndTheirData.get(position));
        System.out.println("slider adapter"+position);

        btn_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextToSpeechHandler.speak(wordsAndTheirData.get(position));
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

}
