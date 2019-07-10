package com.legatir.mylearnenglish.chooseLevel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.legatir.mylearnenglish.MoreInfo;
import com.legatir.mylearnenglish.R;
import com.legatir.mylearnenglish.TextToSpeechHandler;
import com.legatir.mylearnenglish.exam;

public class ChooseCategoryActivity extends AppCompatActivity {
    Button start,pause,stop;
    int mCategoryIndex= 0;
    VideoView videoView;
    MediaController mediaC;
    Activity currentContext = this;
    TextView moveText=null;
    ImageView buttonCategoryBeginners = null;
    ImageView buttonCategoryStudents = null;
    ImageView buttonCategoryUniversity = null;
    ImageView buttonCategoryInfo = null;
    de.hdodenhof.circleimageview.CircleImageView profile=null;
    //MediaPlayer mediaPlayer = null;
    SwipeDismissDialog swipeDismissDialog;
    Button btnOk;
    int saidLessonNumber = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        videoView=(VideoView)findViewById(R.id.simpleVideoView);
        mediaC=new MediaController(this);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoplay();
            }
        });
        TextToSpeechHandler.initialize(currentContext);

        moveText();// method moves the text of textView

//        List<String> lessonTitles = null;

//        try {
//            lessonTitles = new XmlParser().parseSectionFromFileToArray(
//                    this, "lessons/XML/XMLLessons.xml", "Levels","LevelHeb","Level1","lesson");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        buttonCategoryBeginners =  (ImageView) findViewById(R.id.button_category_beginners);
        buttonCategoryStudents =  (ImageView) findViewById(R.id.button_category_students);
        buttonCategoryUniversity =  (ImageView) findViewById(R.id.button_category_university);
        buttonCategoryInfo =  (ImageView) findViewById(R.id.button_category_Info);
        profile =  (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.id_profile);


        buttonCategoryBeginners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(currentContext, R.anim.hyperspace_jump));
                gotoChooseLevelActivity(1, "Beginner");
            }
        });

        buttonCategoryStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(currentContext, R.anim.hyperspace_jump));
                gotoChooseLevelActivity(2, "Students");
            }
        });

        buttonCategoryUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(currentContext, R.anim.hyperspace_jump));
                gotoChooseLevelActivity(3, "Acadimic");
            }
        });
        buttonCategoryInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(currentContext, R.anim.hyperspace_jump));

                startActivity( new Intent(currentContext, MoreInfo.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_profile,null);
                //method  init dialog ' init '
                buildDialog(dialog);
                // method  init the components of file xml od massage dialog
                initComponentsOfDialog(dialog);


            }
        });

    }
    public void gotoChooseLevelActivity( int categoryIndex , String categName) {

//        TextToSpeechHandler.speak("Category " + categName);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            //e.printStackTrace();
//        }

        Intent intent = new Intent(currentContext, ChooseLessonActivity.class);
        intent.putExtra("CATEGORY_TITLE", "Category " + categoryIndex);
        intent.putExtra("CATEGORY_INDEX", categoryIndex);
        intent.putExtra("LEVEL_TITLE", "Level " + 1);
        intent.putExtra("LEVEL_INDEX", 1);

        startActivity(intent);
    }

    public void videoplay(){
        String video_path="android.resource://"+getPackageName()+"/"+R.raw.video;
        System.out.println("pathtttt"+video_path);
        Uri uri=Uri.parse(video_path);
        videoView.setBackgroundResource(0);
        videoView.setVideoURI(uri);

        mediaC.setMediaPlayer(this.videoView);

        videoView.setMediaController(mediaC);
        mediaC.setAnchorView(videoView);

        videoView.start();
    }

    private void moveText(){
        moveText = (TextView) this.findViewById(R.id.moveText);

        String first = "Please, feel right at home . Make yourself ";
        String next = "<font color='#EE0000'>comfortable!</font>";
        moveText.setText(Html.fromHtml(first + next));
        moveText.setSelected(true);  // Set focus to the textview
        moveText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }
    private void buildDialog(View dialog){
        swipeDismissDialog = new SwipeDismissDialog.Builder(this)
                .setView(dialog)
                .build()
                .show();
    }
    private void initComponentsOfDialog(View dialog){

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        videoView.setBackgroundResource(R.drawable.imagevideo);

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
