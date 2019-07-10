package com.legatir.mylearnenglish;

/**
 * Created by lital on 08/09/2017.
 */
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LessonsTitilesListAdapter extends ArrayAdapter<String> {

    private Activity context = null;
    private ImageView imageViewLesson;
    private String[] lessonTitlesHebrewArray = null;
    private String[] lessonDescriptionHebrewArray = null;


    public LessonsTitilesListAdapter(
            Activity context, String[] lessonTitlesHebrewArray, String[] lessonDescriptionHebrewArray) {

        super(context, R.layout.lesson_title_row, lessonTitlesHebrewArray);

        this.context=context;
        this.lessonTitlesHebrewArray = lessonTitlesHebrewArray;
        this.lessonDescriptionHebrewArray = lessonDescriptionHebrewArray;
    }


    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        //LayoutInflater inflater=LayoutInflater.from(getContext());
        View rowView=inflater.inflate(R.layout.lesson_title_row, null,true);
        //View rowView=inflater.inflate(R.layout.lesson_title_row, parent, false);

        TextView txtLessonTitle = (TextView) rowView.findViewById(R.id.lessonTitle);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView txtLessonDescription = (TextView) rowView.findViewById(R.id.lessonDescription);


        txtLessonTitle.setText(lessonTitlesHebrewArray[position]);
        //imageView.setImageResource(imgid[position]);
        //extratxt.setText(itemname[position]);
        txtLessonDescription.setText(lessonDescriptionHebrewArray[position]);


        //rowView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape));

        imageViewLesson=(ImageView)rowView.findViewById(R.id.imageViewLesson);

//    if(position%2==0){
//        imageViewLesson.setBackgroundResource(R.drawable.lessonitem);

//    }
//    else if(position%3==0){
//        imageViewLesson.setBackgroundResource(R.drawable.item3);
//
//    }
//    else
//    {
//        imageViewLesson.setBackgroundResource(R.drawable.item2);
//
//    }


        return rowView;


    };
}
