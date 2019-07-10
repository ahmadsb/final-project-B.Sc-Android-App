package com.legatir.mylearnenglish;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class exam extends AppCompatActivity {

    ImageView imageViewTest;
    int categoryIndex = -1;
    String categoryTitle = null;
    int levelIndex = -1;
    int lessonIndex;
    TextView tv_exam;
    LinearLayout layout_table, layout_table2, layout_table3;
    String text;
    List<String> table;
    String [] rootTable;
    String[] list;
    ArrayList<String> listClicked;
    Button btn_check ;
    HashMap<TextView, Integer> hmButtons;
    android.support.v7.widget.Toolbar toolbar;
    CircleMenu circleMenu;
    String lessonContentTitle;

    int levelTitle = -1;
    // dialog
    SwipeDismissDialog swipeDismissDialog;
    Button btnOk;
    TextView txDialog;
    ImageView imageView;
    Button btn_image_left, btn_image_right;
    int mCurrentIndexImage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        imageViewTest = findViewById(R.id.test_imageView);
        listClicked=new ArrayList<>();
        tv_exam = findViewById(R.id.tv_exam);
        layout_table = findViewById(R.id.layout_table);
        layout_table2 = findViewById(R.id.layout_table2);
        layout_table3 = findViewById(R.id.layout_table3);
        btn_check = findViewById(R.id.btn_check);
        initToolBar("Exam");
        initCircleMenu();
        btn_image_left=(Button)findViewById(R.id.btn_img_left);
        btn_image_right=(Button)findViewById(R.id.btn_img_right);


        categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        categoryTitle = getIntent().getStringExtra("CATEGORY_TITLE");

        levelIndex = getIntent().getIntExtra("LEVEL_INDEX", 0);
        levelTitle = getIntent().getIntExtra("LEVEL_TITLE", 0);
        hmButtons = new HashMap<TextView, Integer>();

        lessonContentTitle = getIntent().getStringExtra("LESSON_TITLE");
        lessonIndex = getIntent().getIntExtra("LESSON_INDEX", 0);
        //Using this API, we can open the asset
        AssetManager assetManager = getAssets();
        InputStream is = null;
        String[] imgPath = new String[0];
        try {
            //here we get string array of file names within pic directory
            String path = CategoriesHandler.categoriesDirectories[categoryIndex - 1]
                    + CategoriesHandler.levelDirectories[levelIndex - 1]
                    + "LESSON" + lessonIndex + "/pic";
            String pathText = CategoriesHandler.categoriesDirectories[categoryIndex - 1]
                    + CategoriesHandler.levelDirectories[levelIndex - 1]
                    + "LESSON" + lessonIndex + "/Text.lsn";
//            StringBuilder text=ReadFile(pathText);
            text = ReadFile(pathText);
            text = text.trim();// remove the right and left space from string
            text =  filterText(text.toLowerCase());
            list = split(text);

            // the word to put in the space of paragraph. 'table'
            table = getTable(list);
            rootTable = new String[table.size()];
            for (int i=0;i<table.size();i++){
                rootTable[i]=table.get(i);
            }
            // shuffle for elements in table
            long seed = System.nanoTime();
            Collections.shuffle(table, new Random(seed));

            createItemOFTable(table);
            // set the text with "____" space
            text = setAnswersOfText(list);
            tv_exam.setText(text);


            imgPath = assetManager.list(path);
            System.out.println("ahmadahmadahmad" + imgPath.length);
            if(mCurrentIndexImage <= imgPath.length-1 && mCurrentIndexImage>=0){

                is = assetManager.open(path + "/"+imgPath[mCurrentIndexImage] );
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageViewTest.setImageBitmap(bitmap);
            }
            else{
                Toast.makeText(this,"you can't to change dirction",Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_image_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setImageExam( "L");
            }
        });
        btn_image_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setImageExam( "R");
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkText()){
                    View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.message_dialog,null);
                    //method  init dialog ' init '
                    buildDialog(dialog);
                    // method  init the components of file xml od massage dialog
                    initComponentsOfDialog(dialog);
                    String [] listOfAnswers=getListAnswers();
                    System.out.println("xxxxx"+checkAnswers(listOfAnswers));
                    System.out.println("xxxxxroottable"+rootTable.toString());
                    System.out.println("xxxxx"+listOfAnswers.toString());
                    if( checkAnswers(listOfAnswers) )  {
                        imageView.setImageResource(R.drawable.correct_answer);
                        txDialog.setText("מצוין , התשובה הנכונה היא "  );
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.not_correct_answer);
                        txDialog.setText("לא נכון , התשובה הנכונה היא "  );
                    }

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeDismissDialog.dismiss();
                        }

                    });
                }
            }
        });

    }

    public String ReadFile(String pathText) {
        InputStream inputStream = null;
        String text = "";
        try {
            inputStream = getAssets().open(pathText);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String[] split(String text) {
        text = text.trim();// remove the right and left space from string
        String[] listString = text.split(" ");
        return listString;
    }

    public List<String> getTable(String[] list) {
        List<String> table = new ArrayList<String>();
        for (String s : list) {
            if (s.contains("#"))
                table.add(s.substring(2, s.length()));
        }
        table=tableWithOutSimilarValue(table);
        return table;
    }

    public String setAnswersOfText(String[] list) {
        for (int i = 0; i < list.length; i++)
            if (list[i].contains("#"))
                list[i] = "______";
        return TextUtils.join(" ", list);
    }

    public void createItemOFTable(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            TextView myButton = new TextView(this);
            myButton.setText(list.get(i));
            myButton.setTextColor(Color.WHITE);
            myButton.setGravity(Gravity.CENTER);
            myButton.setPadding(10,10,10,10);
            myButton.setOnClickListener(mThisButtonListener);
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);


//            if (i < 5) {
            hmButtons.put(myButton,1);
            lp.setMargins(40, 0, 40, 0);
            layout_table.addView(myButton, lp);
//            } else if (i > 5 && i <= 10) {
//                hmButtons.put(myButton,2);
//                layout_table2.addView(myButton, lp);
//            } else {
//                hmButtons.put(myButton,3);
//                layout_table3.addView(myButton, lp);
//
//            }
        }

    }

    private View.OnClickListener mThisButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            v.setEnabled(false);
            v.setBackgroundResource(R.drawable.strike_through);
            TextView b = (TextView) v;
            setTextExam(b.getText().toString());
            b.setTextColor(Color.GRAY);
            listClicked.add(b.getText().toString());

        }
    };
    public void setTextExam(String item) {


        text = text.replaceFirst("______", "[" + item + "]");

        // make api to textview if found word click on word
        tv_exam.setMovementMethod(LinkMovementMethod.getInstance());
        tv_exam.setText(addClickablePart(text), TextView.BufferType.SPANNABLE);
    }
    private SpannableStringBuilder addClickablePart( String str) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        int idx1 = str.indexOf("[");
        int idx2 = 0;
        while (idx1 != -1) {
            idx2 = str.indexOf("]", idx1) + 1;

            final String clickString = str.substring(idx1, idx2);
            final int finalIdx = idx1;
            final int fina2Idx = idx2;
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
//                    flagChang=true;
                    int tempIndex=returnIndexLayout(clickString);
                    System.out.println("indexLayout"+tempIndex);
                    createItemsOFTable(clickString,tempIndex);
                    text=removeWordFromText(tv_exam.getText().toString(),clickString,finalIdx,fina2Idx);
                    tv_exam.setMovementMethod(LinkMovementMethod.getInstance());
                    tv_exam.setText(addClickablePart(text), TextView.BufferType.SPANNABLE);
                }
            }, idx1, idx2, 0);
            idx1 = str.indexOf("[", idx2);
        }

        return ssb;
    }
    private void createItemsOFTable(String word,int indexLayout){
        LinearLayout ll = layout_table;
        LinearLayout l2 = layout_table2;
        LinearLayout l3 = layout_table3;
        final int childCount = ll.getChildCount();
        final int childCount2 = l2.getChildCount();
        final int childCount3 = l3.getChildCount();
        switch (indexLayout)
        {
            case 1:
                checkFound(childCount, ll, word);
                break;
            case 2:
                checkFound(childCount2, l2, word);
                break;
            case 3:
                checkFound(childCount3, l3, word);
                break;
        }

    }
    private void checkFound(int childCount , LinearLayout l,String word){
        word=word.substring(1,word.length()-1);
        for (int i = 0; i < childCount; i++)
        {
            View v = l.getChildAt(i);
            TextView b=(TextView)v;
            int resourceID =b.getResources().getIdentifier("strike_through","drawable",exam.this.getPackageName());


            if( resourceID!=0 && b.getText().toString().equals(word) ){
                v.setEnabled(true);
                v.setBackgroundResource(0);
                v.setOnClickListener(mThisButtonListener);
                b.setText(word);
                b.setTextColor(Color.WHITE);
                break;
            }

        }
    }
    private String removeWordFromText(String text ,String word,int firstInd,int finalInd){
        String newText="";
        String temp1=text,temp2=text,temp3=text;
        temp1=temp1.substring(0, firstInd-1);// ...
        temp2=temp2.substring(firstInd,finalInd+1);// [...]

        temp3=temp3.substring(finalInd+1,temp3.length());// ...
        temp2 = temp2.replace( word, "______");
        newText=temp1+temp2+temp3;
        return newText;
    }
    private int returnIndexLayout(String clickString){
        clickString=clickString.substring(1,clickString.length()-1);
        // get all's buttons in array list
        ArrayList<TextView> listButtons=new ArrayList<>();
        for ( TextView key : hmButtons.keySet() ) {
            listButtons.add( key);
        }

        for (int i=0;i< hmButtons.size();i++){

            if(listButtons.get(i).getText().equals(clickString)  ){
                return  hmButtons.get(listButtons.get(i));//return value(indexLayout) of key(button)
//                hmButtons.remove(listButtons.get(i));
            }
        }
        return -1;
    }
    private List<String>  tableWithOutSimilarValue(List<String> table) {
        for (int i = 0; i < table.size(); i++) {
            for (int j = i + 1; j < table.size(); j++) {
                if (table.get(i).equals(table.get(j))) {
                    table.set(i, table.get(i) + " ");
                    break;
                }
            }
        }

        return table;
    }
    //method's dialog
    private Boolean checkText(){
        if(text.contains("______"))
            return false;
        return true;
    }
    private void buildDialog(View dialog){
        swipeDismissDialog = new SwipeDismissDialog.Builder(exam.this)
                .setView(dialog)
                .build()
                .show();
    }
    private void initComponentsOfDialog(View dialog){
        btnOk=(Button)dialog.findViewById(R.id.btnOk);
        txDialog=(TextView)dialog.findViewById(R.id.txDialog);
        imageView=(ImageView)dialog.findViewById(R.id.imageView);
    }
    private String[] getListAnswers(){
        String [] listAnswers = new String[table.size()];
        String temp=text;
        int i=0;
        while (temp.contains("[") || temp.contains("]")) {
            int idx1 = temp.indexOf("[");
            int idx2 = temp.indexOf("]", idx1) + 1;
            String answer = temp.substring(idx1, idx2);
            answer=answer.substring(1,answer.length()-1);
            listAnswers[i]=answer;
            temp=temp.substring(idx2,temp.length());
            i++;

        }
        return listAnswers;
    }
    private Boolean checkAnswers(String[] listAnswer){
        for (int i=0;i<listAnswer.length;i++){
            System.out.println("xxxxx"+listAnswer[i].toString()+"=="+rootTable[i].toString());
            if(listAnswer[i].toString().compareTo(rootTable[i].toString())!=0){
                return false;
            }
        }
        return true;
    }
    private String filterText(String temp){
        int l;
        for(l =0 ; l<temp.length() ; l++)
        {

            if(temp.charAt(l)>'a' && temp.charAt(l)<'z' ||  temp.charAt(l)=='(')
                break;
        }
        return  text.substring(l);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
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

    private void setImageExam(String dir){
        AssetManager assetManager = getAssets();
        InputStream is = null;
        String[] imgPath = new String[0];

        //here we get string array of file names within pic directory
        String path = CategoriesHandler.categoriesDirectories[categoryIndex - 1]
                + CategoriesHandler.levelDirectories[levelIndex - 1]
                + "LESSON" + lessonIndex + "/pic";





        try {
            imgPath = assetManager.list(path);
            System.out.println("ahmadahmadahmad" + imgPath.length);
            System.out.println("ahmadahmadahmad" + mCurrentIndexImage);
            if(dir.compareTo("R")==0){
                mCurrentIndexImage++;

            }
            else {
                mCurrentIndexImage--;
            }

            if(mCurrentIndexImage > imgPath.length)
                mCurrentIndexImage=imgPath.length-1;
            if(mCurrentIndexImage < 0){
                mCurrentIndexImage =0;
            }
            if(mCurrentIndexImage>=0 && mCurrentIndexImage <= imgPath.length-1 ){
                is = assetManager.open(path + "/"+imgPath[mCurrentIndexImage] );
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageViewTest.setImageBitmap(bitmap);
            }
            else{
                Toast.makeText(this,"you can't to change dirction",Toast.LENGTH_SHORT).show();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initCircleMenu(){
        final boolean[] flagCheck = {false};
        final int[] indexSelceted = {-1};
        circleMenu=(CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.ic_track_changes_black_24dp,R.drawable.ic_remove_black_24dp)
                .addSubMenu(Color.parseColor("#72CAAF"),R.drawable.learnword)
                .addSubMenu(Color.parseColor("#258CFF"),R.drawable.exercise)
                .addSubMenu(Color.parseColor("#CEA1A7"),R.drawable.sound)
                .addSubMenu(Color.parseColor("#ACB7D0"),R.drawable.morequery)
//                .addSubMenu(Color.parseColor("#EFC43F"),R.drawable.exam)
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
                intent = new Intent(this, AdditionalQuestionsActivity.class);
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

