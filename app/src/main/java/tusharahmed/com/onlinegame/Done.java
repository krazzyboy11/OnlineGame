package tusharahmed.com.onlinegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tusharahmed.com.onlinegame.Common.Common;
import tusharahmed.com.onlinegame.Model.QuestionScore;

public class Done extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtResultScore,getTxtResultQuestions;
    ProgressBar mProgressBar;

    FirebaseDatabase database;
    DatabaseReference question_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database= FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");
        txtResultScore = (TextView) findViewById(R.id.txtTotalScore);
        getTxtResultQuestions = (TextView) findViewById(R.id.txtTotalQuestion);
        mProgressBar = (ProgressBar)findViewById(R.id.doneProgressBar);
        btnTryAgain = (Button)findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Done.this,Home.class);
                startActivity(intent);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");
            txtResultScore.setText(String.format("SCORE : %d",score));
            getTxtResultQuestions.setText(String.format("PASSED : %d / %d",correctAnswer,totalQuestion));
            mProgressBar.setMax(totalQuestion);
            mProgressBar.setProgress(correctAnswer);
            question_score.child(String.format("%s_%s", Common.currentUser.getUserName(),
                    Common.categoryId)).setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getUserName(),
                    Common.categoryId),Common.currentUser.getUserName(),
                    String.valueOf(score)));
        }
    }
}
