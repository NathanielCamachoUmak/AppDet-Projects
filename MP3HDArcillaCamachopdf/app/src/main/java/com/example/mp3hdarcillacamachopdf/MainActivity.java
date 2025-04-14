package com.example.mp3hdarcillacamachopdf;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText fullName, prelimGrade, midtermGrade, finalGrade;
    TextView nameLabel, semGradeLabel, ptEquiLabel, remarksLabel, remarksResult;
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
        nameLabel = findViewById(R.id.nameLabel);
        semGradeLabel = findViewById(R.id.semGradeLabel);
        ptEquiLabel = findViewById(R.id.ptEquiLabel);
        remarksLabel = findViewById(R.id.remarksLabel);
        remarksResult = findViewById(R.id.remarksResult); // New TextView
        btnCompute = findViewById(R.id.btnCompute);
        btnNewEntry = findViewById(R.id.btnNewEntry);

        btnCompute.setOnClickListener(v -> showConfirmDialog(true));
        btnNewEntry.setOnClickListener(v -> showConfirmDialog(false));
    }

    private void showConfirmDialog(boolean isCompute) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isCompute ? "Confirm Action" : "Confirm Action")
                .setMessage(isCompute ? "Are all entries correct?" : "Do you want to start a new entry?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (isCompute) {
                        computeGrades();
                    } else {
                        clearFields();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void computeGrades() {
        String name = fullName.getText().toString().trim();
        String pText = prelimGrade.getText().toString().trim();
        String mText = midtermGrade.getText().toString().trim();
        String fText = finalGrade.getText().toString().trim();

        try {
            if (name.isEmpty() || pText.isEmpty() || mText.isEmpty() || fText.isEmpty()) {
                throw new Exception("Incomplete fields");
            }

            float p = Float.parseFloat(pText);
            float m = Float.parseFloat(mText);
            float f = Float.parseFloat(fText);
            float semGrade = (p + m + f) / 3;
            float pointEquiv = getPointEquivalent(semGrade);
            String resultText = pointEquiv == 5.0f ? "FAILED" : "PASSED";
            int resultColor = pointEquiv == 5.0f ? Color.RED : Color.BLUE;

            nameLabel.setText("Student Name: " + name);
            semGradeLabel.setText(String.format("Semestral Grade: %.2f", semGrade));
            ptEquiLabel.setText(String.format("Point Equivalent: %.2f", pointEquiv));
            remarksLabel.setText("Final Remarks:");
            SpannableString styledResult = new SpannableString(resultText);
            styledResult.setSpan(new ForegroundColorSpan(resultColor), 0, resultText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledResult.setSpan(new StyleSpan(Typeface.BOLD), 0, resultText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            remarksResult.setText(styledResult);

            Toast.makeText(this, "Calculation complete!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Incomplete/invalid data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        fullName.setText("");
        prelimGrade.setText("");
        midtermGrade.setText("");
        finalGrade.setText("");

        nameLabel.setText("Student Name:");
        semGradeLabel.setText("Semestral Grade:");
        ptEquiLabel.setText("Point Equivalent:");
        remarksLabel.setText("Final Remarks:");
        remarksResult.setText("");

        Toast.makeText(this, "Ready for new entry", Toast.LENGTH_SHORT).show();
    }

    private float getPointEquivalent(float grade) {
        if (grade >= 100) return 1.00f;
        if (grade >= 95) return 1.50f;
        if (grade >= 90) return 2.00f;
        if (grade >= 85) return 2.50f;
        if (grade >= 80) return 3.00f;
        if (grade >= 75) return 3.50f;
        return 5.00f;
    }
}
