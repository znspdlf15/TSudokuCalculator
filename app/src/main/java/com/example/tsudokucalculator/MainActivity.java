package com.example.tsudokucalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//    static {
//        System.loadLibrary("opencv");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button camera_mode_bt = findViewById(R.id.camera_mode_button);
        Button input_mode_bt = findViewById(R.id.input_mode_button);

        input_mode_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputSudokuActivity.class);
                startActivity(intent);
            }
        });

        camera_mode_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputCameraSudokuActivity.class);
                startActivity(intent);
            }
        });

    }
}
