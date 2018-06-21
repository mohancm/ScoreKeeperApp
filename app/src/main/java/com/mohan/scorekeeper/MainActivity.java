package com.mohan.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIMER_MILLIS = 60000;


    String teamNameA;
    String teamNameB;
    private TextView mTextViewCount;
    private ProgressBar progressBar;
    private Button mFootballButton;
    private Button mButtonReset;

    String mStart = "Start";
    String mPause = "Pause";
    String mReset = "Resetted!";

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIMER_MILLIS;

    int teamScoreA = 0;
    int teamScoreB = 0;
    TextView scoreA;
    TextView scoreB;

    RecyclerView left_action, right_action;
    List<ActionsAdapter.Holder> Lholder = new ArrayList<>();
    List<ActionsAdapter.Holder> Rholder = new ArrayList<>();
    ActionsAdapter Ladapter = new ActionsAdapter(Lholder, this);
    ActionsAdapter Radapter = new ActionsAdapter(Rholder, this);
    ActionsAdapter.Holder left_holder;
    ActionsAdapter.Holder right_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initaiting Team score

        scoreA = findViewById(R.id.score_teamA);
        scoreB = findViewById(R.id.score_teamB);
// STRINGS
        teamNameA = "Chelsea";
        teamNameB = "Monaco";

        mTextViewCount = findViewById(R.id.timer_tv);
        progressBar = findViewById(R.id.progress);
        mFootballButton = findViewById(R.id.football_button);
        mButtonReset = findViewById(R.id.reset_button);
        progressBar.setProgress((int) mTimeLeftInMillis);
        TextView dateView = findViewById(R.id.dateView);

        // set date View
        left_action = findViewById(R.id.l_s);
        right_action = findViewById(R.id.r_s);

        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        dateView.setText(date);

// setting onClickListener to timer
        mFootballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

    }

    private void scoreObserver() {
        if (teamScoreA >= 60) {
            teamScoreA = 60;
            scoreofTeamA(60);
        }
        if (teamScoreB >= 60) {
            teamScoreA = 60;
            displayForTeamB(60);
        }
    }

    // using gif library to show dailog after winning
    private void winner(String teamName, int score) {
        mCountDownTimer.cancel();
        new FancyGifDialog.Builder(this)
                .setTitle("Congratulations " + teamName)
                .setMessage(teamName + "won by " + score)
                .isCancellable(true)
                .setGifResource(R.drawable.trophy)
                .setPositiveBtnText("Ok")
                .setNegativeBtnText("Play Again")
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        startActivity(new Intent(MainActivity.this, SplashActivity.class));
                        resetTimer();
                    }
                })
                .build();
    }

    public void addLeftAction(int type) {
        switch (type) {
            case 0:
                left_holder = new ActionsAdapter.Holder("Goal", R.drawable.football);
                Lholder.add(left_holder);
                left_action.setAdapter(Ladapter);
                left_action.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 1:
                left_holder = new ActionsAdapter.Holder("Yellow Card", R.drawable.ic_yellowcard);
                Lholder.add(left_holder);
                left_action.setAdapter(Ladapter);
                left_action.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 2:
                left_holder = new ActionsAdapter.Holder("Red Card", R.drawable.ic_redcard);
                Lholder.add(left_holder);
                left_action.setAdapter(Ladapter);
                left_action.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        if (Lholder.size() > 2) {
            Lholder.remove(0);
        }
    }

    public void addRightAction(int type) {
        switch (type) {
            case 0:
                right_holder = new ActionsAdapter.Holder("Goal", R.drawable.football);
                Rholder.add(right_holder);
                right_action.setAdapter(Radapter);
                right_action.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 1:
                right_holder = new ActionsAdapter.Holder("Yellow Card", R.drawable.ic_yellowcard);
                Rholder.add(right_holder);
                right_action.setAdapter(Radapter);
                right_action.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 2:
                right_holder = new ActionsAdapter.Holder("Red Card", R.drawable.ic_redcard);
                Rholder.add(right_holder);
                right_action.setAdapter(Radapter);
                right_action.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        if (Rholder.size() > 2) {
            Rholder.remove(0);
        }
    }

    public void clearActions() {
        Rholder.clear();
        Lholder.clear();
        left_action.setAdapter(Ladapter);
        left_action.setLayoutManager(new LinearLayoutManager(this));
        right_action.setAdapter(Radapter);
        right_action.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void scoreofTeamA(int score) {
        scoreA.setText(String.valueOf(score));
        if (teamScoreA == 60) {
            winner(teamNameA, teamScoreA);
        }
    }

    public void displayForTeamB(int score) {
        scoreB.setText(String.valueOf(score));
        if (teamScoreB == 60) {
            winner(teamNameB, teamScoreB);
        }
    }

    public void GoalA(View view) {
        teamScoreA += 3;
        scoreofTeamA(teamScoreA);
        addLeftAction(0);
    }

    public void GoalB(View view) {
        teamScoreB += 3;
        displayForTeamB(teamScoreB);
        addRightAction(0);
    }

    public void YellowA(View view) {
        teamScoreA += 10;
        scoreofTeamA(teamScoreA);
        addLeftAction(1);
    }

    public void YellowB(View view) {
        teamScoreB += 10;
        displayForTeamB(teamScoreB);
        addRightAction(1);
    }

    public void RedA(View view) {
        teamScoreA += 25;
        scoreofTeamA(teamScoreA);
        addLeftAction(2);
    }

    public void RedB(View view) {
        teamScoreB += 25;
        displayForTeamB(teamScoreB);
        addRightAction(2);
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                scoreObserver();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                Toast.makeText(getApplicationContext(), mStart, Toast.LENGTH_SHORT).show();
            }
        }.start();

        mTimerRunning = true;
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        Toast.makeText(getApplicationContext(), mPause, Toast.LENGTH_LONG).show();
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIMER_MILLIS;
        updateCountDownText();
        Toast.makeText(getApplicationContext(), mReset, Toast.LENGTH_LONG).show();
        resetGame();
        clearActions();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCount.setText(timeLeftFormatted);
    }

    public void resetGame() {
        teamScoreA = 0;
        teamScoreB = 0;
        scoreofTeamA(teamScoreA);
        displayForTeamB(teamScoreB);
    }

}