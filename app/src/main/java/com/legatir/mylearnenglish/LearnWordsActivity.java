package com.legatir.mylearnenglish;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LearnWordsActivity extends AppCompatActivity {
    Button start,pause,stop;
    Button selectBtn=null;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    CircleMenu circleMenu;

    ViewPager viewPager=null;
    SliderAdapter mSliderAdapter;
    TextView []mDots;
    LinearLayout mDotLayout;
    Button mNextBtn=null;
    Button mBackBtn=null;
    int mCurrentPage = 0;
    int mOldPage = 0;

    Activity currentContext = this;


    int categoryIndex = -1;
    String categoryTitle = null;
    String titleLesson =null;
    int lessonIndex;

    int levelIndex = -1;
    String lessonContentTitle;

    String levelTitle = null;

    ArrayList<HashMap<String,String>> wordsAndTheirData = null;
    ArrayList<HashMap<String,String>> uniqueWordsAndTheirData = null;

    Set<String> uniqueEnglishWordsSet = null;

    ArrayList<String> uniqueEnglishWordsArrayList = null;
    ArrayList<String> uniqueHebrewWordsArrayList = null;

    LearnWordsListAdapter adapter= null;
    ListView wordsListView = null;

    TextView txtTitle = null;
    TextView txtSubTitle = null;
    TextView txtEnglishWord = null;
    TextView txtPartOfSpeech = null;
    TextView txtHebrewWord = null;
    TextView txtEnglishWordInHebrewLetters = null;
    android.support.v7.widget.Toolbar toolbar;

    Button btnNextWord = null;
    Button btnPreviousWord = null;
    int wordsIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_words);

        TextToSpeechHandler.initialize(currentContext);
        initToolBar("Learn Word");
        initCircleMenu();
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
//        mCurrentPage= sharedPref.getInt("currentIndex",0);




        // boom
//         bmb = (BoomMenuButton) findViewById(R.id.bmb);


//        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
//            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
//                    .listener(new OnBMClickListener() {
//                        @Override
//                        public void onBoomButtonClick(int index) {
//                            // When the boom-button corresponding this builder is clicked.
//                            Toast.makeText(currentContext, "Clicked " + index, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            bmb.addBuilder(builder);
//        }

/*
* CATEGORY_INDEX*  CATEGORY_TITLE*  LEVEL_INDEX* *  * LESSON_TITLE * LESSON_INDEX*/
        getAllDataOFLesson();
        System.out.println("eeeeee"+categoryIndex);

     /*read file xml */
        String pathFile=CategoriesHandler.categoriesDirectories[categoryIndex-1] +
                "/LEVEL" + levelIndex + "/LESSON" + lessonIndex + "/TARGETW.xml";
        System.out.println("pathhhhhhh"+pathFile);
        readFileXml(pathFile);//updata  ArrayList<HashMap<String,String>> wordsAndTheirData

 /*uniqueEnglishWordsSet = new HashSet<String>();
   uniqueWordsAndTheirData = new ArrayList<HashMap<String, String>>();
   uniqueEnglishWordsArrayList = new ArrayList<String>();*/
        setArrayLists();

        System.out.println("teeeeees"+uniqueEnglishWordsArrayList);


        viewPager=(ViewPager)findViewById(R.id.viewPager);
        mSliderAdapter=new SliderAdapter(this,uniqueEnglishWordsArrayList,uniqueHebrewWordsArrayList,uniqueEnglishWordsArrayList.size());
        mDotLayout=(LinearLayout)findViewById(R.id.mdotLayout);
        viewPager.setAdapter(mSliderAdapter);

        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

        mNextBtn=(Button)findViewById(R.id.nextBtn);
        mBackBtn=(Button)findViewById(R.id.backBtn);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mCurrentPage<=uniqueEnglishWordsArrayList.size()){
                    addDotsIndicator(mCurrentPage);
                    viewPager.setCurrentItem(mCurrentPage+1);
                    mSliderAdapter=new SliderAdapter(currentContext,uniqueEnglishWordsArrayList,uniqueHebrewWordsArrayList,uniqueEnglishWordsArrayList.size());

                }
                else{
                    mCurrentPage=uniqueEnglishWordsArrayList.size()-1;
                }




            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addDotsIndicator(mCurrentPage);
                    viewPager.setCurrentItem(mCurrentPage-1);
                    mSliderAdapter=new SliderAdapter(currentContext,uniqueEnglishWordsArrayList,uniqueHebrewWordsArrayList,uniqueEnglishWordsArrayList.size());








            }
        });
        //        txtTitle = (TextView) findViewById(R.id.lessonFirstPageTxtTitle);
