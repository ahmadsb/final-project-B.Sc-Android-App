package com.legatir.mylearnenglish;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MoreInfo extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    TextView content_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        initToolBar("More Infromation");
        content_text=(TextView)findViewById(R.id.content_text);
        String str=" ללמוד אנגלית ללא מורה  היא אפליקצית מחשב שמאפשרת ללמוד אנגלית באמצעות המחשב הנייד.  \n" +
                "במסגרת פרוייקט זה אני מפתחת אפליקציית אנדרואיד שתאפשרת ללמוד את השפה  הנ''ל בצורה קלה, פשוטה ונוחה על גבי מחשב סלולרי. \n" +
                "הבעיה העיקרית שפרוייקט זה בא לפתור הינה ללמוד אנגלית בצורה זמינה דרך המחשב הסלולרי. בנוסף ניתן לחסוך עלות גבוהה של זיכרון ולממש את האפליקציה בצורה יעילה . רווח נוסף הינו לתת אפשרות ללמוד אנגלית בלי חיבור לאינטרנט.\n" +
                " התוצר הצפוי הינו אפליקציה אנדורויד פשוטה, נוחה וקלה לתפעול ולשימוש אשר מממשת את שיעורי הלימודים , כך הלקוח יוכל ללמוד את השפה ללא חיבור אינטרנט. בנוסף, בעקבות שמירת תשובות הלקוח בבסיס נתונים. \n" +
                "האפליקציה תרוץ על מחשבי סולולרי בעלי מערכת הפעלה אנדרואיד. המטרה היא לתכנן, לפתח ולבנות כלי יציב ופשוט כמה שיותר, אשר מיועד לשימוש של סדטודנים בביתי ספר ואקדמאים, כך שהוא יוכל להתאים לכל סוגי המשתמשים, ללא קשר ליכולתם להתמודד עם מכשיר ממוחשב . \n" +
                " הקו המנחה לאורך תכנון הפרוייקט ובנייתו הינו הפשטות שצריכה להיות לשימוש באפליקציה גם עבור משתמשים חסרי ניסיון במכשירים טכנולוגיים כגון סמארטפונים ועוד. \n" +
                "קו זה בא לידי ביטוי באופן המימוש והתיכון ע\"י כך שמתבצעת חשיבה על המשתמש- מה שגרם להחליט כיצד לממש כל שאלה באופן הכי פשוט שאפשר.\n";
        content_text.setText(str);
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
}
