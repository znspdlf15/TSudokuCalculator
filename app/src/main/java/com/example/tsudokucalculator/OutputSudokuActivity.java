package com.example.tsudokucalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Intent;

public class OutputSudokuActivity extends Activity {
    private int row = 9;
    private int column = 9;

    TextView text[][] = new TextView[9][9];

    public class sudoku_item{
        public int number = 0;
        public boolean[] able = {false, true, true, true, true, true, true, true, true, true};
    }

    public int[] dx = { 0, 0, -1, 1 };
    public int[] dy = { -1, 1, 0, 0 };

    public void remove_able(int nowX, int nowY, sudoku_item[][] items){
        items[nowY][nowX].able[items[nowY][nowX].number] = false;

        for ( int d = 0; d < 4; d++ ){
            int targetX = nowX + dx[d];
            int targetY = nowY + dy[d];
            while ( targetX >= 0 && targetX < 9 && targetY >= 0 && targetY < 9 ){
                items[targetY][targetX].able[items[nowY][nowX].number] = false;
                targetX = targetX + dx[d];
                targetY = targetY + dy[d];
            }
        }

        int initX = (nowX / 3) * 3;
        int initY = (nowY / 3) * 3;
        for ( int y = 0; y < 3; y++ ){
            for ( int x = 0; x < 3; x++ ){
                int targetX = initX + x;
                int targetY = initY + y;
                items[targetY][targetX].able[items[nowY][nowX].number] = false;
            }
        }

    }

    public void check_only(int nowX, int nowY, sudoku_item[][] items){
        for ( int i = 1; i <= 9; i++ ){
            int able_count = 0;

            for ( int d = 0; d < 2; d++ ){
                int targetX = nowX + dx[d];
                int targetY = nowY + dy[d];
                while ( targetX >= 0 && targetX < 9 && targetY >= 0 && targetY < 9 ){
                    if ( items[targetY][targetX].number == i ) break;
                    if ( !items[targetY][targetX].able[i] || items[targetY][targetX].number > 0 ){
                        able_count++;
                    }

                    targetX = targetX + dx[d];
                    targetY = targetY + dy[d];
                }
            }
            if ( able_count == 8 ){
                if ( items[nowY][nowX].able[i] && items[nowY][nowX].number == 0 ){
                    items[nowY][nowX].number = i;
                    remove_able(nowX, nowY, items);
                    return;
                }
            }
        }

        for ( int i = 1; i <= 9; i++ ){
            int able_count = 0;

            for ( int d = 2; d < 4; d++ ){
                int targetX = nowX + dx[d];
                int targetY = nowY + dy[d];
                while ( targetX >= 0 && targetX < 9 && targetY >= 0 && targetY < 9 ){
                    if ( items[targetY][targetX].number == i ) break;
                    if ( !items[targetY][targetX].able[i] || items[targetY][targetX].number > 0 ){
                        able_count++;
                    }
                    targetX = targetX + dx[d];
                    targetY = targetY + dy[d];
                }
            }
            if ( able_count == 8 ){
                if ( items[nowY][nowX].able[i] && items[nowY][nowX].number == 0 ){
                    items[nowY][nowX].number = i;
                    remove_able(nowX, nowY, items);
                    return;
                }
            }
        }

        int initX = (nowX / 3) * 3;
        int initY = (nowY / 3) * 3;
        for ( int i = 1; i <= 9; i++ ){
            int able_count = 0;
            for ( int y = 0; y < 3; y++ ) {
                for ( int x = 0; x < 3; x++ ) {
                    int targetX = initX + x;
                    int targetY = initY + y;
                    if ( nowX == targetX && nowY == targetY ) continue;
                    if ( items[targetY][targetX].number == i ) break;
                    if ( !items[targetY][targetX].able[i] || items[targetY][targetX].number > 0 ){
                        able_count++;
                    }
                }
            }
            if ( able_count == 8 ){
                if ( items[nowY][nowX].able[i] && items[nowY][nowX].number == 0 ){
                    items[nowY][nowX].number = i;
                    remove_able(nowX, nowY, items);
                    return;
                }
            }
        }
    }

    public void calculate_sudoku(int[][] sudoku){
        sudoku_item[][] items = new sudoku_item[9][9];
        for ( int y = 0; y < 9; y++ ) {
            for (int x = 0; x < 9; x++) {
                items[y][x] = new sudoku_item();
            }
        }
        System.out.println("initing..");

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                items[y][x].number = sudoku[y][x];
                remove_able(x, y, items);
            }
        }
        while ( true ) {
            int pre_count = 0;
            int next_count = 0;

            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    remove_able(x, y, items);
                    if ( items[y][x].number > 0 ){
                        pre_count++;
                    }
                }
            }
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    check_only(x, y, items);
                }
            }

            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++) {
                    check_item(items[y][x]);

                    if ( items[y][x].number > 0 ){
                        next_count++;
                    }
                }
            }
            System.out.println("check items..");

            if ( pre_count == next_count) break;
        }

        for ( int y = 0; y < 9; y++ ){
            for ( int x = 0; x < 9; x++ ){
                sudoku[y][x] = items[y][x].number;
            }
        }
        System.out.println("submitting..");
    }

    public void check_item(sudoku_item i){
        int count = 0;
        int idx = 0;
        for ( int n = 1; n <= 9; n++ ){
            if ( i.able[n] ) {
                count++;
                idx = n;
            }
        }
        if ( count == 1 ) {
            if ( i.number == 0 ) {
                i.number = idx;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_sudoku);

        Intent intent = getIntent();
        int[] tmp_sudoku = intent.getExtras().getIntArray("text_parameter");
        int[][] sudoku = new int[9][9];

        for (int i = 0; i < tmp_sudoku.length; i++) {
            sudoku[i/9][i%9] = tmp_sudoku[i];
        }

        this.calculate_sudoku(sudoku);

        TableLayout table = findViewById(R.id.output_sudoku_content);
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);
        TableRow row[] = new TableRow[9];

        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int y = 0; y < this.row; y++) {
            row[y] = new TableRow(this);

            for (int x = 0; x < this.column; x++) {
                this.text[y][x] = new Button(this);

                this.text[y][x].setTextSize(15);
                this.text[y][x].setText(String.valueOf(sudoku[y][x]));

                row[y].addView(this.text[y][x]);
            }
            table.addView(row[y], rowLayout);
        }
    }
}

