package com.example.skyz.android_quizapp;

/**
 * Created by Skyz on 3/9/2018.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mNotAnswered = true;
    private boolean mIsCheated = false;

    public Question(int textRestId, boolean answerTrue) {
        mTextResId = textRestId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isNotAnswered() {
        return mNotAnswered;
    }

    public void setNotAnswered(boolean alreadyAnswered) {
        mNotAnswered = alreadyAnswered;
    }

    public boolean isCheated() {
        return mIsCheated;
    }

    public void setCheated(boolean cheated) {
        mIsCheated = cheated;
    }
}
