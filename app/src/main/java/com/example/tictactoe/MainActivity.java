package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean playerXTurn = true;
    private int[][] board = new int[3][3];
    private Button[][] buttons = new Button[3][3];
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.statusTextView);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new ButtonClickListener(i, j));
                board[i][j] = 0;
            }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        int x, y;

        ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View v) {
            if (board[x][y] == 0) {
                board[x][y] = playerXTurn ? 1 : 2;
                ((Button) v).setText(playerXTurn ? "X" : "O");
                if (checkWin()) {
                    statusTextView.setText(playerXTurn ? "Player X wins!" : "Player O wins!");
                    disableButtons();
                } else if (isBoardFull()) {
                    statusTextView.setText("It's a draw!");
                } else {
                    playerXTurn = !playerXTurn;
                    statusTextView.setText(playerXTurn ? "Player X's turn" : "Player O's turn");
                }
            }
        }
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    public void resetGame(View view) {
        playerXTurn = true;
        statusTextView.setText("Player X's turn");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}
