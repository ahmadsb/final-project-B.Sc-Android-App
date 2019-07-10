package com.legatir.mylearnenglish.chooseLevel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.legatir.mylearnenglish.R;
import com.legatir.mylearnenglish.TextToSpeechHandler;

public class ChooseLevelActivity extends Activity {
    Button start,pause,stop;

    Activity currentContext = this;

    int categoryIndex = -1;
    String categoryTitle = null;

    Button buttonLevel1 = null;
    Button buttonLevel2 = null;
    Button buttonLevel3 = null;


    //MediaPlayer mediaPlayer = null;

    int saidLessonNumber = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        TextToSpeechHandler.initialize(currentContext);

        categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        categoryTitle = getIntent().getStringExtra("CATEGORY_TITLE");

//        List<String> lessonTitles = null;

//        try {
//            lessonTitles = new XmlParser().parseSectionFromFileToArray(
//                    this, "lessons/XML/XMLLessons.xml", "Levels","LevelHeb","Level1","lesson");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        buttonLevel1 =  (Button) findViewById(R.id.button_level1);
        buttonLevel2 =  (Button) findViewById(R.id.button_level2);
        buttonLevel3 =  (Button) findViewById(R.id.button_level3);

        buttonLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLessonContentActivity(1);
            }
        });

        buttonLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLessonContentActivity(2);
            }
        });

        buttonLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLessonContentActivity(3);
            }
        });



    }


    public void gotoLessonContentActivity( int levelIndex ) {

//        TextToSpeechHandler.speak("Level " + levelIndex);
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            //e.printStackTrace();
//        }

        Intent intent = new Intent(currentContext, ChooseLessonActivity.class);
        intent.putExtra("CATEGORY_TITLE", "Category " + categoryIndex);
        intent.putExtra("CATEGORY_INDEX", categoryIndex);
        intent.putExtra("LEVEL_TITLE", "Level " + levelIndex);
        intent.putExtra("LEVEL_INDEX", levelIndex);
        startActivity(intent);

    }


}

//Multimedia voice:

//        start=(Button)findViewById(R.id.button1);
//        pause=(Button)findViewById(R.id.button2);
//        stop=(Button)findViewById(R.id.button3);
//        //creating media player
//        final MediaPlayer mp=new MediaPlayer();
//        try{
//
//            Log.d("-- MyLearnEnglish --", Environment.getExternalStorageDirectory().getPath());
//            xx = Environment.getExternalStorageDirectory().getPath();
//
//
//            String audioStoragePath = Environment.getExternalStorageDirectory().getPath()+"/Recording/test123.mp3";
//
//            //This works well
////          FileInputStream is = new FileInputStream(audioStoragePath);
////          mp.setDataSource(is.getFD());
//
//            mp.setDataSource(audioStoragePath);
//
//
//            mp.prepare();
//
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        start.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mp.start();
//            }
//        });verride
//            pu
//        pause.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mp.pause();
//            }
//        });
//        stop.setOnClickListener(new OnClickListener() {
//            @Oblic void onClick(View v) {
//                mp.stop();
//            }
//        });
