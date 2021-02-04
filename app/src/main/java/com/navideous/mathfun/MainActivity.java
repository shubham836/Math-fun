package com.navideous.mathfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    TextView sumTextView, resultTextView;
    TextView scoreTextView;
    CardView goButton;
    TextView button0;
    TextView button1;
    TextView button2;
    TextView button3;
    int locationOfCorrectAnswer;
    int score;
    int numberOfQuestions;
    int rightAnswer;
    TextView timerTextView;
    Button playAgainButton;
    ConstraintLayout gameLayout;
    ConstraintLayout resultLayout;
    TextView finalResultTextView;
    int previousRandom = 0;

    ArrayList<Integer> answers = new ArrayList<>();

    public void chooseAnswer(View view) {

        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            resultTextView.setText("Correct :)");
            score++;
        } else {
            resultTextView.setText("Wrong :(");
        }
        numberOfQuestions++;
        scoreTextView.setText(score + "/" + numberOfQuestions);
        newQuestion();
    }


    public void playAgain(View view) {
        score = 0;
        gameLayout.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.INVISIBLE);
        numberOfQuestions = 0;
        timerTextView.setText("60s");
        scoreTextView.setText(score + "/" + numberOfQuestions);
        resultTextView.setText("");
        newQuestion();

        new CountDownTimer(60100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                if (millisUntilFinished < 11000) {
                    timerTextView.setTextColor(Color.RED);
                } else {
                    timerTextView.setTextColor(Color.parseColor("#FFBB33"));
                }
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                resultLayout.setVisibility(View.VISIBLE);
                gameLayout.setVisibility(View.INVISIBLE);
                finalResultTextView.setText("Your score is " + (score + "/" + numberOfQuestions));
            }
        }.start();
    }

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        playAgain(findViewById(R.id.timerTextView));
        gameLayout.setVisibility(View.VISIBLE);
    }


    public void newQuestion() {
        Random random = new Random();

        /**
         * a gets random value in range of 1 to 21. Since 1 is added 1 and 21 is inclusive.
         * b gets random value in range of 0 to 20 since 21 is exclusive.
         */

        int a = random.nextInt(21) + 1;
        int b = random.nextInt(21);

        /**
         * mathOperation generates random in range of 1 to 3. Both the numbers are inclusive.
         * 1 is responsible for performing addition between two numbers.
         * 2 is responsible for performing subtraction.
         * 3 is responsible for performing multiplication.
         */

        int mathOperation = (int) Math.random() * (3) + 1;

        optionsToChoose(mathOperation, a, b);

        answers.clear();

        locationOfCorrectAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (locationOfCorrectAnswer == i) {
                answers.add(rightAnswer);
            } else {
                int wrongRandom = random.nextInt(10) + 1;
                int wrongRandomCloseAnswer = random.nextInt(2);
                int wrongAnswer = rightAnswer;
                if (wrongRandomCloseAnswer == 0) {
                    wrongAnswer += wrongRandom;
                } else {
                    wrongAnswer -= wrongRandom;
                }

                for (int answer : answers) {
                    if (wrongAnswer == answer) {
                        wrongAnswer = wrongAnswer + wrongRandom + 10;
                    }
                }

                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    /**
     * Sets the question in activity_main.xml based on the math
     * operation selected. Also calls mathOperationSelected for
     * generating solution of that equation and manages the position
     * of first and second value in equation for subtraction to always
     * get positive value as an answer.
     *
     * @param optionSelected an integer responsible for math operation to be performed.
     * @param a              an integer which is a first or second value in math equation.
     * @param b              an integer which is a first or second value in math equation.
     */

    private void optionsToChoose(int optionSelected, int a, int b) {

        if (optionSelected == 1) {

            sumTextView.setText(a + " + " + b);
            rightAnswer = mathOperationSelected(optionSelected, a, b);
        } else if (optionSelected == 2) {

            if (b > a) {
                int temp = a;
                a = b;
                b = temp;
            }

            sumTextView.setText(a + " - " + b);
            rightAnswer = mathOperationSelected(optionSelected, a, b);

        } else if (optionSelected == 3) {

            sumTextView.setText(a + " * " + b);
            rightAnswer = mathOperationSelected(optionSelected, a, b);

        }

    }


    /**
     * Function for solving math operations based on the operation selected
     * and numbers provided.
     *
     * @param operationSelected
     * @param a
     * @param b
     * @return an integer value which is a solution of the operation performed.
     */

    private int mathOperationSelected(int operationSelected, int a, int b) {
        int result = 0;
        switch (operationSelected) {
            case 1:
                result = a + b;
                break;
            case 2:
                result = a - b;
                break;
            case 3:
                result = a * b;
                break;
        }
        return result;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultTextView);
        timerTextView = findViewById(R.id.timerTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        finalResultTextView = findViewById(R.id.finalResultTextView);
        sumTextView = findViewById(R.id.sumTextView);
        goButton = findViewById(R.id.goButton);
        gameLayout = findViewById(R.id.gameLayout);
        resultLayout = findViewById(R.id.resultLayout);
        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
        resultLayout.setVisibility(View.INVISIBLE);
    }
}