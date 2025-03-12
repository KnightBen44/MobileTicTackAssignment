package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    boolean gameEnded = false;



    /// Changes the image of a grid space on user click & changes players
    public void onGridSpaceClick(View v) {
        ImageView img = findViewById(v.getId());
        img.setClickable(true);

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
                player = "O";
            } else if (Objects.equals(player, "O")) {
                img.setImageResource(R.drawable.o);
                img.startAnimation(fadeIn);
                player = "X";
            }
        } else { // grid space already contains an image
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

            // Animate the X or O if user clicks on it
            img.startAnimation(shake); // the letter shakes
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

        winnerText = findViewById(R.id.textWinner);
        String versus = name1 + " vs " + name2;
        winnerText.setText(versus); // set default text to names



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}