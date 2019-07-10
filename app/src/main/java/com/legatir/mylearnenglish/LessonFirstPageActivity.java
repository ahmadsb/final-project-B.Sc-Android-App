package com.legatir.mylearnenglish;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class LessonFirstPageActivity extends Activity {
    Button start,pause,stop;
    String arrayName[]={"Learn Word",
            "exercice1",
            "exercice2",
            "setlectExam",
            "moreQuery"};

    CircleMenu circleMenu;
    Activity currentContext = this;

    int categoryIndex = -1;
    String categoryTitle = null;

    int levelIndex = -1;
    String levelTitle = null;
//    Toolbar toolbar;

    String[] firstPageContentsArray = null;

    TextView txtTitle = null;
    TextView txtContent = null;
    android.support.v7.widget.CardView btnLearnWords = null;
    android.support.v7.widget.CardView btnPractice = null;
    android.support.v7.widget.CardView btnRehearsal=null;
    android.support.v7.widget.CardView btnTestYourSelf=null;
    android.support.v7.widget.CardView btnAdditionalQuestions=null;
    String lessonContentTitle;
    int lessonIndex;
    String[] lessonTitlesHebrewArray = null;
    String[] lessonsDescriptionsHebrewArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_first_page);

        TextToSpeechHandler.initialize(currentContext);
//        initCompsLayout();
        getAllDataOFLesson();





//        txtTitle.setText(lessonContentTitle);
//
//        firstPageContentsArray = getResources().getStringArray(R.array.lessons_first_page_content_array);
//
//        txtContent.setText( firstPageContentsArray[lessonIndex]);
//        toolbar = findViewById(R.id.toolbar);
//        ((AppCompatActivity)currentContext).setSupportActionBar(toolbar);
//        ((AppCompatActivity)currentContext).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        initCircleMenu();// metho to init the circle View of options


//        btnLearnWords.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                // currentContext.startActivity(activityChangeIntent);
//
//                TextToSpeechHandler.speak("Learn word");
//
//                try {
//                    Thread.sleep(1000);
//
//                    Intent intent = new Intent(LessonFirstPageActivity.this, LearnWordsActivity.class);
//                    intent.putExtra("CATEGORY_TITLE", "Category " + categoryIndex);
//                    intent.putExtra("CATEGORY_INDEX", categoryIndex);
//                    intent.putExtra("LEVEL_TITLE", levelTitle);
//                    intent.putExtra("LEVEL_INDEX", levelIndex);
//                    intent.putExtra("LESSON_TITLE", lessonContentTitle);
//                    intent.putExtra("LESSON_INDEX", lessonIndex);
//                    startActivity(intent);
//                } catch (InterruptedException e) {
//                    //e.printStackTrace();
//                }
//
//
//
//            }
//        });
//
//        btnPractice.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                // currentContext.startActivity(activityChangeIntent);
//
//                TextToSpeechHandler.speak("Practice");
//
//                try {
//                    Thread.sleep(1000);
//
//                    Intent intent = new Intent(LessonFirstPageActivity.this, PracticeActivity.class);
//                    intent.putExtra("CATEGORY_TITLE", "Category " + categoryIndex);
//                    intent.putExtra("CATEGORY_INDEX", categoryIndex);
//                    intent.putExtra("LEVEL_TITLE", levelTitle);
//                    intent.putExtra("LEVEL_INDEX", levelIndex);
//                    intent.putExtra("LESSON_TITLE", lessonContentTitle);
//                    intent.putExtra("LESSON_INDEX", lessonIndex);
//                    startActivity(intent);
//
//
//                } catch (InterruptedException e) {
//                    //e.printStackTrace();
//                }
//
//
//
//            }
//        });
//
//        btnAdditionalQuestions.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                // currentContext.startActivity(activityChangeIntent);
//
//                TextToSpeechHandler.speak("Additional Questions");
//
//                try {
//                    Thread.sleep(1000);
//
//                    Intent intent = new Intent(LessonFirstPageActivity.this, AdditionalQuestionsActivity.class);
//                    intent.putExtra("CATEGORY_TITLE", "Category " + categoryIndex);
//                    intent.putExtra("CATEGORY_INDEX", categoryIndex);
//                    intent.putExtra("LEVEL_TITLE", levelTitle);
//                    intent.putExtra("LEVEL_INDEX", levelIndex);
//                    intent.putExtra("LESSON_TITLE", lessonContentTitle);
//                    intent.putExtra("LESSON_INDEX", lessonIndex);
//                    startActivity(intent);
//
//
//                } catch (InterruptedException e) {
//                    //e.printStackTrace();
//                }
//
//
//
//            }
//        });
//
//        btnTestYourSelf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextToSpeechHandler.speak("Test Your Self");
//
//                try {
//                    Thread.sleep(1000);
//
//
//
//
//                } catch (InterruptedException e) {
//                    //e.printStackTrace();
//                }
//            }
//        });
//
//        //previous activity
//        btnRehearsal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextToSpeechHandler.speak("back");
//                try {
//                    Thread.sleep(1000);
//                    onBackPressed();
//                } catch (InterruptedException e) {
//                    //e.printStackTrace();
//                }
//
//            }
//        });


    }

    //    public void initCompsLayout(){
