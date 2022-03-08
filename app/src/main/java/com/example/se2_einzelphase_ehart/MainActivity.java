package com.example.se2_einzelphase_ehart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public Button btn_send;
    public Button btn_calc;
    public EditText input_txt;
    public TextView srvroutput_txt;
    private String input;
    private String output;
    private TextView calcoutput_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send = findViewById(R.id.btn_send);
        input_txt = findViewById(R.id.input_txt);
        srvroutput_txt = findViewById(R.id.srvroutput_txt);
        btn_calc = findViewById(R.id.btn_calc);
        calcoutput_txt = findViewById(R.id.clc_output);
        //add eventlistener to send_button
        btn_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){taskSendButton();}
        });
        //add eventlistener to calc_button
        btn_calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                taskCalcButton();
            }
        });
    }

    private void taskSendButton(){
        // creation of seperate thread, because functionality should be executed asnyc
        new Thread() {
            public void run(){
                String sending = input_txt.getText().toString();
                try{
                    Socket clientSocket = new Socket("se2-isys.aau.at", 53212);
                    //output, den socket ausliefern soll --> output to server
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    // input der in socket geschrieben wird --> in from server
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outToServer.writeBytes(sending+"\n");
                    String srveroutput = inFromServer.readLine();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            srvroutput_txt.setText(srveroutput);
                        }
                    });
                    clientSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void taskCalcButton(){
        // 12021905 % 7 = 0 --> implemented task 0
        input = input_txt.getText().toString();
        int counter = 0;
        Integer alternating_checksum = 0;
        String evenOdd = "odd";
        if(input.length() > 0){
           String[] digits = input.split("");
           for(String s : digits){
               Integer d = Integer.parseInt(s);
               if(counter > 0 && counter % 2 ==1){
                   alternating_checksum -= d;
               }
               else if(counter > 0 && counter % 2 == 0){
                   alternating_checksum +=d;
               }else{
                   alternating_checksum = d;
               }
               counter++;
           }
            if(alternating_checksum % 2 == 0){
                evenOdd = "even";
            }
            calcoutput_txt.setText("alternating checksum of your matrikelnumber is "+ alternating_checksum.toString() + ". This is an  "+evenOdd + " number.");
        }
    }
}