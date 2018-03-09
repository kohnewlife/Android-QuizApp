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
    private Button mNextButton;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        int question = mQuestionBank[mCurrentIndex].getTextResId();

        mQuestionTextView.setText(question);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Quiz.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Quiz.this,
                        R.string.incorrect_toast,
                        Toast.LENGTH_LONG).show();
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
            }
        });
    }

    private void updateQuestion
}
