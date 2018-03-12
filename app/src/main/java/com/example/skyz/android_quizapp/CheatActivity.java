package com.example.skyz.android_quizapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    private boolean mAnswerIsTrue;
    private boolean mDidCheat = false;

    private static final String KEY_INDEX_DID_CHEAT = "index did cheat";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.skyz.android_quizapp.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.skyz.android_quizapp.answer_shown";

    public static Intent newIntent(Context packageContent, boolean answerIsTrue) {
        Intent intent = new Intent(packageContent, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    // Decoding the result intent
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null) {
            mDidCheat = savedInstanceState.getBoolean(KEY_INDEX_DID_CHEAT, false);
        }
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mDidCheat = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, mDidCheat);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_INDEX_DID_CHEAT, mDidCheat);
    }
}
