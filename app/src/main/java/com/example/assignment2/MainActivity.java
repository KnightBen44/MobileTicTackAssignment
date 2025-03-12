package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button startGameBtn;
    TextView errorText;
    String userInput;
    String userInput2;

    public void startGame(View v) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra("Name1", userInput); // store user 1's name
        gameIntent.putExtra("Name2", userInput2); // store user 2's name
        startActivity(gameIntent); // begin the game activity
    }
    /// Generates a random number after validating the user's name
    public void takeNameInput(View v) {
        // Take user input
        EditText nameInputField = findViewById(R.id.enterNameText);
        EditText nameInputField2 = findViewById(R.id.enterName2Text);
        userInput = String.valueOf(nameInputField.getText());
        userInput2 = String.valueOf(nameInputField2.getText());

        // Loop through all characters of the name and check for anything other than letters or space
        boolean onlyLetters = true;
        boolean onlyLetters2 = true;
        char[] charArray = userInput.toCharArray();
        char[] charArray2 = userInput2.toCharArray();
        // Check first name input
        for (char character : charArray) {
            // checks if character is neither letter nor space
            if (!Character.isLetter(character) && !Character.isSpaceChar(character)) {
                onlyLetters = false;
            }
        }
        // Check second name input
        for (char character : charArray2) {
            // checks if character is neither letter nor space
            if (!Character.isLetter(character) && !Character.isSpaceChar(character)) {
                onlyLetters2 = false;
            }
        }

        // Validate user input for name
        if (userInput.isEmpty() || userInput2.isEmpty()) { // if blank
            errorText.setText(R.string.validation_empty_name);
        } else if (!onlyLetters || !onlyLetters2) { // if invalid
            errorText.setText(R.string.validation_invalid_name);
        } else { // valid names entered
            errorText.setText(""); // no error message

            // Show next activity that displays the game
            startGame(v);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // *** setContextView MUST COME BEFORE ANY findViewById ***
        setContentView(R.layout.activity_main);

        startGameBtn = findViewById(R.id.startGameBtn);
        errorText = findViewById(R.id.errorText);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}