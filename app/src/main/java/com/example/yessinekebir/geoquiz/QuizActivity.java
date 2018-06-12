package com.example.yessinekebir.geoquiz;

import android.media.Image;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Test";  //static final --> indica una costante che ha tutti gli oggetti della stessa classe
    private static final String KEY_INDEX = "key_index";

    private Button btnTrue;
    private Button btnFalse;
    private Button nextBtn;
    private Button prevBtn;
     //private ImageButton prevBtn;  --> Se un icona fare cosi


    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");  // Serve per debuggare un "consoleLog" sul pannello Logcat .. Utilizzando il tag "TAG" , che ha come stringa Test
        setContentView(R.layout.activity_main);
        // Se nel dizionario Bundle trovo un valore con null ,
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        btnTrue = findViewById(R.id.button_true);
        btnFalse = findViewById(R.id.button_false);
        nextBtn = findViewById(R.id.next_button);
        prevBtn = findViewById(R.id.prev_button);
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();



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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

// SALVATAGGIO DEI DATI NELLA FASE PRIMA DI ANDARE IN MODALITà LANDSCAPE DEL CELL
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        //QUINDI CI SALVIAMO L'INDICE DELLA DOMANDA , IN MODO TALE CHE QUANDO VA IN LANDSCAPE STA ALLA STESSA DOMANDA (CHE C'ERA IN PORTRAIT) E NON TORNA ALLA PRIMA
        savedInstanceState.putInt(KEY_INDEX, this.mCurrentIndex);
    }

    //Bundle è un "dizionario" che permette di salvare oggetti abbastanza primitivi (stringhe,interi,float,e array semplici) è formato da --> Chiave : Valore  --> Stringa : ...

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_true:
                checkAnswer(true);
                break;
            case R.id.button_false:
                checkAnswer(false);
                break;

            case R.id.next_button:
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                break;

            case R.id.prev_button:
                if (mCurrentIndex>0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                } else {
                    Toast.makeText(this, R.string.error_outOfIndex, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }
}
