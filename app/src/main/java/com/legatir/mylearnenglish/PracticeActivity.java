package com.legatir.mylearnenglish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.library.NavigationBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class PracticeActivity extends AppCompatActivity {

    Activity currentContext = this;
    CircleMenu circleMenu;
    String lessonContentTitle;

    String levelTitle = null;
    int categoryIndex = -1;
    String categoryTitle = null;

    int levelIndex = -1;
    TextView txtTitle = null;

    int saidLessonNumber = -1;
    android.support.v7.widget.Toolbar toolbar;

    // buttons of options
    Button btn_opt1,btn_opt2,btn_opt3,btn_opt4,btn_opt5,btn_opt6,btn_RSLT,btn_check;
    Boolean flagClick=true;
    // array tu put the data of file 'file.XML'
    ArrayList<HashMap<String,String>> arrayData  = null;
    ArrayList<String> ArrayQuery;
    ArrayList<String> ArrayAnswer;
    //    private NavigationBar bar;
    int index=1;
    int lessonIndex;
    TextView tv_query;
    Hashtable<String, String> hashTable;
    //dialog
    SwipeDismissDialog swipeDismissDialog;
    Button btnOk;
    TextView txDialog;
    ImageView imageView;
    TextView []mDots;
    LinearLayout mDotLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        mDotLayout=(LinearLayout)findViewById(R.id.mdotLayout);
        initToolBar("Parctice");
        categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        categoryTitle = getIntent().getStringExtra("CATEGORY_TITLE");

        levelIndex = getIntent().getIntExtra("LEVEL_INDEX", 0);
        initCircleMenu();
        final String title = getIntent().getStringExtra("LESSON_TITLE");
        lessonIndex = getIntent().getIntExtra("LESSON_INDEX", 0);

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        TextToSpeechHandler.initialize(currentContext);
        tv_query=(TextView)findViewById(R.id.tv_query);
        readFileXMLQuery();// read file xml and put all the query in objectArray of string
        retArrayQuery();// return ArrayQuery
        retArrayAnswer();// return ArrayAnswer
        tv_query.setText(ArrayQuery.get(index-1));
//        initBar();
        initBtnsOptions();// init all the buttons
        setTextButtons();

        createArrayKeyAndValue();
//        btn_check.setVisibility(View.INVISIBLE);
        btn_check.setEnabled(false);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempInt=index-1;
                String resultCorrect=null;

                if(index < ArrayQuery.size())
                {
                    tv_query.setText(ArrayQuery.get(index));

                    addDotsIndicator(index++);
                    resultCorrect= hashTable.get(ArrayQuery.get(tempInt));

//                    bar.setCurrentPosition(index++);

                    //fun. to dialog
                    View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.message_dialog,null);
                    buildDialog(dialog);
                    initComponentsOfDialog(dialog);

                    if(resultCorrect.equals(btn_RSLT.getText().toString()))
                    {
                        imageView.setImageResource(R.drawable.correct_answer);
                        txDialog.setText("מצוין , התשובה הנכונה היא " +resultCorrect );
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.not_correct_answer);
                        txDialog.setText("לא נכון , התשובה הנכונה היא " +resultCorrect );
                    }

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeDismissDialog.dismiss();
                        }

                    });

                    allButtonsVisible();
                    btn_RSLT.setVisibility(View.INVISIBLE);
//                btn_check.setVisibility(View.INVISIBLE);
                    btn_check.setEnabled(false);
                    flagClick=true;
                    setTextButtons();

                }
                else {
                    Toast.makeText(currentContext,"final question",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_RSLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_RSLT.setVisibility(View.INVISIBLE);
//                btn_check.setVisibility(View.INVISIBLE);
                btn_check.setEnabled(false);
                // and all the buttons visible
                allButtonsVisible();
                flagClick=!flagClick;
            }
        });




        btn_opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagClick ) {
                    flagClick = !flagClick;
                    btn_RSLT.setVisibility(View.VISIBLE);
//                    btn_check.setVisibility(View.VISIBLE);
                    btn_check.setEnabled(true);
                    btn_RSLT.setText(btn_opt1.getText().toString());
                    btn_opt1.setVisibility(View.INVISIBLE);
                }
            }
        });


        btn_opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagClick ) {
                    flagClick = !flagClick;
                    btn_RSLT.setVisibility(View.VISIBLE);
//                    btn_check.setVisibility(View.VISIBLE);
                    btn_check.setEnabled(true);
                    btn_RSLT.setText(btn_opt2.getText().toString());
                    btn_opt2.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagClick ) {
                    flagClick = !flagClick;
                    btn_RSLT.setVisibility(View.VISIBLE);
//                    btn_check.setVisibility(View.VISIBLE);
                    btn_check.setEnabled(true);
                    btn_RSLT.setText(btn_opt3.getText().toString());
                    btn_opt3.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagClick ) {
                    flagClick = !flagClick;
                    btn_RSLT.setVisibility(View.VISIBLE);
//                    btn_check.setVisibility(View.VISIBLE);
                    btn_check.setEnabled(true);
                    btn_RSLT.setText(btn_opt4.getText().toString());
                    btn_opt4.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_opt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagClick ) {
                    flagClick = !flagClick;
                    btn_RSLT.setVisibility(View.VISIBLE);
//                    btn_check.setVisibility(View.VISIBLE);
                    btn_check.setEnabled(true);
                    btn_RSLT.setText(btn_opt5.getText().toString());
                    btn_opt5.setVisibility(View.INVISIBLE);
                }
            }
        });

        btn_opt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagClick ) {
                    flagClick = !flagClick;
                    btn_RSLT.setVisibility(View.VISIBLE);
//                    btn_check.setVisibility(View.VISIBLE);
                    btn_check.setEnabled(true);
                    btn_RSLT.setText(btn_opt6.getText().toString());
                    btn_opt6.setVisibility(View.INVISIBLE);
                }
            }
        });

        addDotsIndicator(0);

