package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class Citas extends AppCompatActivity {
    String idActual;
    private TextView fechatxt, fechatxtDelete;
    private Button inicio, car, cita, perfil, btnfecha, btnfechaDelete;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        inicio = findViewById(R.id.buttonInicio3);
        car = findViewById(R.id.buttonCar3);
        cita = findViewById(R.id.buttonCitas3);
        perfil = findViewById(R.id.buttonPefil3);
        btnfecha = findViewById(R.id.button5Fecha);
        btnfechaDelete = findViewById(R.id.button6FechaDelete);

        fechatxt = findViewById(R.id.editFechatxt);
        fechatxtDelete = findViewById(R.id.editFechatxt2);

        
        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 1;
                fecha(i);
            }

        });
        btnfechaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                fecha(i);
            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Vehiculos.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Citas.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Perfil.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private void fecha(int i) {
        DatePickerDialog dialog = new DatePickerDialog(Citas.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month , int day) {
                switch (i){
                    case 1:
                        fechatxtDelete.setText("");
                        fechatxt.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                        break; // Agregar esta línea
                    case 0:
                        fechatxt.setText("");
                        fechatxtDelete.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                        break; // Agregar esta línea
                }
            }
        },
                2024,0,15);
        dialog.show();
    }
    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyboardUtils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }


}