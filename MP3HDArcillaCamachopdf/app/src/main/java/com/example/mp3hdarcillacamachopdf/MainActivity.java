package com.example.mp3hdarcillacamachopdf;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText fullName, prelimGrade, midtermGrade, finalGrade;
    Button btnCompute, btnNewEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI elements
        fullName = findViewById(R.id.fullName);
        prelimGrade = findViewById(R.id.prelimGrade);
        midtermGrade = findViewById(R.id.midtermGrade);
        finalGrade = findViewById(R.id.finalGrade);
        btnCompute = findViewById(R.id.btnCompute);
        btnNewEntry = findViewById(R.id.btnNewEntry);

        // Compute button click
        btnCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are all entries correct?")
                        .setPositiveButton("Yes", (dialog, which) -> computeGrade())
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        // New Entry button click
        btnNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to clear all entries?")
                        .setPositiveButton("Yes", (dialog, which) -> clearFields())
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    private void computeGrade() {
        try {
            String name = fullName.getText().toString();
            float prelim = Float.parseFloat(prelimGrade.getText().toString());
            float midterm = Float.parseFloat(midtermGrade.getText().toString());
            float finals = Float.parseFloat(finalGrade.getText().toString());

            float semGrade = (prelim * 0.25f) + (midterm * 0.25f) + (finals * 0.5f);
            String ptEquivalent;

            if (semGrade == 100) {
                ptEquivalent = "1.00";
            } else if (semGrade >= 95) {
                ptEquivalent = "1.50";
            } else if (semGrade >= 90) {
                ptEquivalent = "2.00";
            } else if (semGrade >= 85) {
                ptEquivalent = "2.50";
            } else if (semGrade >= 80) {
                ptEquivalent = "3.00";
            } else if (semGrade >= 75) {
                ptEquivalent = "3.50";
            } else {
                ptEquivalent = "5.00";
            }

            String message = "Student: " + name +
                    "\nSemestral Grade: " + semGrade +
                    "\nPoint Equivalent: " + ptEquivalent;

            new AlertDialog.Builder(this)
                    .setTitle("Grade Result")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();

        } catch (Exception e) {
            Toast.makeText(this, "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        fullName.setText("");
        prelimGrade.setText("");
        midtermGrade.setText("");
        finalGrade.setText("");
    }
}