//
//        int topMargin = 5;
//        int textSize = 20;
//
//        //make a new horizontal LinearLayout each time to hold the children.
//        //LinearLayout temp = new LinearLayout(this);
//        LinearLayout rootLayout = (LinearLayout)findViewById(R.id.layoutPractice);
//
//        LinearLayout layoutByCode = new LinearLayout(this);
//        layoutByCode.setOrientation(LinearLayout.HORIZONTAL);
//        layoutByCode.setLayoutParams
//                (
//                        new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                0)
//                );
//
//
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)layoutByCode.getLayoutParams();
//        params.setMargins(0, topMargin, 0, 0);
//        params.gravity = Gravity.LEFT;
//        layoutByCode.setLayoutParams(params);
//
//
//        TextView textView = new TextView(this);
//        textView.setLayoutParams
//                (
//                        new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT,1)
//                );
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        textView.setText("The");
//        layoutByCode.addView(textView); //add them to this temporary layout.
//
//
//
//        EditText editText = new EditText(this);
//        editText.setLayoutParams
//                (
//                        new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT,5)
//                );
//        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        editText.setText("_____");
//        layoutByCode.addView (editText);
//
//
//        TextView textView2 = new TextView(this);
//        textView2.setLayoutParams
//                (
//                        new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT,1)
//                );
//        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        textView2.setText("  is blue and");
//        layoutByCode.addView(textView2); //add them to this temporary layout.
//
//
//        EditText editText2 = new EditText(this);
//        editText2.setLayoutParams
//                (
//                        new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT,5)
//                );
//        editText2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        editText2.setText("_____");
//        layoutByCode.addView (editText2);
//
//        TextView textView3 = new TextView(this);
//        textView3.setLayoutParams
//                (
//                        new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT,1)
//                );
//        textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        textView3.setText(".");
//        layoutByCode.addView(textView3); //add them to this temporary layout.
//
//        rootLayout.addView(layoutByCode);
//
//
//
//
//        topMargin = 5;
//
//        LinearLayout layoutByCode2 = new LinearLayout(this);
//        layoutByCode2.setOrientation(LinearLayout.HORIZONTAL);
//        layoutByCode2.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,0));
//        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)layoutByCode2.getLayoutParams();
//        params2.setMargins(0, topMargin, 0, 0);
//        params2.gravity = Gravity.LEFT;
//        layoutByCode2.setLayoutParams(params2);
//
//
//        TextView textViewB = new TextView(this);
//        textViewB.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,1));
//        textViewB.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        textViewB.setText("Father");
//        layoutByCode2.addView(textViewB); //add them to this temporary layout.
//
//        EditText editTextB = new EditText(this);
//        editTextB.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,5));
//        editTextB.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        editTextB.setText("_____");
//        layoutByCode2.addView (editTextB);
//
//        TextView textViewB2 = new TextView(this);
//        textViewB2.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,1));
//        textViewB2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        textViewB2.setText(" shirt");
//        layoutByCode2.addView(textViewB2); //add them to this temporary layout.
//
//
//        EditText editTextB2 = new EditText(this);
//        editTextB2.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,5));
//        editTextB2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        editTextB2.setText("_____");
//        layoutByCode2.addView (editTextB2);
//
//        TextView textViewB3 = new TextView(this);
//        textViewB3.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,1));
//        textViewB3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
//        textViewB3.setText(" shoes.");
//        layoutByCode2.addView(textViewB3); //add them to this temporary layout.
//
//
//        rootLayout.addView(layoutByCode2);
    }
    public void initBtnsOptions(){
        btn_RSLT=findViewById(R.id.btn_RSLT);
        btn_check=findViewById(R.id.btn_check);

        btn_opt1=findViewById(R.id.btn_opt1);
        btn_opt2=findViewById(R.id.btn_opt2);
        btn_opt3=findViewById(R.id.btn_opt3);
        btn_opt4=findViewById(R.id.btn_opt4);
        btn_opt5=findViewById(R.id.btn_opt5);
        btn_opt6=findViewById(R.id.btn_opt6);


    }
    public void allButtonsVisible(){
        btn_opt1.setVisibility(View.VISIBLE);
        btn_opt2.setVisibility(View.VISIBLE);
        btn_opt3.setVisibility(View.VISIBLE);
        btn_opt4.setVisibility(View.VISIBLE);
        btn_opt5.setVisibility(View.VISIBLE);
        btn_opt6.setVisibility(View.VISIBLE);
    }
    public void readFileXMLQuery(){
        try {
            arrayData =
                    new XmlParser().parseSectionFromFileToArrayOfHashMaps(
                            currentContext,
                            CategoriesHandler.categoriesDirectories[categoryIndex-1] +
                                    "/LEVEL" + levelIndex + "/LESSON" + lessonIndex + "/TARGETW.xml",
                            "Words",
                            null,
                            null,
                            "word");
        }
        catch (Exception e) {
            Toast.makeText(currentContext,"תקלה בהצגת תרגול" + "\n" + e.getMessage(), Toast.LENGTH_SHORT);
        }

    }

    public void retArrayQuery(){
        ArrayQuery = new ArrayList<String>();
        for (int i =0 ; i<arrayData.size();i++){
            ArrayQuery.add( arrayData.get(i).get("Heb"));
        }
    }
    public void  retArrayAnswer(){
        ArrayAnswer = new ArrayList<String>();
        for (int i =0 ; i<arrayData.size();i++){
            ArrayAnswer.add( arrayData.get(i).get("Eng"));
        }
    }

