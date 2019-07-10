package com.legatir.mylearnenglish;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.library.NavigationBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class AdditionalQuestionsActivity extends AppCompatActivity {

    Activity currentContext = this;
    TextView txtTitle=null,txtSubTitle=null,txtQuery=null;
    RadioGroup groupRadio=null;
    LinearLayout activity_additional_questions=null;
    Button btn_submit=null ,btn_next_query=null;
    ArrayList<String> array_Query;
    private NavigationBar bar;
    ImageButton iconSpeak;
    int categoryIndex = -1;
    String categoryTitle = null;
    android.support.v7.widget.Toolbar toolbar;
    CircleMenu circleMenu;
    int levelIndex = -1;
    int oldIndexChecked;
    TextView []mDots;
    LinearLayout mDotLayout;

    ArrayList<HashMap<String,String>> questionAndTheirData = null;
    ArrayList<HashMap<String,String>>  answersAndTheirData  = null;
    Set<HashMap<String, String>> optionsSet = null;

    int index=1;
    // dialog
    SwipeDismissDialog swipeDismissDialog;
    Button btnOk;
    TextView txDialog;
    ImageView imageView;

    int lessonIndex;
    String lessonContentTitle;
    String levelTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_questions);
        TextToSpeechHandler.initialize(currentContext);
        mDotLayout = (LinearLayout) findViewById(R.id.mdotLayout);
        initToolBar("Additional Quersions");
        initCircleMenu();
        initKeysIntent();


        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(lessonContentTitle);
        txtQuery = (TextView) findViewById(R.id.txt_query);
        groupRadio = (RadioGroup) findViewById(R.id.groupRadio);
        activity_additional_questions = (LinearLayout) findViewById(R.id.activity_additional_questions);

        String pathFile = CategoriesHandler.categoriesDirectories[categoryIndex - 1] +
                "/LEVEL" + levelIndex + "/LESSON" + lessonIndex + "/QUESTIONS.xml";
        try {
            questionAndTheirData =
                    new XmlParser().parseSectionFromFileToArrayOfHashMaps(
                            currentContext,
                            pathFile,
                            "Questions",
                            null,
                            null,
                            "Question");
            System.out.println("eeeeeee" + questionAndTheirData);
        } catch (Exception e) {
            Toast.makeText(currentContext, "תקלה בהצגת שאלות נוספות." + "\n" + e.getMessage(), Toast.LENGTH_SHORT);
        }


        final ArrayList<String> correctSet = new ArrayList<String>();
        array_Query = new ArrayList<String>();
        for (HashMap<String, String> element : questionAndTheirData) {

            if (!array_Query.contains(element.get("question"))) {
                array_Query.add(element.get("question"));

            }
            if (!correctSet.contains(element.get("correct"))) {
                correctSet.add(element.get("correct"));

            }
        }

        try {
            answersAndTheirData =
                    new XmlParser().parseSectionFromFileToArrayOfHashMaps(
                            currentContext,
                            CategoriesHandler.categoriesDirectories[categoryIndex - 1] +
                                    "/LEVEL" + levelIndex + "/LESSON" + lessonIndex + "/QUESTIONS.xml",
                            "Questions",
                            null,
                            "Question" + index,
                            "Answers");
        } catch (Exception e) {
            Toast.makeText(currentContext, "תקלה בהצגת שאלות נוספות." + "\n" + e.getMessage(), Toast.LENGTH_SHORT);
        }


        txtQuery.setText(array_Query.get(index - 1));

