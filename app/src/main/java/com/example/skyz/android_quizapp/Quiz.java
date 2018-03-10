package com.example.skyz.android_quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {

    private TextView mQuestionTextView;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false)
    };

    private int mCurrentIndex = 0;
    private int mPrevIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // All the views
        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mPrevButton = findViewById(R.id.prev_button);
        mNextButton = findViewById(R.id.next_button);

        updateQuestion(mCurrentIndex);   // initially update question
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
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
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

    // Update question
    private void updateQuestion(int index) {
        mQuestionTextView.setText(mQuestionBank[index].getTextResId());
    }
    // Checking the user's answer
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
