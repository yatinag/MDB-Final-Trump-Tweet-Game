package com.example.sp20finalassessment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.example.sp20finalassessment.Utils.getRandomPhrase;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView phraseTextView;
    TextView accuracyTextView;
    ImageView doneImageView;
    Chronometer timer;
    private static Context appContext;
    EditText typedEditText;

    String phrase;
    String[] splitPhrase;
    boolean gameStarted = false;
    String typedString;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        appContext = getApplicationContext();

        // Uncomment this out once you connect your database
        firebaseDatabase = FirebaseDatabase.getInstance();
        phraseTextView = (TextView) findViewById(R.id.phraseTextView);
        accuracyTextView = (TextView) findViewById(R.id.wpmTextView);
        doneImageView = (ImageView) findViewById(R.id.doneImageView);
        timer = (Chronometer) findViewById(R.id.timer);
        typedEditText = (EditText) findViewById(R.id.typedEditText);

        doneImageView.setOnClickListener(this);

        /* TODO Part 4
         * call getRandomPhrase in a new thread. Use the result of this to set the phrase variable,
         * set the phraseTextView to that phrase, and set splitPhrase to phrase.split("\\s")
         */
        try {
//            System.out.println("BOOM");
//            ExecutorService service = Executors.newFixedThreadPool(1);
//            Future<String> boom = service.submit((Callable<String>) getRandomPhrase());
            phrase = getRandomPhrase();

            System.out.println(phrase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //phraseTextView.setText(phrase);austin
        phraseTextView.setText("LOL Check sout to see correct api call, still have to make it async");
        splitPhrase = phrase.split("\\s");

        typedEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        typedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!gameStarted) {
                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    gameStarted = true;
                }
                typedString = s.toString();
                accuracyTextView.setText(getAccuracyPercentage() + "%");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doneImageView:
                clickDone();
                break;
        }
    }

    public void clickDone() {
        timer.stop();
        int accuracyPercentage = getAccuracyPercentage();
        int wpm = getWPM();
        accuracyTextView.setText(accuracyPercentage + "% with " + wpm + " WPM");

        /* TODO Part 6
         * Add a game to the database. Keep in mind that we also want to know which users are
         * attached to which games, so we should be changing two nodes of the database here, "games"
         * and "users". We know how to generate random keys for adding games, but how can we add
         * that game to our user's node? We can't generate a random key, because we want it to go
         * to the same node every time for a given user.
         */
        typedEditText.setEnabled(false);
        doneImageView.setClickable(false);
    }

    private int getAccuracyPercentage() {
        String[] typedStringWords = typedString.split("\\s");
        double correctCount = 0;
        for (int i = 0; i < typedStringWords.length; i++) {
            if (i < splitPhrase.length && typedStringWords[i].equals(splitPhrase[i])) {
                correctCount++;
            }
        }
        return (int) (100 * correctCount / typedStringWords.length);
    }

    private int getWPM() {
        double minutes = ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000.0) / 60.0;
        return (int) (typedString.split("\\s").length / minutes);
    }

    public static Context getAppContext() {
        return appContext;
    }
}
