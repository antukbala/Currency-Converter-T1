package com.antukbala.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.antukbala.currencyconvert.Retrofit.RetrofitBuilder;
import com.antukbala.currencyconvert.Retrofit.RetrofitInterface;
import com.google.gson.JsonObject;


public class MainActivity extends AppCompatActivity {
    Button button;
    EditText amountToConvert;
    TextView output;
    Spinner convertTo;
    Spinner convertFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        amountToConvert = findViewById(R.id.editTextAmount);
        output = findViewById(R.id.textViewOutput);
        convertTo = findViewById(R.id.spinnerConvertTo);
        convertFrom = findViewById(R.id.spinnerConvertFrom);

        String[] dropDownList = {"USD", "BDT", "INR", "EUR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convertTo.setAdapter(adapter);
        convertFrom.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertFrom.getSelectedItem().toString());

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonObject conversion_rates = res.getAsJsonObject("conversion_rates");

                        double amount;
                        if (amountToConvert.getText().toString().length() == 0) amount = 0;
                        else amount = Double.valueOf(amountToConvert.getText().toString());

                        double multiplier = Double.valueOf(conversion_rates.get(convertTo.getSelectedItem().toString()).toString());

                        double result = amount * multiplier;

                        output.setText(String.valueOf(result));
                        output.setTextColor(Color.GREEN);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });
    }
}