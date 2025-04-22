package com.example.fifthhandsonexercise;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox cbCaffe, cbGreenTea, cbSmores, cbMocha;
    RadioButton rb5, rb10, rb15, rbNone;
    TextView tvSubTotal, tvDiscount, tvNetAmount;
    Button btnCompute, btnClear;
    RadioGroup rgDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbCaffe = findViewById(R.id.cbCaffe);
        cbGreenTea = findViewById(R.id.cbGreenTea);
        cbSmores = findViewById(R.id.cbSmores);
        cbMocha = findViewById(R.id.cbMocha);

        rb5 = findViewById(R.id.rb5);
        rb10 = findViewById(R.id.rb10);
        rb15 = findViewById(R.id.rb15);
        rbNone = findViewById(R.id.rbNone);
        rgDiscount = findViewById(R.id.rgDiscount);

        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvNetAmount = findViewById(R.id.tvNetAmount);

        btnCompute = findViewById(R.id.btnCompute);
        btnClear = findViewById(R.id.btnClear);

        setListeners();

        btnCompute.setOnClickListener(v -> computeTotal());
        btnClear.setOnClickListener(v -> clearAll());
    }

    private void setListeners() {
        cbCaffe.setOnClickListener(v -> showToast("You Select Caffè Vanilla Frappuccino\nPhp 150.00"));
        cbGreenTea.setOnClickListener(v -> showToast("You Select Green Tea Crème Frappuccino\nPhp 190.00"));
        cbSmores.setOnClickListener(v -> showToast("You Select S'mores Frappuccino\nPhp 199.00"));
        cbMocha.setOnClickListener(v -> showToast("You Select Mocha Frappuccino\nPhp 130.00"));

        rb5.setOnClickListener(v -> showToast("You Select 5% discount"));
        rb10.setOnClickListener(v -> showToast("You Select 10% discount"));
        rb15.setOnClickListener(v -> showToast("You Select 15% discount"));
        rbNone.setOnClickListener(v -> showToast("You Select No discount"));
    }

    private void computeTotal() {
        double subTotal = 0.0;

        if (cbCaffe.isChecked()) subTotal += 150;
        if (cbGreenTea.isChecked()) subTotal += 190;
        if (cbSmores.isChecked()) subTotal += 199;
        if (cbMocha.isChecked()) subTotal += 130;

        double discountRate = 0.0;
        if (rb5.isChecked()) discountRate = 0.05;
        else if (rb10.isChecked()) discountRate = 0.10;
        else if (rb15.isChecked()) discountRate = 0.15;

        double discount = subTotal * discountRate;
        double netAmount = subTotal - discount;

        tvSubTotal.setText("Sub-Total: " + subTotal);
        tvDiscount.setText("Discount: " + discount);
        tvNetAmount.setText("Net Amount: " + netAmount);
    }

    private void clearAll() {
        cbCaffe.setChecked(false);
        cbGreenTea.setChecked(false);
        cbSmores.setChecked(false);
        cbMocha.setChecked(false);

        rbNone.setChecked(true);

        tvSubTotal.setText("Sub-Total: ");
        tvDiscount.setText("Discount: ");
        tvNetAmount.setText("Net Amount: ");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
