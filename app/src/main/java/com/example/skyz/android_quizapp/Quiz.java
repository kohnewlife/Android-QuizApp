package com.example.skyz.android_quizapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class Quiz extends AppCompatActivity {

    private TextView mQuestionTextView;
    private TextView mCheatTokenTextView;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false)
    };

    private int mCurrentIndex = 0;
    private int mPrevIndex = 0;
    private int mCorrectAnswers = 0;
    private int mTotalAnswers = 0;
    private int mCheatTokens = 3;

    private Stack<Integer> questionStack = new Stack<>();   // TODO push questions to stack for prev

    private static final String TAG = "Quiz Activity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEX_ANSWERED = "index answered";
    private static final String KEY_INDEX_CORRECT_ANSWERED = "index correct answered";
    private static final String KEY_INDEX_TOTAL_ANSWERED = "index total answered";
    private static final String KEY_INDEX_CHEATED = "index cheated";
    private static final String KEY_INDEX_CHEAT_TOKEN = "index cheat token";
    private static final int REQUEST_CODE_CHEAT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        // load current question (for handling rotation  or other runtime configuration changes)
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            boolean[] questionAnswered = savedInstanceState.getBooleanArray(KEY_INDEX_ANSWERED);
            for (int i = 0; i < questionAnswered.length; i++) {
                mQuestionBank[i].setNotAnswered(questionAnswered[i]);
            }
            boolean[] questionCheated = savedInstanceState.getBooleanArray(KEY_INDEX_CHEATED);
            for (int i = 0; i <questionCheated.length; i++) {
                mQuestionBank[i].setCheated(questionCheated[i]);
            }
            mCorrectAnswers = savedInstanceState.getInt(KEY_INDEX_CORRECT_ANSWERED, 0);
            mTotalAnswers = savedInstanceState.getInt(KEY_INDEX_TOTAL_ANSWERED);
            mCheatTokens = savedInstanceState.getInt(KEY_INDEX_CHEAT_TOKEN);
        }

        // All the views
        mQuestionTextView = findViewById(R.id.question_text_view);
        mCheatTokenTextView = findViewById(R.id.cheat_token_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mCheatButton = findViewById(R.id.cheat_button);
        mPrevButton = findViewById(R.id.prev_button);
        mNextButton = findViewById(R.id.next_button);

        updateQuestion(mCurrentIndex);   // initially update question
        // Set texts
        mCheatTokenTextView.setText(getString(R.string.cheat_token, mCheatTokens));
        
        // The  onClick listeners
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrevIndex = mCurrentIndex;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion(mCurrentIndex);
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mTrueButton.setEnabled(false);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(Quiz.this, isAnswerTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(mPrevIndex);

            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrevIndex = mCurrentIndex;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion(mCurrentIndex);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null)
                return;
//            mDidCheat = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheated(CheatActivity.wasAnswerShown(data));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");

        updateAnswerButtons();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceSate");
        outState.putInt(KEY_INDEX, mCurrentIndex);

        boolean[] questionAnswered = new boolean[mQuestionBank.length];
        for (int i = 0; i < mQuestionBank.length; i++) {
            questionAnswered[i] = mQuestionBank[i].isNotAnswered();
        }
        outState.putBooleanArray(KEY_INDEX_ANSWERED, questionAnswered);
        boolean[] questionCheated = new boolean[mQuestionBank.length];
        for (int i = 0; i < mQuestionBank.length; i++) {
            questionCheated[i] = mQuestionBank[i].isCheated();
        }
        outState.putBooleanArray(KEY_INDEX_CHEATED, questionCheated);
        outState.putInt(KEY_INDEX_CORRECT_ANSWERED, mCorrectAnswers);
        outState.putInt(KEY_INDEX_TOTAL_ANSWERED, mTotalAnswers);
        outState.putInt(KEY_INDEX_CHEAT_TOKEN, mCheatTokens);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    // Update question
    private void updateQuestion(int index) {
        mCurrentIndex = index;
        mQuestionTextView.setText(mQuestionBank[index].getTextResId());
        updateAnswerButtons();
    }
    // Checking the user's answer
    private void checkAnswer(boolean userPressedTrue) {
        mTotalAnswers++;
        Question question = mQuestionBank[mCurrentIndex];
        // disable buttons
        question.setNotAnswered(false);
        updateAnswerButtons();
        boolean answerIsTrue = question.isAnswerTrue();

        int messageResId;
        if (question.isCheated()) {
            Toast.makeText(this, R.string.judgement_toast, Toast.LENGTH_SHORT).show();
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mCorrectAnswers++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        }
        if (mTotalAnswers == mQuestionBank.length) {
            Toast.makeText(this, "You answered " + mCorrectAnswers + " correct" +
                    " answers," + " out of " + mTotalAnswers, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAnswerButtons() {    // disable, enable buttons
        mTrueButton.setEnabled(mQuestionBank[mCurrentIndex].isNotAnswered());
        mFalseButton.setEnabled(mQuestionBank[mCurrentIndex].isNotAnswered());
    }
}
