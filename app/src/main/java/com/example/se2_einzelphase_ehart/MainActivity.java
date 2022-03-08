package com.example.se2_einzelphase_ehart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Button btn_send;
    public EditText input_txt;
    public TextView srvroutput_txt;
    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send = findViewById(R.id.btn_send);
        input_txt = findViewById(R.id.input_txt);
        srvroutput_txt = findViewById(R.id.srvroutput_txt);
        //add eventlistener to send_button
        btn_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){taskSendButton();}
        });
    }

    private void taskSendButton(){

    }
}