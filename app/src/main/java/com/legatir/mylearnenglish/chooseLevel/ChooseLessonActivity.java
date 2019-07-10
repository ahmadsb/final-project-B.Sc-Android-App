package com.legatir.mylearnenglish.chooseLevel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.legatir.mylearnenglish.CategoriesHandler;
import com.legatir.mylearnenglish.LearnWordsActivity;
import com.legatir.mylearnenglish.LessonFirstPageActivity;
import com.legatir.mylearnenglish.LessonsTitilesListAdapter;
import com.legatir.mylearnenglish.R;
import com.legatir.mylearnenglish.TextToSpeechHandler;
import com.legatir.mylearnenglish.XmlParser;

import java.util.ArrayList;

public class ChooseLessonActivity extends AppCompatActivity {

    Button start,pause,stop;
    LinearLayout lyout_read, lyout_voc, lyout_gram;
    android.support.v7.widget.Toolbar toolbar;
    Activity currentContext = this;
    final int defSize=20,newSize=25;
    int categoryIndex = -1;
    String categoryTitle = null;

    String levelTitle = null;
    int levelIndex = -1;

    ListView lessonsListView = null;

    String[] lessonTitlesHebrewArray = null;
    String[] lessonTitlesEnglishArray = null;
    String[] lessonsDescriptionsHebrewArray = null;
    ArrayList<String> lessonsTitlesHebrew = null;
    ArrayList<String> lessonsTitlesEnglish = null;
    MediaPlayer mediaPlayer = null;

    int saidLessonNumber = -1;

    SearchView resultSrch=null;
    TextView total=null;
    TextView tv_beginner = null, tv_students =null, tv_academic=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_lesson);
        lyout_read=findViewById(R.id.lyout_reading);
        lyout_voc=findViewById(R.id.lyout_voc);
        lyout_gram=findViewById(R.id.lyout_gram);

        TextToSpeechHandler.initialize(currentContext);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_choose_lesson);
        toolbar.setTitle("choose a lesson");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setDrawingCacheBackgroundColor(Color.WHITE);
        setSupportActionBar(toolbar);
        lyout_read.setVisibility(View.VISIBLE);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        tv_beginner=(TextView)findViewById(R.id.tv_beginner);
        tv_students=(TextView)findViewById(R.id.tv_students);
        tv_academic=(TextView)findViewById(R.id.tv_academic);

        categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", -1);
        categoryTitle = getIntent().getStringExtra("CATEGORY_TITLE");
        levelIndex = getIntent().getIntExtra("LEVEL_INDEX", -1);
        levelTitle = getIntent().getStringExtra("LEVEL_TITLE");

        tv_beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tv_beginner.setTextColor(Color.BLUE);
//                tv_beginner.setTextSize(newSize);

//                tv_students.setTextColor(Color.parseColor("#FFFFFF"));
//                tv_students.setTextSize(defSize);

//                tv_academic.setTextColor(Color.parseColor("#FFFFFF"));
//                tv_academic.setTextSize(defSize);

                lyout_read.setVisibility(View.VISIBLE);
                lyout_voc.setVisibility(View.INVISIBLE);
                lyout_gram.setVisibility(View.INVISIBLE);

                setList(1);
            }
        });
        tv_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tv_beginner.setTextColor(Color.parseColor("#FFFFFF"));
////                tv_beginner.setTextSize(defSize);
//
//                tv_students.setTextColor(Color.BLUE);
////                tv_students.setTextSize(newSize);
//
//                tv_academic.setTextColor(Color.parseColor("#FFFFFF"));
////                tv_academic.setTextSize(defSize);
                lyout_read.setVisibility(View.INVISIBLE);
                lyout_voc.setVisibility(View.VISIBLE);
                lyout_gram.setVisibility(View.INVISIBLE);
                setList(2);

            }
        });
        tv_academic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tv_beginner.setTextColor(Color.parseColor("#FFFFFF"));
////                tv_beginner.setTextSize(defSize);
//
//                tv_students.setTextColor(Color.parseColor("#FFFFFF"));
////                tv_students.setTextSize(defSize);
//
//                tv_academic.setTextColor(Color.BLUE);
////                tv_academic.setTextSize(newSize);

                lyout_read.setVisibility(View.INVISIBLE);
                lyout_voc.setVisibility(View.INVISIBLE);
                lyout_gram.setVisibility(View.VISIBLE);

                setList(3);
            }
        });

        lessonsListView =  (ListView) findViewById(R.id.lessonsListview);
        resultSrch=(SearchView)findViewById(R.id.searchList);
        resultSrch.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search) + "</font>"));

//        total=(TextView)findViewById(R.id.textView);


        setList(1);//showing default lesson

        // ListView Item Click Listener
        lessonsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition  = position;

                // ListView Clicked item value
                String  itemValue    = (String) lessonsListView.getItemAtPosition(position);