//
//        txtTitle = (TextView) findViewById(R.id.lessonFirstPageTxtTitle);
//        txtContent = (TextView) findViewById(R.id.lessonFirstPageTxtContent);
//        btnLearnWords =  (android.support.v7.widget.CardView) findViewById(R.id.btnLearnWords);
//        btnPractice =  (android.support.v7.widget.CardView) findViewById(R.id.btnPractice);
//        btnRehearsal =  (android.support.v7.widget.CardView) findViewById(R.id.btnRehearsal);
//        btnTestYourSelf =  (android.support.v7.widget.CardView) findViewById(R.id.btnTestYourSelf);
//        btnAdditionalQuestions =  (android.support.v7.widget.CardView) findViewById(R.id.btnAdditionalQuestions);
//    }
    private void initCircleMenu(){
        final boolean[] flagCheck = {false};
        final int[] indexSelceted = {-1};
        circleMenu=(CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.ic_track_changes_black_24dp,R.drawable.ic_remove_black_24dp)
                .addSubMenu(Color.parseColor("#72CAAF"),R.drawable.learnword)
                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.exercise)
                .addSubMenu(Color.parseColor("#CEA1A7"),R.drawable.sound)
                .addSubMenu(Color.parseColor("#ACB7D0"),R.drawable.morequery)
                .addSubMenu(Color.parseColor("#EFC43F"),R.drawable.exam)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        flagCheck[0] =!flagCheck[0];
                        indexSelceted[0] =i;

                    }
                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {
            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
                if(flagCheck[0]){
                    gotoActivity(indexSelceted[0]);
                    indexSelceted[0]=-1;
                    flagCheck[0]=!flagCheck[0];
                }
            }
        });

    }
    private void gotoActivity(int index){
        Intent intent=null;
        switch (index){
            case 0:
                intent = new Intent(LessonFirstPageActivity.this, LearnWordsActivity.class);
                break;
            case 1:
                intent = new Intent(LessonFirstPageActivity.this, PracticeActivity.class);
                break;
            case 2:
                intent = new Intent(LessonFirstPageActivity.this, recorder.class);
                break;
            case 3:
                intent = new Intent(LessonFirstPageActivity.this, AdditionalQuestionsActivity.class);
                break;
            case 4:
                intent = new Intent(LessonFirstPageActivity.this, exam.class);
                break;


        }

        intent.putExtra("CATEGORY_TITLE", "Category " + categoryIndex);
        intent.putExtra("CATEGORY_INDEX", categoryIndex);
        intent.putExtra("LEVEL_TITLE", levelTitle);
        intent.putExtra("LEVEL_INDEX", levelIndex);
        intent.putExtra("LESSON_TITLE", lessonContentTitle);
        intent.putExtra("LESSON_INDEX", lessonIndex);

        startActivity(intent);



    }
    private void getAllDataOFLesson(){

        categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        categoryTitle = getIntent().getStringExtra("CATEGORY_TITLE");
        levelIndex = getIntent().getIntExtra("LEVEL_INDEX", 0);
        lessonIndex = getIntent().getIntExtra("LESSON_INDEX", 0);

    }

}
