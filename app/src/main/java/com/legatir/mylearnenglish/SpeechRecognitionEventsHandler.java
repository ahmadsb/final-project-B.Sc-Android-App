package com.legatir.mylearnenglish;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lital on 09/09/2017.
 */

public class SpeechRecognitionEventsHandler implements RecognitionListener  {

    static private String LOG_TAG = "SpeechRecoEvntHandler";

    static public ArrayList<String> matches = new ArrayList<>();

    static private SpeechRecognitionResultsListener resultsListener = null;

    //    private SpeechRecognizer speech = null;
    private ContextWrapper currentContext = null;

    //deny private CTOR
    private SpeechRecognitionEventsHandler() {

    }


    public SpeechRecognitionEventsHandler(ContextWrapper currentContext) {
        this.currentContext = currentContext;
    }


    static public void setListenerForResult(SpeechRecognitionResultsListener listener) {
        resultsListener = listener;
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        Toast.makeText(currentContext,
                "שמעתי התחלת דיבור" , Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
        Toast.makeText(currentContext,
                "שמעתי דיבור" , Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");

        Toast.makeText(currentContext,
                "הדיבור ששמעתי הסתיים" , Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);

        Toast.makeText(currentContext,
                " :בעיה בזיהוי דיבור" + "\n" + errorMessage, Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");

        Toast.makeText(currentContext,
                "זיהוי דיבור חלקי", Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
        Toast.makeText(currentContext,
                "מוכנה לזיהוי דיבור", Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        resultsListener.onRecognizedResults(matches);

        String text = "";
        for (String result : matches)
            text += result + "\n";

        Toast.makeText(currentContext,
                "שמעתי: " + "\n" + text, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                //message = "Audio recording error";
                message = "בעיה בהקלטת דיבור";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                //message = "Client side error";
                message = "בעיה בחיבור לאינטרנט";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                //message = "Insufficient permissions";
                message = "אין לאפליקציה הרשאות לזיהוי דיבור";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                //message = "Network error";
                message = "בעיה בחיבור לאינטרנט";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                //message = "Network timeout";
                message = "בעיה בחיבור לאינטרנט";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                //message = "No match";
                message = "לא הבנתי את הדיבור שלך";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                //message = "RecognitionService busy";
                message = "שרת זיהוי דיבור עסוק";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                //message = "error from server";
                message = "שרת זיהוי דיבור החזיר שגיאה";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                //message = "No speech input";
                message = "לא שמעתי דיבור!";
                break;
            default:
                //message = "Didn't understand, please try again.";
                message = "לא הצלחתי להבין מה אמרת";
                break;
        }
        return message;
    }


}
