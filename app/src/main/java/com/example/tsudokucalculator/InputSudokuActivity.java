package com.example.tsudokucalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

public class InputSudokuActivity extends Activity implements View.OnClickListener {
    private int row = 9;
    private int column = 9;
    TextView text[][];
    int sudoku[][] = new int[9][9];
    public int chooseX = 0;
    public int chooseY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sudoku);

        TableLayout table = findViewById(R.id.sudoku_content);
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TableRow row[] = new TableRow[9];
        this.text = new TextView[this.row][this.column]; // 데이터

        for (int y = 0; y < this.row; y++) {
            row[y] = new TableRow(this);

            for (int x = 0; x < this.column; x++) {
                this.text[y][x] = new Button(this);

                this.text[y][x].setTextSize(15);
                this.text[y][x].setId(y * 9 + x);
                this.text[y][x].setOnClickListener(this);

                this.sudoku[y][x] = 0;
                row[y].addView(this.text[y][x]);
            }
            table.addView(row[y], rowLayout);
        }

        findViewById(R.id.input1).setOnClickListener(this);
        findViewById(R.id.input2).setOnClickListener(this);
        findViewById(R.id.input3).setOnClickListener(this);
        findViewById(R.id.input4).setOnClickListener(this);
        findViewById(R.id.input5).setOnClickListener(this);
        findViewById(R.id.input6).setOnClickListener(this);
        findViewById(R.id.input7).setOnClickListener(this);
        findViewById(R.id.input8).setOnClickListener(this);
        findViewById(R.id.input9).setOnClickListener(this);
        findViewById(R.id.input_complete).setOnClickListener(this);
        findViewById(R.id.input_clear).setOnClickListener(this);
        findViewById(R.id.input_reset).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch ( id ){
            case R.id.input1:
                this.text[this.chooseY][this.chooseX].setText("1");
                this.sudoku[this.chooseY][this.chooseX] = 1;
                break;
            case R.id.input2:
                this.text[this.chooseY][this.chooseX].setText("2");
                this.sudoku[this.chooseY][this.chooseX] = 2;
                break;
            case R.id.input3:
                this.text[this.chooseY][this.chooseX].setText("3");
                this.sudoku[this.chooseY][this.chooseX] = 3;
                break;
            case R.id.input4:
                this.text[this.chooseY][this.chooseX].setText("4");
                this.sudoku[this.chooseY][this.chooseX] = 4;
                break;
            case R.id.input5:
                this.text[this.chooseY][this.chooseX].setText("5");
                this.sudoku[this.chooseY][this.chooseX] = 5;
                break;
            case R.id.input6:
                this.text[this.chooseY][this.chooseX].setText("6");
                this.sudoku[this.chooseY][this.chooseX] = 6;
                break;
            case R.id.input7:
                this.text[this.chooseY][this.chooseX].setText("7");
                this.sudoku[this.chooseY][this.chooseX] = 7;
                break;
            case R.id.input8:
                this.text[this.chooseY][this.chooseX].setText("8");
                this.sudoku[this.chooseY][this.chooseX] = 8;
                break;
            case R.id.input9:
                this.text[this.chooseY][this.chooseX].setText("9");
                this.sudoku[this.chooseY][this.chooseX] = 9;
                break;
            case R.id.input_complete:
                Intent intent = new Intent(InputSudokuActivity.this, OutputSudokuActivity.class);
                int[] aa = new int[81];

                for ( int y = 0; y < 9; y++ ){
                    for ( int x = 0; x < 9; x++ ){
                        aa[y*9 + x] = sudoku[y][x];
                    }
                }
                intent.putExtra("text_parameter", aa);
                startActivityForResult(intent, 1);

                break;
            case R.id.input_clear:
                this.text[this.chooseY][this.chooseX].setText("");
                this.sudoku[this.chooseY][this.chooseX] = 0;
                break;
            case R.id.input_reset:
                for ( int y = 0; y < this.row; y++ ){
                    for ( int x = 0; x < this.column; x++ ){
                        this.text[y][x].setText("");

                        this.sudoku[y][x] = 0;
                    }
                }
                break;

            default:
                int y = id / 9;
                int x = id % 9;

                this.chooseX = x;
                this.chooseY = y;
                break;
        }

    }
}
