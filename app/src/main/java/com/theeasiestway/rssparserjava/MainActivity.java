package com.theeasiestway.rssparserjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.theeasiestway.enities.RssChannel;
import com.theeasiestway.parser.RssParser;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "RssParserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        TextView textView = findViewById(R.id.textView);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        new Thread(() -> {
            try {
                RssParser rssParser = new RssParser();
                //  RssChannel rssChannel = rssParser.parse("https://www.techrepublic.com/rssfeeds/downloads/");
                //  RssChannel rssChannel = rssParser.parse("https://www.techrepublic.com/rssfeeds/downloads/", "<li>|</li>|<ul>|</ul>|<p>|</p>|<br>", "");
                  RssChannel rssChannel = rssParser.parse("http://www.aif.ru/rss/all.php");
                //  RssChannel rssChannel = rssParser.parse("http://www.aif.ru/rss/all.php", "<li>|</li>|<ul>|</ul>|<p>|</p>|<br>", "");
                //  RssChannel rssChannel = rssParser.parse("https://www.vesti.ru/section.rss?cid=8");
                //  RssChannel rssChannel = rssParser.parse("https://www.vesti.ru/section.rss?cid=8", "<li>|</li>|<ul>|</ul>|<p>|</p>|<br>", "");
                //  RssChannel rssChannel = rssParser.parse("");
                String rssString = rssChannel == null ? null : rssChannel.toString();
                if (rssString != null) runOnUiThread(() -> textView.setText(rssString));
                else runOnUiThread(() -> textView.setText("rssString is null"));
                printString(rssString);
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> textView.setText("Error while parsing rss: " + e));
            }
        }).start();
    }

    private void printString(String string) {
        if (string == null) Log.d(TAG, "rssString is null");
        else if (string.trim().length() <= 999) Log.d(TAG, string);
        else {
            int strLength = string.length();
            int loopCount = strLength / 1000;
            int pos = 0;
            for (int i = 0; i < loopCount + 1; i++) {
                String subString = i + 1 == loopCount + 1 ? string.substring(pos, strLength) : string.substring(pos, pos + 1000);
                Log.d(TAG, subString);
                pos += 1000;
            }
        }
    }
}