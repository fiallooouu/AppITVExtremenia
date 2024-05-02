package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {

    String correoActual;

    ListView misCitas, misVehiculos;

    Button inicio, car, cita, perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Intent intent = getIntent();
        correoActual = intent.getStringExtra("gmail");

        //Botones
        inicio = findViewById(R.id.buttonInicio);
        car = findViewById(R.id.buttonCar);
        cita = findViewById(R.id.buttonCitas);
        perfil = findViewById(R.id.buttonPefil);

        //Listas
        misCitas = findViewById(R.id.listCitas);
        misVehiculos = findViewById(R.id.listVehiculos);



        ArrayList<String> listaDatos = new ArrayList<>();
        listaDatos.add(correoActual);
        listaDatos.add(correoActual);
        listaDatos.add(correoActual);
        listaDatos.add(correoActual);
        listaDatos.add(correoActual);
        listaDatos.add(correoActual);
        listaDatos.add(correoActual);
        listaDatos.add(correoActual);

        //Tipos de listas:
        // android.R.layout.simple_list_item_1: Muestra una sola línea de texto.
        //android.R.layout.simple_list_item_2: Muestra dos líneas de texto, útil para mostrar un título y una descripción, por ejemplo.
        //android.R.layout.simple_list_item_checked: Muestra una sola línea de texto con una casilla de verificación a la izquierda.
        //android.R.layout.simple_list_item_single_choice: Muestra una sola línea de texto con un botón de selección a la derecha. Solo un elemento puede estar seleccionado a la vez.
        //android.R.layout.simple_list_item_multiple_choice: Similar a simple_list_item_single_choice, pero permite seleccionar múltiples elementos.
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, listaDatos);
        misCitas.setAdapter(adaptador);









        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Inicio.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Vehiculos.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Citas.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Perfil.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyboardUtils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}
