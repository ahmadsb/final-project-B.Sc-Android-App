package com.legatir.mylearnenglish;

import java.util.ArrayList;

/**
 * Created by lital on 09/09/2017.
 */

public interface SpeechRecognitionResultsListener {

    public void onRecognizedResults(ArrayList<String> results);
}
