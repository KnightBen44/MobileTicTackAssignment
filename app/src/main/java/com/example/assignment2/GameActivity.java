package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    String name1; // name of player 1
    String name2; // name of player 2
    TextView winnerText;

    // Game Play code here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // *** setContextView MUST COME BEFORE ANY findViewById ***
        setContentView(R.layout.activity_game);

        Intent intent = getIntent(); // get the intent made in MainActivity
        name1 = intent.getStringExtra("Name1"); // get data from intent
        name2 = intent.getStringExtra("Name2");

        winnerText = findViewById(R.id.winnerText);
        String versus = name1 + " vs " + name2;
        winnerText.setText(versus); // set default text to names

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}