//        txtContent = (TextView) findViewById(R.id.lessonFirstPageTxtContent);
//        btnLearnWords =  (Button) findViewById(R.id.btnLearnWords);
//


//        txtTitle = (TextView)findViewById(R.id.txtTitle);
//        txtTitle.setText(title);
//
//        txtSubTitle = (TextView)findViewById(R.id.txtSubTitle);
//        txtSubTitle.setText("לימוד מילים");
//




//        wordsListView =  (ListView) findViewById(R.id.wordsLView);
//
//
//
//
//        adapter = new LearnWordsListAdapter(this, uniqueEnglishWordsArrayList);
        // Assign adapter to ListView
//        wordsListView.setAdapter(adapter);

//
//        txtEnglishWord = (TextView) findViewById(R.id.englishWord);
//        txtPartOfSpeech = (TextView) findViewById(R.id.partOfSpeech);
//        txtHebrewWord = (TextView) findViewById(R.id.hebrewWord);
//        txtEnglishWordInHebrewLetters = (TextView) findViewById(R.id.englishWordInHebrew);
//        btnNextWord = (Button) findViewById((R.id.btnNextWord));
//        btnPreviousWord = (Button) findViewById((R.id.btnPreviousWord));
//        TextToSpeechHandler.speak(uniqueWordsAndTheirData.get(0).get("Eng"));
//
//        txtEnglishWord.setText(uniqueWordsAndTheirData.get(0).get("Eng"));
//        txtPartOfSpeech.setText(uniqueWordsAndTheirData.get(0).get("PartOfSpeechHeb"));
//        txtHebrewWord.setText(uniqueWordsAndTheirData.get(0).get("Heb"));
//        txtEnglishWordInHebrewLetters.setText(uniqueWordsAndTheirData.get(0).get("EngInHeb"));
//
//        btnNextWord.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                if (wordsIndex < uniqueWordsAndTheirData.size()-1) {
//                    wordsIndex++;
//                }
//
//                TextToSpeechHandler.speak(uniqueWordsAndTheirData.get(wordsIndex).get("Eng"));
//
//                txtEnglishWord.setText(uniqueWordsAndTheirData.get(wordsIndex).get("Eng"));
//                txtPartOfSpeech.setText(uniqueWordsAndTheirData.get(wordsIndex).get("PartOfSpeechHeb"));
//                txtHebrewWord.setText(uniqueWordsAndTheirData.get(wordsIndex).get("Heb"));
//                txtEnglishWordInHebrewLetters.setText(uniqueWordsAndTheirData.get(wordsIndex).get("EngInHeb"));
//
//
//                 wordsListView.smoothScrollToPosition(
//                         (wordsIndex+2<uniqueWordsAndTheirData.size()-1)?wordsIndex+2:uniqueWordsAndTheirData.size()-1 );
////                //adapter.notifyDataSetChanged();
//                //View previousHighlightedRowView = wordsListView.getChildAt(wordsIndex - wordsListView.getFirstVisiblePosition()-1);
//                View previousHighlightedRowView = getViewByPosition(wordsListView,wordsIndex-1);
//                previousHighlightedRowView.setBackgroundColor(Color.WHITE);
//                //View rowView = wordsListView.getChildAt(wordsIndex - wordsListView.getFirstVisiblePosition());
//                //View rowView = wordsListView.getAdapter().getView(wordsIndex, null, wordsListView);
//
//                View rowView = getViewByPosition(wordsListView,wordsIndex);
//
//                rowView.setBackgroundColor(Color.YELLOW);
//
//
//
//            }
//        });
//
//        btnPreviousWord.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //TextToSpeechHandler.speak("OK");
//
//                if (wordsIndex > 0) {
//                    wordsIndex--;
//                }
//
//                TextToSpeechHandler.speak(uniqueWordsAndTheirData.get(wordsIndex).get("Eng"));
//
//                txtEnglishWord.setText(uniqueWordsAndTheirData.get(wordsIndex).get("Eng"));
//                txtPartOfSpeech.setText(uniqueWordsAndTheirData.get(wordsIndex).get("PartOfSpeechHeb"));
//                txtHebrewWord.setText(uniqueWordsAndTheirData.get(wordsIndex).get("Heb"));
//                txtEnglishWordInHebrewLetters.setText(uniqueWordsAndTheirData.get(wordsIndex).get("EngInHeb"));
//
//
//                for(int i=0; i < wordsListView.getChildCount();i++) {
//                    View rowView = wordsListView.getChildAt(i);
//                    rowView.setBackgroundColor(Color.WHITE);
//                }
//
//                wordsListView.smoothScrollToPosition(
//                        (wordsIndex+2<uniqueWordsAndTheirData.size()-1)?wordsIndex+2:uniqueWordsAndTheirData.size()-1 );
//                //adapter.notifyDataSetChanged();
//                //View previousHighlightedRowView = wordsListView.getChildAt(wordsIndex+1);
//                View previousHighlightedRowView = getViewByPosition(wordsListView,wordsIndex+1);
//                previousHighlightedRowView.setBackgroundColor(Color.WHITE);
//                //View rowView = wordsListView.getChildAt(wordsIndex);
//                View rowView = getViewByPosition(wordsListView,wordsIndex);
//                rowView.setBackgroundColor(Color.YELLOW);
//
//
//
//
//            }
//        });

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
        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }
    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            System.out.println(position+"aaaa");
