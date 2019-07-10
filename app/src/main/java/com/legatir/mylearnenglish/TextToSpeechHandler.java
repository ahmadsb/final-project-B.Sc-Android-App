package com.legatir.mylearnenglish;

/**
 * Created by lital on 08/09/2017.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Locale;
import android.widget.Toast;


public class TextToSpeechHandler {

    static private TextToSpeech tts;
    static ContextWrapper context = null;

    static public void initialize(ContextWrapper context) {

        TextToSpeechHandler.context = context;

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    //int result = tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.5f);

                    int result = tts.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        //Log.e("error", "This Language is not supported");
                        Toast.makeText(TextToSpeechHandler.context, "Language not supported!", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(TextToSpeechHandler.context, "Initialization failed!", Toast.LENGTH_SHORT);
                }
            }
        } );

    }

    static public void setSpeechRate(final float rate) {
        tts.setSpeechRate(rate);
    }

    static public void speak(final String  text) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
    }
}