//               TextToSpeechHandler.speak(lessonTitlesEnglishArray[itemPosition]);

                gotoLessonContentActivity(itemPosition+1);

            }

        });

        final String [] final_lessonTitlesHebrewArray = lessonTitlesHebrewArray;
        final String [] final_lessonsTitlesEnglish = lessonTitlesEnglishArray;

        final ArrayList<String> finallessonTitlesHebrewArray = lessonsTitlesHebrew;
        final ArrayList<String> finallessonsTitlesEnglish = lessonsTitlesEnglish;

        final ArrayList<String> resultSearch = new ArrayList<>();

        resultSrch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resultSearch.clear();
                for(int i=0;i<finallessonsTitlesEnglish.size();i++){
                    if(finallessonsTitlesEnglish.get(i).toLowerCase().contains(newText.toLowerCase())
                            || finallessonTitlesHebrewArray.get(i).toLowerCase().contains(newText.toLowerCase()) ) {
                        resultSearch.add(finallessonTitlesHebrewArray.get(i));
                    }
                }
                String[] ArrayStr_OF_resultSeacrch = new String[resultSearch.size()];
                ArrayStr_OF_resultSeacrch=resultSearch.toArray(ArrayStr_OF_resultSeacrch);

                LessonsTitilesListAdapter adapter=
                        new LessonsTitilesListAdapter(currentContext, ArrayStr_OF_resultSeacrch, final_lessonsTitlesEnglish );
//                total.setText("סכ''ה: "+resultSearch.size());


                if(newText.equals(""))
                {
//                    total.setText("סכ''ה: "+finallessonTitlesHebrewArray.size());
                    adapter=new LessonsTitilesListAdapter(currentContext, final_lessonTitlesHebrewArray,  final_lessonsTitlesEnglish);
                }
                // Assign adapter to ListView
                lessonsListView.setAdapter(adapter);
                return true;
            }
        });

    }



    private void setList(int Index){
        levelIndex=Index;
        try {

            lessonsTitlesHebrew = new XmlParser().parseSectionFromFileToArray(currentContext,
                    CategoriesHandler.categoriesDirectories[categoryIndex-1] +
                            "/XML" + "/XMLLessons.xml",
                    "LevelsHeb",
                    "Level" + Index,
                    null,
                    "Lesson");
            lessonsTitlesEnglish = new XmlParser().parseSectionFromFileToArray(currentContext,
                    CategoriesHandler.categoriesDirectories[categoryIndex-1] +
                            "/XML" + "/XMLLessons.xml",
                    "LevelsEng",
                    "Level" + Index,
                    null,
                    "Lesson");

        }
        catch (Exception e) {
            Toast.makeText(currentContext,"תקלה בהצגת שיעורים." + "\n" + e.getMessage(), Toast.LENGTH_SHORT);
        }

        //lessonTitlesHebrewArray = getResources().getStringArray(R.array.lessons_titles_hebrew_array);
        for (int i = 0; i < lessonsTitlesHebrew.size(); i++) {
            lessonsTitlesHebrew.set( i, "שיעור " + (i+1) + ": " + lessonsTitlesHebrew.get(i)  );
        }

        lessonTitlesHebrewArray = new String[lessonsTitlesHebrew.size()];
        lessonTitlesHebrewArray = lessonsTitlesHebrew.toArray(lessonTitlesHebrewArray);
//        total.setText("סכ''ה: "+lessonsTitlesHebrew.size());


        for (int i = 0; i < lessonsTitlesEnglish.size(); i++) {
            lessonsTitlesEnglish.set( i, "Lesson " + (i+1) + ": " + lessonsTitlesEnglish.get(i)  );
        }

        //lessonTitlesEnglishArray = getResources().getStringArray(R.array.lessons_titles_english_array);
        lessonTitlesEnglishArray = new String[lessonsTitlesEnglish.size()];
        lessonTitlesEnglishArray = lessonsTitlesEnglish.toArray(lessonTitlesEnglishArray);



        //lessonsDescriptionsHebrewArray =
        //        getResources().getStringArray(R.array.lessons_descriptions_hebrew_array);
        final LessonsTitilesListAdapter adapter=
                new LessonsTitilesListAdapter(currentContext, lessonTitlesHebrewArray, lessonTitlesEnglishArray);

        // Assign adapter to ListView
        lessonsListView.setAdapter(adapter);
    }
    public void gotoLessonContentActivity( int lessonIndex ) {

//        TextToSpeechHandler.speak(lessonTitlesEnglishArray[lessonIndex-1]);

//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            //e.printStackTrace();
//        }
        if( lessonIndex>3)
        {
            Toast.makeText(currentContext, "this lesson not found", Toast.LENGTH_SHORT).show();

        }
        else{
            System.out.println("categoryIndex "+categoryIndex+" Indexlevel "+levelIndex+" lessonIndex "+lessonIndex);

            Intent intent = new Intent(currentContext, LearnWordsActivity.class);
            intent.putExtra("CATEGORY_TITLE", categoryTitle);
            intent.putExtra("CATEGORY_INDEX", categoryIndex);
            intent.putExtra("LEVEL_TITLE", levelTitle);
            intent.putExtra("LEVEL_INDEX", levelIndex);
            intent.putExtra("LESSON_TITLE", lessonTitlesHebrewArray[lessonIndex-1]);
            intent.putExtra("LESSON_INDEX", lessonIndex);
            startActivity(intent);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}