//

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPage=position;
            System.out.println("assda"+position);
            addDotsIndicator(mCurrentPage);

            if(position ==0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("");



            }else if(position == mDots.length - 1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("FINISH");
                mBackBtn.setText("Back");

            }else{
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);



                mNextBtn.setText("Next");
                mBackBtn.setText("Back");

            }
            System.out.println(mSliderAdapter.word+"asd");



        }

        @Override
        public void onPageScrollStateChanged(int state) {

            System.out.println(state +"statess");
        }
    };

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
        uniqueHebrewWordsArrayList = new ArrayList<>();
        for ( HashMap<String,String> wordAndItsData : wordsAndTheirData ) {
            if ( !uniqueEnglishWordsSet.contains(wordAndItsData.get("Eng")) ) {

                uniqueEnglishWordsSet.add(wordAndItsData.get("Eng"));
                uniqueWordsAndTheirData.add(wordAndItsData);
                uniqueEnglishWordsArrayList.add(wordAndItsData.get("Eng"));
            }
            if ( !uniqueHebrewWordsArrayList.contains(wordAndItsData.get("Heb")) ) {

                uniqueHebrewWordsArrayList.add(wordAndItsData.get("Heb"));
            }

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            if(mCurrentPage == 0){

            }else{
                sheredPerf(mCurrentPage);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //    public View getViewByPosition(ListView listView, int pos) {
//        final int firstListItemPosition = listView.getFirstVisiblePosition();
//        final int lastListItemPosition = firstListItemPosition
//                + listView.getChildCount() - 1;
//
//        if (pos < firstListItemPosition || pos > lastListItemPosition) {
//            return listView.getAdapter().getView(pos, null, listView);
//        } else {
//            final int childIndex = pos - firstListItemPosition;
//            return listView.getChildAt(childIndex);
//        }
//    }
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


    private void initCircleMenu(){
        final boolean[] flagCheck = {false};
        final int[] indexSelceted = {-1};
        circleMenu=(CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.ic_track_changes_black_24dp,R.drawable.ic_remove_black_24dp)
//                .addSubMenu(Color.parseColor("#72CAAF"),R.drawable.learnword)
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
        finish();
        switch (index){
            case 0:
                intent = new Intent(this, PracticeActivity.class);
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

    protected void sheredPerf(int index){
        editor.putInt("currentIndex", index);
        editor.apply();
    }
}
