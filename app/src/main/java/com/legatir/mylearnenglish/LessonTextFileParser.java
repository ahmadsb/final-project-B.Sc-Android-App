package com.legatir.mylearnenglish;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lital on 14/10/2017.
 */

public class LessonTextFileParser {

    String textFromFile = "";
    ArrayList<String> wordsToFill = new ArrayList<String>();

    void parseLessonFile(Context context, String fullPathToTextFile) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(
                            CategoriesHandler.categoriesDirectories[0]+ "LEVEL2/LESSON1/Text.lsn")));

            // do reading, usually loop until end of file reading
            String textLine;
            while ((textLine = reader.readLine()) != null) {
                //process line
                textFromFile += textLine;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }
}
