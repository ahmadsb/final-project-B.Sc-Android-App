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
import android.widget.TextView;

public class LessonContentListAdapter extends ArrayAdapter<String> {

    private Activity context = null;

    private String[] lessonTitlesHebrewArray = null;

    public LessonContentListAdapter(Activity context, String[] lessonTitlesHebrewArray) {

            super(context, R.layout.lesson_content_row, lessonTitlesHebrewArray);

            this.context=context;
            this.lessonTitlesHebrewArray =
                    lessonTitlesHebrewArray;
    }


    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        //LayoutInflater inflater=LayoutInflater.from(getContext());
        View rowView=inflater.inflate(R.layout.lesson_content_row, null,true);
        //View rowView=inflater.inflate(R.layout.lesson_title_row, parent, false);

        TextView txtLessonTitle = (TextView) rowView.findViewById(R.id.lessonTitle);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView txtLessonDescription = (TextView) rowView.findViewById(R.id.lessonDescription);

        txtLessonTitle.setText(lessonTitlesHebrewArray[position]);

        // Set the border of View (ListView Item)
        //rowView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape));

        //imageView.setImageResource(imgid[position]);
        //extratxt.setText(itemname[position]);
        return rowView;

    };
}
