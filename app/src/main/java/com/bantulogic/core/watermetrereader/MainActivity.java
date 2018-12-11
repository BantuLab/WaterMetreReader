package com.bantulogic.core.watermetrereader;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Annotation;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView mtvLastRedDigit;
    private TextView mtvMidRedDigit;
    private TextView mtvFirstRedDigit;
    private TextView mtvBlackMetreReading;

    final Handler h = new Handler();
    private Animation animMoveUp;
    private long lastRedDigitcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtvLastRedDigit = findViewById(R.id.tvLastRedDigit);
        mtvMidRedDigit = findViewById(R.id.tvMidRedDigit);
        mtvFirstRedDigit = findViewById(R.id.tvFirstDigit);
        mtvBlackMetreReading = findViewById(R.id.tvBlackMetreReading);

        animMoveUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);

        mtvLastRedDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mtvLastRedDigit.startAnimation(animMoveUp);

            }
        });

        runStartupMetreReader();
    }

    public int login(String username, String password){
        boolean validCredentials = false;
        //qu
        return validCredentials ? 0 : 1;
    }

    public void runStartupMetreReader(){
        h.post(new Runnable() {
            @Override
            public void run() {
                if(lastRedDigitcount==10){
                    lastRedDigitcount=0;
                    if (mtvMidRedDigit.getText().toString() == "9"){
                        mtvMidRedDigit.setText(String.valueOf(lastRedDigitcount));
                        if (mtvFirstRedDigit.getText().toString() == "9"){
                            mtvFirstRedDigit.setText("1");
                        }else {
                            mtvFirstRedDigit.setText(String.valueOf(Integer.parseInt(mtvFirstRedDigit.getText().toString())+1));
                        }
                    }else {
                        mtvMidRedDigit.setText(String.valueOf(Integer.parseInt(mtvMidRedDigit.getText().toString())+1));
                    }
                }
                mtvLastRedDigit.setText(String.valueOf(lastRedDigitcount));
                h.postDelayed(this, 4000);
                lastRedDigitcount++;
            }
        });
    }
}
