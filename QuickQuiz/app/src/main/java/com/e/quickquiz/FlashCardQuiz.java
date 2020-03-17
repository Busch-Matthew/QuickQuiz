package com.e.quickquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FlashCardQuiz extends AppCompatActivity implements GestureDetector.OnGestureListener {
    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 500;
    public static final String ANSWER_TITLE = "Answer";
    public static final String QUESTION_TITLE = "Question";
    TextView questionAnswerTextView;
    TextView cardNumberTextView;
    TextView cardTitleTextView;
    Button btnCorrect;
    Button btnIncorrect;
    int currentCardNumber;
    int numberOfCards;
    CardSet currentCardSet;
    GestureDetector gestureDetector = new GestureDetector(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_quiz);


        Intent intent = getIntent();
        currentCardSet = (CardSet) intent.getSerializableExtra("current card set");

        cardTitleTextView = (TextView) findViewById(R.id.quiz_question_answer_title_TextView);
        questionAnswerTextView = (TextView) findViewById(R.id.question_answer_TextView);
        cardNumberTextView = (TextView) findViewById(R.id.card_number_TextView);
        btnCorrect = (Button) findViewById(R.id.quiz_btn_correct);
        btnIncorrect = (Button) findViewById(R.id.quiz_btn_incorrect);

        btnCorrect.setVisibility(View.INVISIBLE);
        btnIncorrect.setVisibility(View.INVISIBLE);

        currentCardNumber = 1;
        numberOfCards = currentCardSet.cards.size();

        update_text(currentCardSet);





    }

    public void show_answer() {
        cardTitleTextView.setText(ANSWER_TITLE);
        questionAnswerTextView.setText(currentCardSet.cards.get(currentCardNumber - 1).getCardAnswer());
        btnCorrect.setVisibility(View.VISIBLE);
        btnIncorrect.setVisibility(View.VISIBLE);
    }

    public void show_question() {
        cardTitleTextView.setText(QUESTION_TITLE);
        questionAnswerTextView.setText(currentCardSet.cards.get(currentCardNumber - 1).getCardQuestion());
        btnCorrect.setVisibility(View.INVISIBLE);
        btnIncorrect.setVisibility(View.INVISIBLE);
    }

    public void update_text(CardSet currentCardSet) {
        cardTitleTextView.setText(QUESTION_TITLE);
        questionAnswerTextView.setText(currentCardSet.cards.get(currentCardNumber - 1).getCardQuestion());
        cardNumberTextView.setText(currentCardNumber + "/" + numberOfCards);
        btnCorrect.setVisibility(View.INVISIBLE);
        btnIncorrect.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    @Override
    public boolean onDown(MotionEvent e) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (questionAnswerTextView.getText().equals(currentCardSet.cards.get(currentCardNumber - 1).cardQuestion)) {
            show_answer();
        }
        else {
            show_question();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        /**
         * the gesture may not be completely in the X or Y direction
         * so we need to figure out which axis had the greatest movement
         * in order to determine the general direction of the gesture
         */
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();

        // Which axis had a greater movement?
        if (Math.abs(diffX) > Math.abs(diffY)) {
            //right or left swipe
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                }
                else {
                    onSwipeLeft();
                }
                result = true;
            }
        }
        else {
            //up or down swipe
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeDown();
                }
                else {
                    onSwipeUp();
                }
                result = true;
            }
        }

        return result;
    }

    private void onSwipeUp() {
        Toast.makeText(FlashCardQuiz.this,"Swipe up", Toast.LENGTH_SHORT);
        questionAnswerTextView.setText("God Damn it!");
    }

    private void onSwipeDown() {
        finish();
    }

    private void onSwipeLeft() {
        if (currentCardNumber < currentCardSet.cards.size()) {
            currentCardNumber++;
            update_text(currentCardSet);
        }

    }

    private void onSwipeRight() {
        if (currentCardNumber - 1 > 0) {
            currentCardNumber--;
            update_text(currentCardSet);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
