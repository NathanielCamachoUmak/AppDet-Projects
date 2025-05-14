package com.example.sixthhandsonexercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerID, spinnerPosition;
    EditText editTextDays;
    TextView textViewName;
    RadioGroup radioGroupStatus;
    Button computeBtn, clearBtn;

    String[] employeeNames = {"Pepsi", "Coke", "Sprite", "Fanta", "Royal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerID = findViewById(R.id.spinnerID);
        spinnerPosition = findViewById(R.id.spinnerPosition);
        editTextDays = findViewById(R.id.editTextDays);
        textViewName = findViewById(R.id.textViewName);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        computeBtn = findViewById(R.id.buttonCompute);
        clearBtn = findViewById(R.id.buttonClear);

        ArrayAdapter<CharSequence> adapterID = ArrayAdapter.createFromResource(this, R.array.employee_ids, android.R.layout.simple_spinner_item);
        adapterID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerID.setAdapter(adapterID);

        ArrayAdapter<CharSequence> adapterPos = ArrayAdapter.createFromResource(this, R.array.position_codes, android.R.layout.simple_spinner_item);
        adapterPos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosition.setAdapter(adapterPos);

        spinnerID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textViewName.setText(employeeNames[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        computeBtn.setOnClickListener(view -> {
            if (editTextDays.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter number of days worked", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedStatusID = radioGroupStatus.getCheckedRadioButtonId();
            if (selectedStatusID == -1) {
                Toast.makeText(this, "Please select a civil status", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                String empID = spinnerID.getSelectedItem().toString();
                String empName = textViewName.getText().toString();
                String posCode = spinnerPosition.getSelectedItem().toString();
                int daysWorked = Integer.parseInt(editTextDays.getText().toString());

                RadioButton selectedStatus = findViewById(selectedStatusID);
                String civilStatus = selectedStatus.getText().toString();

                double rate = getRate(posCode);
                double basicPay = daysWorked * rate;
                double taxRate = getTaxRate(civilStatus);
                double withholdingTax = basicPay * taxRate;
                double sssRate = getSSSRate(basicPay);
                double sssContribution = basicPay * sssRate;
                double netPay = basicPay - (withholdingTax + sssContribution);

                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                intent.putExtra("empID", empID);
                intent.putExtra("empName", empName);
                intent.putExtra("posCode", posCode);
                intent.putExtra("civilStatus", civilStatus);
                intent.putExtra("daysWorked", daysWorked);
                intent.putExtra("basicPay", basicPay);
                intent.putExtra("sss", sssContribution);
                intent.putExtra("tax", withholdingTax);
                intent.putExtra("netPay", netPay);
                startActivity(intent);

            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        clearBtn.setOnClickListener(view -> {
            spinnerID.setSelection(0);
            spinnerPosition.setSelection(0);
            editTextDays.setText("");
            radioGroupStatus.clearCheck();
            textViewName.setText("");
        });
    }

    private double getRate(String position) {
        switch (position) {
            case "A": return 500;
            case "B": return 400;
            case "C": return 300;
            default: return 0;
        }
    }

    private double getTaxRate(String status) {
        switch (status) {
            case "Single": return 0.10;
            case "Married":
            case "Widowed": return 0.05;
            default: return 0;
        }
    }

    private double getSSSRate(double basicPay) {
        if (basicPay >= 10000) return 0.07;
        else if (basicPay >= 5000) return 0.05;
        else if (basicPay >= 1000) return 0.03;
        else return 0.01;
    }
}
