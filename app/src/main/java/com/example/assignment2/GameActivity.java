package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {
    String name1; // name of player 1
    String name2; // name of player 2
    TextView winnerText;
    String player = "X";
    Button btnPlayAgain;

    boolean gameEnded = false;
    int[][] boardState = { // LEGEND: 1 = X, 0 = O, -1 = nothing
            {-1,-1,-1},
            {-1,-1,-1},
            {-1,-1,-1}
    };

    /// Quits the game and returns to the main screen
    public void quitGame(View v) {
        gameEnded = false;
        clearGrid();
        Intent gameIntent = new Intent(this, MainActivity.class);
        startActivity(gameIntent); // begin the game activity
    }
    /// Resets the game board and keeps the user on the game page (replay)
    public void playAgain(View v) {
        gameEnded = false;
        btnPlayAgain.setVisibility(View.GONE);
        clearGrid();
        winnerText.setText("Another round for " + name1 + " vs " + name2);
        winnerText.setTextSize(25);

        // Make the grid clickable before restarting the game
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageInGrid = (ImageView) gridLayout.getChildAt(i);
            imageInGrid.setClickable(true);
        }
    }

    /// Checks and displays the winner
    public void checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            // Check for horizontal win
            if (boardState[i][0] == boardState[i][1] &&
                    boardState[i][1] == boardState[i][2] && boardState[i][0] != -1)
            {
                declareWinner(boardState[i][0]);
                return;
            }
            // Check for vertical win
            if (boardState[0][i] == boardState[1][i] &&
                    boardState[1][i] == boardState[2][i] && boardState[0][i] != -1) {
                declareWinner(boardState[0][i]);
                return;
            }
        }
        // Checks for diagonals
        if (boardState[0][0] == boardState[1][1] &&
                boardState[1][1] == boardState[2][2] && boardState[0][0] != -1) {
            declareWinner(boardState[0][0]);
            return;
        }
        if (boardState[0][2] == boardState[1][1] &&
                boardState[1][1] == boardState[2][0] && boardState[0][2] != -1) {
            declareWinner(boardState[0][2]);
            return;
        }
        // Check for a tie
        boolean tie = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardState[i][j] == -1) {
                    tie = false;
                    break;
                }
            }
        }
        if (tie) {
            winnerText.setText(R.string.it_s_a_tie);
            btnPlayAgain.setVisibility(View.VISIBLE);

            // Make the grid not clickable after a tie
            androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                ImageView imageInGrid = (ImageView) gridLayout.getChildAt(i);
                imageInGrid.setClickable(false);
            }
        }
    }
    private void declareWinner(int playerCode) {
        String winner = (playerCode == 1) ? name1 : name2;
        winnerText.setText("Congrats " + winner + "!");
        gameEnded = true;
        // Make the grid not clickable after a win
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageInGrid = (ImageView) gridLayout.getChildAt(i);
            imageInGrid.setClickable(false);
        }
        btnPlayAgain.setVisibility(View.VISIBLE);
    }

    /// Changes the image of a grid space on user click & changes players
    public void onGridSpaceClick(View v) {
        // Store the clicked view as an ImageView
        ImageView img = findViewById(v.getId());
        img.setClickable(true); // not clickable by default; set to true

        // Getting the id of the clicked ImageView (not the auto-generated id, but
        // the one that we manually gave it)
        String imgFullName = getResources().getResourceName(v.getId());
        String imgName = imgFullName.substring(imgFullName.lastIndexOf("/")+1);
        // Extract row & column from view id (name)
        int clickedRow = Integer.parseInt(""+imgName.charAt(imgName.length() - 2));
        int clickedColumn = Integer.parseInt(""+imgName.charAt(imgName.length() - 1));

        // Make sure there is no image in the ImageView before changing the image
        if (img.getDrawable() == null) {
            // fade-in animation for Xs & Os
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    img.setVisibility(View.VISIBLE);
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            if (Objects.equals(player, "X")) { // set image to current player's symbol
                img.setImageResource(R.drawable.x);
                img.startAnimation(fadeIn);

                boardState[clickedRow][clickedColumn] = 1;

                checkWin();

                player = "O"; // switch players
            } else if (Objects.equals(player, "O")) {
                img.setImageResource(R.drawable.o);
                img.startAnimation(fadeIn);

                boardState[clickedRow][clickedColumn] = 0;

                checkWin();

                player = "X"; // switch players
            }
        } else { // grid space already contains an image
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

            // Animate the X or O if user clicks on it
            img.startAnimation(shake); // the letter shakes
        }
    }

    public void clearGrid() {
        // Clear all ImageViews
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageInGrid = (ImageView) gridLayout.getChildAt(i);
            imageInGrid.setImageResource(0);
        }

        for (int row = 0; row < boardState[0].length; row++) {
            for (int col = 0; col < boardState.length; col++) {
                boardState[row][col] = -1;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // *** setContextView MUST COME BEFORE ANY findViewById ***
        setContentView(R.layout.activity_game);

        Intent intent = getIntent(); // get the intent made in MainActivity
        name1 = intent.getStringExtra("Name1"); // get data from intent
        name2 = intent.getStringExtra("Name2");

        winnerText = findViewById(R.id.lblWinner);
        String versus = name1 + " vs " + name2;
        winnerText.setText(versus); // set default text to names

        // Always clear the connect 3 grid before starting a game
        // (loading the game screen)
        clearGrid();
        // Make the Play Again (Reset) button invisible until game ends
        btnPlayAgain = findViewById(R.id.btnReset);
        btnPlayAgain.setVisibility(View.GONE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}