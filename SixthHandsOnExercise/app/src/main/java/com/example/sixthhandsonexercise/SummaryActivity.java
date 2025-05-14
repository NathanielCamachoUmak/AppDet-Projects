package com.example.sixthhandsonexercise;

import static java.lang.String.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    TextView summaryText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView empID = findViewById(R.id.textEmpID);
        TextView empName = findViewById(R.id.textEmpName);
        TextView posCode = findViewById(R.id.textPositionCode);
        TextView status = findViewById(R.id.textStatus);
        TextView days = findViewById(R.id.textDaysWorked);
        TextView basic = findViewById(R.id.textBasicPay);
        TextView sss = findViewById(R.id.textSSS);
        TextView tax = findViewById(R.id.textTax);
        TextView net = findViewById(R.id.textNetPay);

        empID.setText(getIntent().getStringExtra("empID"));
        empName.setText(getIntent().getStringExtra("empName"));
        posCode.setText(getIntent().getStringExtra("posCode"));
        status.setText(getIntent().getStringExtra("civilStatus"));
        days.setText(valueOf(getIntent().getIntExtra("daysWorked", 0)));
        basic.setText(format("Php %.2f", getIntent().getDoubleExtra("basicPay", 0)));
        sss.setText(format("Php %.2f", getIntent().getDoubleExtra("sss", 0)));
        tax.setText(format("Php %.2f", getIntent().getDoubleExtra("tax", 0)));
        net.setText(format("Php %.2f", getIntent().getDoubleExtra("netPay", 0)));

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes this activity and returns to the previous one
            }
        });
    }
}
