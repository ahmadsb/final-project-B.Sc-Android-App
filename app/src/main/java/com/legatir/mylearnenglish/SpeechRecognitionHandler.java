package com.legatir.mylearnenglish;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lital on 09/09/2017.
 */

public class SpeechRecognitionHandler {

    private String LOG_TAG = "SpeechRecogHandler";

    static private SpeechRecognizer speech = null;
    static private Intent recognizerIntent;

    static private ContextWrapper currentContext = null;

    static public void initialize(ContextWrapper context) {

        currentContext = context;

        speech = SpeechRecognizer.createSpeechRecognizer(context);
        speech.setRecognitionListener(new SpeechRecognitionEventsHandler(currentContext));
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                context.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


    }

    static public void startListening() {
        speech.startListening(recognizerIntent);

        Toast.makeText(currentContext,
                "מקשיבה...", Toast.LENGTH_SHORT)
                .show();

    }


    static public void stopListening() {
        speech.stopListening();

        Toast.makeText(currentContext,
                "לא מקשיבה", Toast.LENGTH_SHORT)
                .show();

    }


    static protected void destroy() {
        if (speech != null) {
            speech.destroy();
            Toast.makeText(currentContext,
                    "לא מקשיבה יותר!", Toast.LENGTH_SHORT)
                    .show();
        }

    }


}

