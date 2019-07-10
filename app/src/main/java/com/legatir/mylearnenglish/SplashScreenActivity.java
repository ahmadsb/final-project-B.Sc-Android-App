package com.legatir.mylearnenglish;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.legatir.mylearnenglish.chooseLevel.ChooseCategoryActivity;

public class SplashScreenActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try{

            mediaPlayer = MediaPlayer.create(this, R.raw.intro);
            mediaPlayer.start();

        }catch(Exception e){
            e.printStackTrace();
        }


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent( SplashScreenActivity.this, ChooseCategoryActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                SplashScreenActivity.this.finish();


            }
        }, SPLASH_DISPLAY_LENGTH);


    }

}