//    groupRadio.setOrientation(RadioGroup.VERTICAL);
//    RadioGroup.LayoutParams rl;
//    for (int i=1;i <= answersAndTheirData.size();i++)
//    {
//        RadioButton r=new RadioButton(this);
//        r.setText(answersAndTheirData.get(0).get("answer"+i));
//        rl= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);
//        groupRadio.addView(r,rl);
//    }
        createRadioGroup(answersAndTheirData);


        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_next_query = (Button) findViewById(R.id.btn_next_query);

        btn_next_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index <= array_Query.size()-1) {

                    if(index <= array_Query.size()-1) {
                        addDotsIndicator(index++);
                        txtQuery.setText(array_Query.get(index - 1));
                    }
                    try {
                        answersAndTheirData =
                                new XmlParser().parseSectionFromFileToArrayOfHashMaps(
                                        currentContext,
                                        CategoriesHandler.categoriesDirectories[categoryIndex - 1] +
                                                "/LEVEL" + levelIndex + "/LESSON" + lessonIndex + "/QUESTIONS.xml",
                                        "Questions",
                                        null,
                                        "Question" + index,
                                        "Answers");
                    } catch (Exception e) {
                        Toast.makeText(currentContext, "תקלה בהצגת שאלות נוספות." + "\n" + e.getMessage(), Toast.LENGTH_SHORT);
                    }

                    createRadioGroup(answersAndTheirData);



                } else {
                    Toast.makeText(currentContext,"final question ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton oldrb = (RadioButton) findViewById(oldIndexChecked);
                oldrb.setBackgroundResource(R.drawable.border);
                RadioButton rb = (RadioButton) findViewById(i);
                rb.setBackgroundResource(R.drawable.myrect);
                btn_submit.setEnabled(true);
                oldIndexChecked = i;
            }
        });
        btn_submit.setEnabled(false);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > array_Query.size()) {
                    Toast.makeText(currentContext,"final question ",Toast.LENGTH_SHORT).show();

                }else{

                    btn_submit.setEnabled(false);
//                if(groupRadio.getCheckedRadioButtonId() == -1)
//                {
//                    Toast.makeText(currentContext,"first click answer",Toast.LENGTH_LONG).show();
//                    return ;
//                }

                    //answers from 0 to 5
                    View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.message_dialog, null);
                    //method  init dialog ' init '
                    buildDialog(dialog);
                    // method  init the components of file xml od massage dialog
                    initComponentsOfDialog(dialog);
                    String strCorrectAnswer="";
                    int indCorrectAnswer=0;
                    // the answers start from 'index' 0 so do +1 to indCorrectAnswer
                    if(index <= array_Query.size()-1) {
                        indCorrectAnswer = Integer.valueOf(correctSet.get(index - 1)) ;
                        indCorrectAnswer++;
                        strCorrectAnswer = answersAndTheirData.get(0).get("answer" + indCorrectAnswer);
                        indCorrectAnswer--;
                    }
                    if (groupRadio.getCheckedRadioButtonId() == indCorrectAnswer) {
                        imageView.setImageResource(R.drawable.correct_answer);
                        txDialog.setText("מצוין , התשובה הנכונה היא " + strCorrectAnswer);
                    } else {
                        imageView.setImageResource(R.drawable.not_correct_answer);
                        txDialog.setText("לא נכון , התשובה הנכונה היא " + strCorrectAnswer);
                    }


                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeDismissDialog.dismiss();
                            if(index <= array_Query.size()-1)
                                btn_next_query.callOnClick();
                        }

                    });
                }

            }

        });


        // button of icon speak
        iconSpeak = (ImageButton) findViewById(R.id.iconSpeak);
        iconSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextToSpeechHandler.speak(array_Query.get(index - 1));
            }
        });
        addDotsIndicator(0);


    }
    //methods of Dialog
    private void buildDialog(View dialog){
        swipeDismissDialog = new SwipeDismissDialog.Builder(AdditionalQuestionsActivity.this)
                .setView(dialog)
                .build()
                .show();
    }
    private void initComponentsOfDialog(View dialog){
        btnOk=(Button)dialog.findViewById(R.id.btnOk);
        txDialog=(TextView)dialog.findViewById(R.id.txDialog);
        imageView=(ImageView)dialog.findViewById(R.id.imageView);
    }

    private void createRadioGroup( ArrayList<HashMap<String,String>> answersAndTheirData){
        groupRadio.removeAllViews();
        groupRadio.setOrientation(RadioGroup.VERTICAL);
        RadioGroup.LayoutParams rl;


        for (int i=1;i <= answersAndTheirData.get(0).size();i++)
        {
            RadioButton r=new RadioButton(this);
            r.setId(i-1);
            r.setBackgroundResource(R.drawable.border);
            r.setText("  "+answersAndTheirData.get(0).get("answer"+i));
            r.setTextSize(25);

            rl= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.WRAP_CONTENT);
            rl.setMargins(30,30,80,30);
            groupRadio.addView(r,rl);
        }
    }



    public void addDotsIndicator(int position){
        mDots=new TextView[array_Query.size()];
        mDotLayout.removeAllViews();
        for (int i=0; i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDotLayout.addView(mDots[i]);

        }
        if(array_Query.size() > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
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
                .addSubMenu(Color.parseColor("#CEA1A7"),R.drawable.sound)
//                .addSubMenu(Color.parseColor("#ACB7D0"),R.drawable.morequery)
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
                intent = new Intent(this, recorder.class);
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
    private void initKeysIntent(){
        categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        categoryTitle = getIntent().getStringExtra("CATEGORY_TITLE");
        levelIndex = getIntent().getIntExtra("LEVEL_INDEX", 0);

        lessonContentTitle = getIntent().getStringExtra("LESSON_TITLE");
        lessonIndex = getIntent().getIntExtra("LESSON_INDEX", 0);
    }


}

