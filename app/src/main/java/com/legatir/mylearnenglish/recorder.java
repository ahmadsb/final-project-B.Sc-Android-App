package com.legatir.mylearnenglish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class recorder extends AppCompatActivity {
    ImageView recorder_sound, volume_word;
    private  TextView word;
    private TextView resultRecognize=null;
    Button check_word=null;

    CircleMenu circleMenu;
    String lessonContentTitle;
    String levelTitle = null;

    android.support.v7.widget.Toolbar toolbar;

    int index=0;
    int categoryIndex = -1;
    String categoryTitle = null;
    String titleLesson =null;
    int lessonIndex;
    int levelIndex = -1;
    ArrayList<HashMap<String,String>> wordsAndTheirData = null;
    ArrayList<HashMap<String,String>> uniqueWordsAndTheirData = null;
    TextView []mDots;
    LinearLayout mDotLayout;
    Set<String> uniqueEnglishWordsSet = null;

    ArrayList<String> uniqueEnglishWordsArrayList = null;
    // dialog
    SwipeDismissDialog swipeDismissDialog;
    Button btnOk;
    TextView txDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        volume_word=(ImageView)findViewById(R.id.volume_word);
        check_word=(Button)findViewById(R.id.check_word);
        check_word.setEnabled(false);
        initToolBar("Recorder ");
        initCircleMenu();
        mDotLayout=(LinearLayout)findViewById(R.id.mdotLayout);


        word=(TextView)findViewById(R.id.word);
        resultRecognize=(TextView)findViewById(R.id.resultRecognize);
        volume_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextToSpeechHandler.speak(word.getText().toString());
            }
        });
        recorder_sound=(ImageView)findViewById(R.id.recorder_sound);
        recorder_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSpeechInput(view);
            }
        });

        getAllDataOFLesson();
        String pathFile=CategoriesHandler.categoriesDirectories[categoryIndex-1] +
                "/LEVEL" + levelIndex + "/LESSON" + lessonIndex + "/TARGETW.xml";
        readFileXml(pathFile);
        setArrayLists();

        check_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index <= uniqueEnglishWordsArrayList.size()){
                    if(index <= uniqueEnglishWordsArrayList.size()-1)
                        addDotsIndicator(index);
                    //answers from 0 to 5
                    View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.message_dialog,null);
                    //method  init dialog ' init '
                    buildDialog(dialog);
                    // method  init the components of file xml od massage dialog
                    initComponentsOfDialog(dialog);


                    if(word.getText().toString().toUpperCase().compareTo(resultRecognize.getText().toString().toUpperCase())==0 ) {
                        imageView.setImageResource(R.drawable.correct_answer);
                        txDialog.setText("מצוין ,"  );
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.not_correct_answer);
                        txDialog.setText("לא נכון ,  "  );
                    }

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeDismissDialog.dismiss();
//                            if(index<=wordsAndTheirData.size())
                            if(index <= uniqueEnglishWordsArrayList.size()-1)
                                word.setText(uniqueEnglishWordsArrayList.get(index++).toString());
                            else {// if arrice to final question  do this ..
                                Toast.makeText(recorder.this,"final question",Toast.LENGTH_SHORT).show();
                                recorder_sound.setEnabled(false);
                            }
                            resultRecognize.setText("");
                            check_word.setEnabled(false);

                        }

                    });

                }else  {

                    Toast.makeText(recorder.this,"final question",Toast.LENGTH_SHORT).show();
                }
            }
        });
        word.setText(uniqueEnglishWordsArrayList.get(index++).toString());

    }

    public  void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(intent, 10);
        }else{
            Toast.makeText(this,"Your Device Don't support Speech Input",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK  && i != null){
                    check_word.setEnabled(true);
                    ArrayList<String> result=i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultRecognize.setText(result.get(0).toString());
                    System.out.println("teeeeest"+result);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, i);

    }
    private void getAllDataOFLesson(){


        categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        categoryTitle = getIntent().getStringExtra("CATEGORY_TITLE");
        levelIndex = getIntent().getIntExtra("LEVEL_INDEX", 0);
        titleLesson = getIntent().getStringExtra("LESSON_TITLE");
        lessonIndex = getIntent().getIntExtra("LESSON_INDEX", 0);

    }
    private void readFileXml(String pathFile){
        try {
            wordsAndTheirData =
                    new XmlParser().parseSectionFromFileToArrayOfHashMaps(
                            this,
                            pathFile,
                            "Words",
                            null,
                            null,
                            "word");
        }
        catch (Exception e) {
            Toast.makeText(this,"תקלה בהצגת המילים ללימוד." + "\n" + e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
    private void setArrayLists(){
        uniqueEnglishWordsSet = new HashSet<String>();
        uniqueWordsAndTheirData = new ArrayList<HashMap<String, String>>();
        uniqueEnglishWordsArrayList = new ArrayList<String>();

        for ( HashMap<String,String> wordAndItsData : wordsAndTheirData ) {
            if ( !uniqueEnglishWordsSet.contains(wordAndItsData.get("Eng")) ) {

                uniqueEnglishWordsSet.add(wordAndItsData.get("Eng"));
                uniqueWordsAndTheirData.add(wordAndItsData);
                uniqueEnglishWordsArrayList.add(wordAndItsData.get("Eng"));
            }
        }
    }


    //methods of Dialog
    private void buildDialog(View dialog){
        swipeDismissDialog = new SwipeDismissDialog.Builder(this)
                .setView(dialog)
                .build()
                .show();
    }
    private void initComponentsOfDialog(View dialog){
        btnOk=(Button)dialog.findViewById(R.id.btnOk);
        txDialog=(TextView)dialog.findViewById(R.id.txDialog);
        imageView=(ImageView)dialog.findViewById(R.id.imageView);
    }
    private void initToolBar(String title){
        //toobar init
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_learn_words);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setDrawingCacheBackgroundColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    private void initCircleMenu(){
        final boolean[] flagCheck = {false};
        final int[] indexSelceted = {-1};
        circleMenu=(CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.ic_track_changes_black_24dp,R.drawable.ic_remove_black_24dp)
                .addSubMenu(Color.parseColor("#72CAAF"),R.drawable.learnword)
                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.exercise)
//                .addSubMenu(Color.parseColor("#CEA1A7"),R.drawable.sound)
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
        finish();
        switch (index){
            case 0:
                intent = new Intent(this, LearnWordsActivity.class);
                break;
            case 1:
                intent = new Intent(this, PracticeActivity.class);
                break;
            case 2:
                intent = new Intent(this, AdditionalQuestionsActivity.class);
                break;
            case 3:
                intent = new Intent(this, exam.class);
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
    public void addDotsIndicator(int position){
        mDots=new TextView[uniqueEnglishWordsArrayList.size()];
        mDotLayout.removeAllViews();
        for (int i=0; i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDotLayout.addView(mDots[i]);

        }
        if(uniqueEnglishWordsArrayList.size() > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

}