//    private void initBar(){
//        bar = (NavigationBar) findViewById(R.id.navBar);
//        setup(true, ArrayQuery.size());
//    }

//    private void setup(boolean reset, int count) {
//        if (reset)
//            bar.resetItems();
//        bar.setTabCount(count);
//        bar.animateView(3000);
//        bar.setIndicatorColor(R.color.bluelight);
//        bar.setCurrentPosition(index <= 0 ? 0 : index);
//    }

    public void setTextButtons(){
        int arrayButtons[]=new int[6];

        initArrayRadnom(arrayButtons);
        shuffleArray(arrayButtons);

        btn_opt1.setText(ArrayAnswer.get(arrayButtons[0]));
        btn_opt2.setText(ArrayAnswer.get(arrayButtons[1]));
        btn_opt3.setText(ArrayAnswer.get(arrayButtons[2]));
        btn_opt4.setText(ArrayAnswer.get(arrayButtons[3]));
        btn_opt5.setText(ArrayAnswer.get(arrayButtons[4]));
        btn_opt6.setText(ArrayAnswer.get(arrayButtons[5]));
    }

    public void shuffleArray(int array[]) {
        int currentIndex = array.length, temporaryValue, randomIndex;

        // While there remain elements to shuffle...
        while (0 != currentIndex) {

            // Pick a remaining element...
            randomIndex = (int) Math.floor(Math.random() * currentIndex);
            currentIndex -= 1;

            // And swap it with the current element.
            temporaryValue = array[currentIndex];
            array[currentIndex] = array[randomIndex];
            array[randomIndex] = temporaryValue;
        }

    }

    public void initArrayRadnom(int arrayButtons[]){
        Random r = new Random();
        arrayButtons[0]=index-1;
        int i1;
        for (int i=1;i<arrayButtons.length;i++){
            i1 = r.nextInt(ArrayAnswer.size() ) + 0;
            arrayButtons[i]=i1;
        }
    }
    public void createArrayKeyAndValue(){
        hashTable = new Hashtable<>();
        retArrayQuery();// ArrayQuery
        retArrayAnswer();//ArrayAnswer
        for(int i =0 ;i<ArrayQuery.size();i++){
            hashTable.put(ArrayQuery.get(i), ArrayAnswer.get(i));
        }
    }

    //methods of Dialog
    private void buildDialog(View dialog){
        swipeDismissDialog = new SwipeDismissDialog.Builder(PracticeActivity.this)
                .setView(dialog)
                .build()
                .show();
    }
    private void initComponentsOfDialog(View dialog){
        btnOk=(Button)dialog.findViewById(R.id.btnOk);
        txDialog=(TextView)dialog.findViewById(R.id.txDialog);
        imageView=(ImageView)dialog.findViewById(R.id.imageView);
    }
    public void addDotsIndicator(int position){
        mDots=new TextView[ArrayQuery.size()];
        mDotLayout.removeAllViews();
        for (int i=0; i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDotLayout.addView(mDots[i]);

        }
        if(ArrayQuery.size() > 0){
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
//                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.exercise)
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
        finish();// to remote final Activity that 'learn Word f.g'
        switch (index){
            case 0:
                intent = new Intent(this, LearnWordsActivity.class);
                break;
            case 1:
                intent = new Intent(this, recorder.class);
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

}

